let x, y, r

window.onload = function () {
    const table = document.getElementById("resultTable");

    if (table) {
        processTable(table);
    }

    svg.addEventListener("click", (event) => {
        if (checkR()) {
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
                    Math.floor(planeCoords.x), planeCoords.y.toFixed(1), r
                );
            }
        }
    });
};


document.getElementById("subbmit").onclick = (elem) => {
    clearError();
    fieldsIsValid = checkX() && checkY() && checkR();
    if (!fieldsIsValid) {
        return;
    }

    const form = $('<form>', {
        action: "controller",
        method: "post"
    });

    const args = { action: "submit", x: x, y: y, r: r };
    Object.entries(args).forEach(entry => {
        const [key, value] = entry;
        $('<input>').attr({
            type: "hidden",
            name: key,
            value: value
        }).appendTo(form);
    });

    form.appendTo('body').submit();
};

function sendReq(x, y, r) {
    clearError();

    const data = new URLSearchParams();
    data.append("x", x);
    data.append("y", y);
    data.append("r", r);
    data.append("action", "check");
    console.log(data);

    fetch("./controller", {
        method: 'POST',
        body: data,
    }).then((res) => res.json()).then((res) => {
        drawPoint(x, y, r, res.result);
        addToTable(x, y, r, res.result);
    });
}

//
// document.getElementById("subbmit").onclick = (elem) => {

// }

function processTable(table) {
    for (let item of table.rows) {
        let x = parseFloat(item.children[0].innerText.trim());
        let y = parseFloat(item.children[1].innerText.trim());
        let r = parseFloat(item.children[2].innerText.trim());
        if (isNaN(x) || isNaN(y) || isNaN(r)) continue;

        let result = item.children[3].innerText.trim() === "Попал";
        drawPoint(x, y, r, result);
    }
}

function addPoint() {
    let point = document.getElementById("pointer");
    point.setAttribute("cx", x * 60 * 2/r + 150);
    point.setAttribute("cy", y * 60 * 2/r + 150);
    pointer.style.visibility = "visible";
}

const buttons = document.querySelectorAll("input[name = R-Input]")
buttons.forEach((elem) => {
    elem.onclick = function () {
        r = this.value;
        buttons.forEach((buttton) => {
            buttton.checked = false;
        })
        this.checked = true;
    }
})

function checkX() {
    value = document.querySelector("select[name = X-Input]").value
    if (!(value >= -4 && value <= 4)) {
        showError("X не входит в область допустимых значений");
        return false;
    }

    x = value;
    return true;
}

function checkY() {
    y = document.querySelector("input[name=Y-Input]").value.replace(",", ".");
    if (y === undefined) {
        showError("Y не введён");
        return false;
    } else if (!isNumeric(y)) {
        showError("Y не число");
        return false;
    } else if (!((y >= -5) && (y <= 3))) {
        showError("Y не входит в область допустимых значений");
        return false;
    } else {
        const reqex1 = /\-5\.0+([1-9])/;
        const reqex2 = /3\.0+([1-9])/;
        if(reqex1.test(y) || reqex2.test(y)) {
            showError("Y не входит в область допустимых значений");
            return false;
        } else {
            return true;
        }
    }
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

svg = document.getElementById("graph");
function drawPoint(x, y, r, result) {
    let circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    circle.setAttribute("cx", x * 60 * 2 / r + 150);
    circle.setAttribute("cy", -y * 60 * 2 / r + 150);
    circle.setAttribute("r", 3);
    circle.style.fill = result ? "#09a53d" : "#a50909";
    svg.appendChild(circle);
}

function transformSvgToPlane(svgX, svgY, r) {
    return { x: (svgX - 150) / (120 / r), y: (150 - svgY) / (120 / r) };
}

function addToTable(x, y, r, result) {
    const table = document.getElementById("results-values");
    clearError();

    const row = table.insertRow();
    row.insertCell().innerText = x;
    row.insertCell().innerText = y;
    row.insertCell().innerText = r;
    row.insertCell().innerHTML = result ? "<span class=\"success\">Попал</span>" : "<span class=\"fail\">Промазал</span>";
}

const error = document.getElementById("error-message");
function showError(message) {
    error.textContent = message;
}

function clearError() {
    error.textContent = "";
}

function isNumeric(val) {
    return !isNaN(parseFloat(val)) && isFinite(val);
}

