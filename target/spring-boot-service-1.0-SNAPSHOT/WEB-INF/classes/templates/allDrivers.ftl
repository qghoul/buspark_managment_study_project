<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/static/css/styles.css" rel="stylesheet" type="text/css">
    <title>Drivers</title>
</head>
<body>
<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>

<ul class="button-list">
    <li><a href="/drivers/add">Add new driver</a></li>
    <li><a href="/drivers/update">Update information about driver</a></li>
    <!--<li><a href="/unavailable_buses">Find drivers by route number with their work schedules</a></li>-->
</ul>

<div class="form-list">
    <form class="findByParam" action="/drivers/searchByRouteWithSchedule" method="GET">
        <button type="submit">Find drivers by route number with their work schedules</button>
        <label for="routeNumber">Enter route number:</label>
        <input type="text" id="routeNumber" name="routeNumber"
               <#if routeNumber??>value="${routeNumber}" </#if> required>
    </form>
    <form class="deleteById" action="/drivers/deleteById" method="POST">
        <button type="submit">Delete driver by ID</button>
        <label for="driverId">Enter driver ID:</label>
        <input type="text" id="driverId" name="driverId" required>
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
        <th>Full Name</th>
        <th>Passport Number</th>
        <th>Bus ID</th>
        <th>Driver Class</th>
        <th>Experience (years)</th>
        <th>Salary (UAH)</th>
        <th>Update</th>
    </tr>
    <#list drivers as driver>
        <tr>
            <td>${driver.id}</td>
            <td>${driver.passportData.fullName}</td>
            <td>${driver.passportData.passportNumber}</td>
            <td>${driver.bus.id}</td>
            <td>${driver.driverClass}</td>
            <td>${driver.experience}</td>
            <td>${driver.salary}</td>
            <td><div class="driver-fast-update">Update</div></td>
        </tr>
    </#list>
</table>