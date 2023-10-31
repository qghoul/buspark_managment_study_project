<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Page Title</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/style.css' />">
    <title>All Unavailable Buses</title>
</head>
<body>

<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>

<h1>All Unavailable Buses</h1>

<table>
    <tr>
        <th>Bus Registration Number</th>
        <th>Route Number</th>
        <th>Bus Type</th>
        <th>Reason</th>
    </tr>
    <c:forEach items="${unavailableBuses}" var="unavailableBus">
        <tr>
            <td>${unavailableBus.bus.registrationNumber}</td>
            <td>${unavailableBus.bus.route.routeNumber}</td>
            <td>${unavailableBus.bus.busType}</td>
            <td>${unavailableBus.reason}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>