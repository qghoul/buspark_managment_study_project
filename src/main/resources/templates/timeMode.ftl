<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/static/css/styles.css" rel="stylesheet" type="text/css">
    <title>Routes time mode</title>
</head>
<body>
<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>

<ul class="button-list">
    <li><a href="/routes">Back to Routes info page</a></li>
</ul>
<table class="table">
    <tr>
        <th>Route Number</th>
        <th>Start location</th>
        <th>End location</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Travel interval</th>
    </tr>
    <#list routes as route>
        <tr>
            <td>${route.routeNumber}</td>
            <td>${route.startLocation}</td>
            <td>${route.endLocation}</td>
            <td>${route.startTime}</td>
            <td>${route.endTime}</td>
            <td>${route.travelInterval}</td>
        </tr>
    </#list>
</table>
</body>
<html>