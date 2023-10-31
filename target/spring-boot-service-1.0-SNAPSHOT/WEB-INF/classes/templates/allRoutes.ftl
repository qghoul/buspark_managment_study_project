<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/static/css/styles.css" rel="stylesheet" type="text/css">
    <title>Routes</title>
</head>
<body>
<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>

<ul class="button-list">
    <li><a href="/routes/add">Add new route</a></li>
    <li><a href="/routes/update">Update information about route</a></li>
    <li><a href="/routes/time">On only time mode</a></li>
</ul>

<div class="form-list">
    <form class="findByParam" action="/routes/searchByLocation" method="GET">
        <button type="submit">Search route by location</button>
        <label for="location">Enter location:</label>
        <input class="long-input" type="text" id="location" name="location"
               <#if location??> value="${location}"</#if> required>
    </form>
    <form class="deleteById" action="/routes/deleteById" method="POST">
        <button type="submit">Delete route by ID</button>
        <label for="routeId">Enter route ID:</label>
        <input type="text" id="routeId" name="routeId" required>
    </form>
    <form class="findByParam" id="getDurationByRouteNumber" action="/routes/getDurationByRouteNumber" method="GET">
        <button type="submit">Get duration by route number</button>
        <label for="routeNumber">Enter route number:</label>
        <input type="text" id="routeNumber" name="routeNumber"
                <#if routeNumber??> value="${routeNumber}"</#if> required>
        <#if durationByRouteNumberError??>
            <div class="durationByRouteNumberError">${durationByRouteNumberError}</div>
        </#if>
        <#if durationByRouteNumber??>
            <div class="durationByRouteNumber">${durationByRouteNumber}</div>
        </#if>
    </form>
</div>
<#if errorMessage??>
    <div class="errorMessage">${errorMessage}</div>
</#if>
<#if successMessage??>
    <div class="successMessage">${successMessage}</div>
</#if>
<table class="table">
    <tr>
        <th>ID</th>
        <th>Route Number</th>
        <th>Start Location</th>
        <th>End Location</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Travel Interval (min)</th>
        <th>Duration (min)</th>
        <th>Update</th>
    </tr>
    <#list routes as route>
        <tr>
            <td>${route.id}</td>
            <td>${route.routeNumber}</td>
            <td>${route.startLocation}</td>
            <td>${route.endLocation}</td>
            <td>${route.startTime}</td>
            <td>${route.endTime}</td>
            <td>${route.travelInterval}</td>
            <td>${route.duration}</td>
            <td><div class="route-fast-update"> Update</div></td>
        </tr>
    </#list>
</table>