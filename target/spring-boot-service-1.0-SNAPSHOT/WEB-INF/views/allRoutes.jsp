<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Page Title</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/style.css' />">
    <title>Routes</title>
</head>
<body>

<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>

<h1>All Routes</h1>
<p>The total duration of all routes is ${totalDuration} minutes.</p>
<form action="/routes/getDuration" method="post">
    <label for="routeNumber">Route Number:</label>
    <input type="text" id="routeNumber" name="routeNumber" required>
    <button type="submit">Check Duration</button>
</form>
<div>
    <p>${message}</p>
</div>

<form action="/routes/searchByLocation" method="post">
    <label for="location">Location:</label>
    <input type="text" id="location" name="location" required>
    <button type="submit">Search</button>
</form>

<table>
    <tr>
        <th>ID</th>
        <th>Route Number</th>
        <th>Start Location</th>
        <th>End Location</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Travel Interval (min)</th>
        <th>Duration (min)</th>
    </tr>
    <c:forEach items="${routes}" var="route">
        <tr>
            <td>${route.id}</td>
            <td>${route.routeNumber}</td>
            <td>${route.startLocation}</td>
            <td>${route.endLocation}</td>
            <td>${route.startTime}</td>
            <td>${route.endTime}</td>
            <td>${route.travelInterval}</td>
            <td>${route.duration}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>