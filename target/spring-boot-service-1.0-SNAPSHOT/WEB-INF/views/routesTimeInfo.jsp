<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Page Title</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/style.css' />">
    <title>Routes Time Info</title>
</head>
<body>

<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>

<h1>All Routes Time Info</h1>

<table>
    <tr>
        <th>Route Number</th>
        <th>Start Time</th>
        <th>End Time</th>
    </tr>
    <c:forEach items="${routes}" var="route">
        <tr>
            <td>${route.routeNumber}</td>
            <td>${route.startTime}</td>
            <td>${route.endTime}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>