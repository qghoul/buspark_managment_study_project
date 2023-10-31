<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Page Title</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/style.css' />">
    <meta charset="UTF-8">
    <title>Drivers by Route</title>
</head>
<body>

<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>

<h1>Drivers by Route</h1>

<form method="get" action="/driversByRoute">
    <label for="routeNumber">Enter Route Number:</label>
    <input type="text" id="routeNumber" name="routeNumber">
    <input type="submit" value="Search">
</form>

<table>
    <tr>
        <th>Driver Name</th>
        <th>Route Number</th>
        <th>Work Days</th>
    </tr>
    <c:forEach items="${drivers}" var="driverInfo">
        <tr>
            <td>${driverInfo[0].passportData.fullName}</td>
            <td>${driverInfo[1]}</td>
            <td>
                <ul>
                    <c:forEach items="${driverInfo[2]}" var="workDay">
                        <li>${workDay.dayOfWeek} (${workDay.startTime} - ${workDay.endTime})</li>
                    </c:forEach>
                </ul>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>