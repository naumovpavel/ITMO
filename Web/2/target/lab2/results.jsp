<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Point" %>
<%@ page import="models.Points" %>

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
        <div class="results">
            <H3>Результаты проверки</H3>
            <%
                Points points = (Points) request.getSession().getAttribute("points");
                if (points == null) {
            %>
            <span>Нет результатов</span>
            <%} else {%>
            <table>
                <thead>
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
                </thead>
            </table>
            <%}%>
            <a href="./">Вернуться к форме</a>
        </div>
    </div>
</main>
<script src="script.js"></script>
</body>
</html>
