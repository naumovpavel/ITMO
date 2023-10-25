let r = 1;

window.onload = function () {
    var container = document.querySelector(".ice-checkboxbutton-checked").closest(".ice-checkboxbutton");
    var label = container.querySelector("label");
    var value = label.textContent.trim();
    r = value;
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
                console.log(planeCoords)
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

    //setAriaValueNowToX(x);
    submitButton.click();
}

function setAriaValueNowToX(x) {
    var sliderElement = document.getElementById("j_idt9:X-Input_handle");
    document.getElementById("j_idt9:X-Input_hidden").setAttribute("value", x);

    if (sliderElement) {
        sliderElement.setAttribute("aria-valuenow", x.toString());
        var min = parseFloat(sliderElement.getAttribute("aria-valuemin"));
        var max = parseFloat(sliderElement.getAttribute("aria-valuemax"));
        var position = ((x - min) / (max - min)) * 100 + "%";
        sliderElement.style.left = position;
    }
}

function handleCheckboxButtonClick(event) {
    // Get the clicked button element
    var button = event.target;

    // Traverse up the DOM to find the parent container (div) that contains the label
    var container = button.closest(".ice-checkboxbutton");

    // Find the associated label element within the container
    var label = container.querySelector("label");

    // Get the value from the label
    var value = label.textContent.trim();
    r = value;
    clearPoints();
    processTable();

    // Print the value to the console
    console.log("Clicked button value: " + value);
}

function clearPoints() {
    let points = document.querySelectorAll(".hitPoint");
    console.log(points);
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
        //let r = parseFloat(item.children[2].innerText.trim());
        if (isNaN(x) || isNaN(y) || isNaN(r)) continue;

        let result = item.children[3].innerText.trim() === "Есть пробите!";
        drawPoint(x, y, r, result ? "true" : "false");
    }
}

document.addEventListener("DOMContentLoaded", function() {
    var buttons = document.querySelectorAll(".ice-checkboxbutton button");
    buttons.forEach(function(button) {
        button.addEventListener("click", handleCheckboxButtonClick);
    });
});



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

function checkR() {
    if (isNumeric(r)) {
        if (r >= 1 && r <= 3) {
            return true;
        } else {
            showError("R не входит в область допустимых значений")
            return false;
        }
    } else {
        showError("Значение R не выбрано")
        return false;
    }
}


function drawPoint(x, y, r, result) {
    clearError();
    result = "false";
    if(x < 0 && y < 0) {
        if(-2*x - y <= r && 2*x >= -r && y >= -r) {
            result = "true";
        }
    } else if (x>= 0 && y >= 0) {
        if( 4*(x*x + y*y) <= r) {
            result = "true";
        }
    } else if (x>=0 && y <= 0) {
        if( x <= r && 2*y >= -r) {
            result = "true";
        }
    }
    console.log(x,y,r,result, result === "true" ? 1 : 2);
    let svg = document.getElementById("graph");
    console.log(svg);
    let circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    circle.setAttribute("cx", x * 60 * 2 / r + 150);
    circle.setAttribute("cy", -y * 60 * 2 / r + 150);
    circle.setAttribute("r", 3);
    circle.setAttribute("class", "hitPoint");
    console.log(x, y, r, result, "drawPoint");
    if(result === "true") {
        circle.setAttribute("fill", "#1ffd01");
    } else {
        console.log(x,y,r,result, "hui");
        circle.setAttribute("fill","#f60000");
    }
    svg.appendChild(circle);
}

function transformSvgToPlane(svgX, svgY, r) {
    console.log(r, "rrr");
    return { x: (svgX - 150) / (120 / r), y: (150 - svgY) / (120 / r) };
}


function showError(message) {
    console.log(message);
    const error = document.getElementById("error-message");
    error.textContent = message;
}

function clearError() {
    const error = document.getElementById("error-message");
    if(!!error)
        error.textContent = "";
}

function isNumeric(val) {
    return !isNaN(parseFloat(val)) && isFinite(val);
}