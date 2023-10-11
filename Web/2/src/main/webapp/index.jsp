<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Point" %>
<%@ page import="models.Points" %>
<%@ page import="java.util.Collections" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Лаба 1</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<header class="header">
    <div class="info">
        <h1>Лабораторная работа 1, вар 2811</h1>
        <h2>Наумов Павел Викторович, P3218</h2>
    </div>
</header>
<main class="main">
    <div class="row">
        <div class="form">
            <div class="fields">

                <div class="error">
                    <span id="error-message"></span>
                </div>

                <div class="x">
                    <label for="X-Input">Выберите X:</label>
                    <select name="X-Input" id="X-Input">
                        <option value="-4">-4</option>
                        <option value="-3">-3</option>
                        <option value="-2">-2</option>
                        <option value="-1">-1</option>
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                    </select>
                </div>
                <div class="y">
                    <label for="Y-Input">Выберите Y:</label>
                    <input type="text" name="Y-Input" maxlength="50" id="Y-Input" placeholder="-5 ... 5" row>
                </div>
                <div class="r">
                    <label for="R-Input">Выберите R:</label>
                    <div class="rows">
                        <div class="row1">
                            <span>1</span>
                            <input name="R-Input" type="checkbox" value="1.0">
                            <span>1.5</span>
                            <input name="R-Input" type="checkbox" value="1.5">
                            <span>2</span>
                            <input name="R-Input" type="checkbox" value="2.0">
                            <span>2.5</span>
                            <input name="R-Input" type="checkbox" value="2.5">
                            <span>3</span>
                            <input name="R-Input" type="checkbox" value="3.0">
                        </div>
                    </div>
                </div>
                <div class="subbmit">
                    <button id="subbmit">
                        Проверить
                    </button>
                </div>
            </div>
        </div>
        <div id="plot">
            <svg xmlns="http://www.w3.org/2000/svg" id="graph">
                <line x1="0" y1="150" x2="300" y2="150" stroke="#000720"></line>
                <line x1="150" y1="0" x2="150" y2="300" stroke="#000720"></line>
                <line x1="270" y1="148" x2="270" y2="152" stroke="#000720"></line>
                <text x="265" y="140">R</text>
                <line x1="210" y1="148" x2="210" y2="152" stroke="#000720"></line>
                <text x="200" y="140">R/2</text>
                <line x1="90" y1="148" x2="90" y2="152" stroke="#000720"></line>
                <text x="75" y="140">-R/2</text>
                <line x1="30" y1="148" x2="30" y2="152" stroke="#000720"></line>
                <text x="20" y="140">-R</text>
                <line x1="148" y1="30" x2="152" y2="30" stroke="#000720"></line>
                <text x="156" y="35">R</text>
                <line x1="148" y1="90" x2="152" y2="90" stroke="#000720"></line>
                <text x="156" y="95">R/2</text>
                <line x1="148" y1="210" x2="152" y2="210" stroke="#000720"></line>
                <text x="156" y="215">-R/2</text>
                <line x1="148" y1="270" x2="152" y2="270" stroke="#000720"></line>
                <text x="156" y="275">-R</text>


                <polygon points="90,150 150,30 150,150" fill-opacity="0.4" stroke="navy" fill="blue"></polygon>
                <rect x="150" y="150" width="120" height="120" fill-opacity="0.4" stroke="navy" fill="blue"></rect>
                <path d="M 150 150 L 210 150 C 210 160 220 90 150 90 Z" fill-opacity="0.4" stroke="navy" fill="blue"></path>
                <circle id="pointer" cx="150" cy="150"></circle>
            </svg>
        </div>
    </div>
    <div class="row">
        <div class="results">
            <%
                Points points = (Points) request.getSession().getAttribute("points");
                if (points == null) {
            %>
            <span>Нет результатов</span>
            <table id="resultTable">
                <thead>
                <tr>
                    <th>x</th>
                    <th>y</th>
                    <th>r</th>
                    <th>статус</th>
                </tr>
                </thead>
                <tbody id="results-values">
                </tbody>
            </table>
            <%} else {%>
            <table id="resultTable">
                <thead>
                <tr>
                    <th>x</th>
                    <th>y</th>
                    <th>r</th>
                    <th>статус</th>
                </tr>
                </thead>
                <tbody id="results-values">
                <%for(Point point : points.getPoints()) {%>
                <tr>
                    <td><%=point.getX()%></td>
                    <td><%=point.getY()%></td>
                    <td><%=point.getR()%></td>
                    <td><%=point.isInArea() ? "<span class=\"success\">Попал</span>"
                            : "<span class=\"fail\">Промазал</span>"%></td>
                </tr>
                <%}%>
                </tbody>
            </table>
            <%}%>
        </div>

    </div>
</main>
<script
        src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
        crossorigin="anonymous"></script>
<script src="script.js"></script>
</body>
</html>
