<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Page Title</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/style.css' />">
    <title>Drivers</title>
</head>
<body>

<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>

<h1>All Drivers</h1>

<table>
    <tr>
        <th>ID</th>
        <th>Full Name</th>
        <th>Passport Number</th>
        <th>Bus Registration Number</th>
        <th>Driver Class</th>
        <th>Experience</th>
        <th>Salary</th>
    </tr>
    <c:forEach items="${drivers}" var="driver">
        <tr>
            <td>${driver.id}</td>
            <td>${driver.passportData.fullName}</td>
            <td>${driver.passportData.passportNumber}</td>
            <td>${driver.bus.registrationNumber}</td>
            <td>${driver.driverClass}</td>
            <td>${driver.experience}</td>
            <td>${driver.salary}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>