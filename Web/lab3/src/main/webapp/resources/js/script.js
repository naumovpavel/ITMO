let r_cur = 1;

window.onload = function () {
    var container = document.querySelector(".ice-checkboxbutton-checked").closest(".ice-checkboxbutton");
    var label = container.querySelector("label");
    var value = label.textContent.trim();
    r = value;
    //var buttons = document.querySelectorAll(".ice-checkboxbutton button");
    for (let i = 0; i < 5; i++) {
        document.getElementById("j_idt9:checkboxButtons:"+i+"_button").addEventListener("click", handleCheckboxButtonClick);
    }
    svg = document.getElementById("graph");
    svg.addEventListener("click", (event) => {
            let point = svg.createSVGPoint();
            point.x = event.clientX;
            point.y = event.clientY;

            let ctm = svg.getScreenCTM();
            if (ctm) {
                let invertedCTM = ctm.inverse();
                let svgPoint = point.matrixTransform(invertedCTM);

                let planeCoords = transformSvgToPlane(svgPoint.x, svgPoint.y, r);
                sendReq(
                    planeCoords.x.toFixed(1), planeCoords.y.toFixed(1)
                );
            }
    });
};


function sendReq(x, y) {
    if(!checkX(x) || !checkY(y))
        return
    var inputElement = document.getElementById("j_idt9:Y-Input");
    if (inputElement) {
        inputElement.value = y;
    }

    var inputElement = document.getElementById("j_idt9:X-Input-hidden");
    if (inputElement) {
        inputElement.value = x;
    }

    var submitButton = document.getElementById('j_idt9:subbmit');

    submitButton.click();
}

function handleCheckboxButtonClick(event) {
    var button = event.target;
    var container = button.closest(".ice-checkboxbutton");
    var label = container.querySelector("label");
    var value = label.textContent.trim();
    r_cur = value;
    clearPoints();
    processTable();
}

function clearPoints() {
    let points = document.querySelectorAll(".hitPoint");
    points.forEach(function (point) {
        point.remove();
    })
}

function processTable() {
    clearPoints();
    let table = document.getElementById("resultTable");
    for (let item of table.rows) {
        let x = parseFloat(item.children[0].innerText.trim());
        let y = parseFloat(item.children[1].innerText.trim());
        let r = parseFloat(item.children[2].innerText.trim());
        if (isNaN(x) || isNaN(y) || isNaN(r)) continue;

        let result = item.children[3].innerText.trim() === "Есть пробите!";
        drawPoint(x, y, result ? "true" : "false");
    }
}


function checkX(value) {
    if (!(value >= -2 && value <= 2)) {
        showError("X не входит в область допустимых значений");
        return false;
    }

    x = value;
    return true;
}


function checkY(y) {
    if (!(y >= -3) && (y <= 5)) {
        showError("Y не входит в область допустимых значений");
        return false;
    }
    return true;
}


function drawPoint(x, y, result) {
    clearError();
    result = false;
    r = r_cur
    if(x < 0 && y < 0) {
        if(-2*x - y <= r && 2*x >= -r && y >= -r) {
            result = true;
        }
    } else if (x>= 0 && y >= 0) {
        if( 4*(x*x + y*y) <= r) {
            result = true;
        }
    } else if (x>=0 && y <= 0) {
        if( x <= r && 2*y >= -r) {
            result = true;
        }
    }
    let svg = document.getElementById("graph");
    let circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    circle.setAttribute("cx", x * 60 * 2 / r + 150);
    circle.setAttribute("cy", -y * 60 * 2 / r + 150);
    circle.setAttribute("r", 3);
    circle.setAttribute("class", "hitPoint");
    if(result === true) {
        circle.setAttribute("fill", "#1ffd01");
    } else {
        circle.setAttribute("fill","#f60000");
    }
    svg.appendChild(circle);
}

function transformSvgToPlane(svgX, svgY, r) {
    return { x: (svgX - 150) / (120 / r), y: (150 - svgY) / (120 / r) };
}


function showError(message) {
    const error = document.getElementById("error-message");
    error.textContent = message;
}

function clearError() {
    const error = document.getElementById("error-message");
    if(error)
        error.textContent = "";
}

function isNumeric(val) {
    return !isNaN(parseFloat(val)) && isFinite(val);
}