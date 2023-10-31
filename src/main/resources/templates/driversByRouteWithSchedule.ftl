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
    <li><a href="/drivers">Back to full drivers list</a></li>
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
        <input type="number" id="driverId" name="driverId" required>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</div>
<#if errorMessage??>
    <div class="errorMessage">${errorMessage}</div>
</#if>
<#if successMessage??>
    <div class="successMessage">${successMessage}</div>
</#if>
<table class="table" id="resultTable">
    <tr>
        <th>ID</th>
        <th>Full Name</th>
        <th>Bus ID</th>
        <th>Route Number</th>
        <th>Driver Class</th>
        <th>Work Schedule</th>
    </tr>
    <#list drivers as driver>
        <tr>
            <td>${driver.id}</td>
            <td>${driver.passportData.fullName}</td>
            <td>${driver.bus.id}</td>
            <td>${driver.bus.route.routeNumber}</td>
            <td>${driver.driverClass}</td>
            <td>
                <table class="table" id="scheduleTable">
                    <#if driver.workSchedule??>
                    <#list driver.workSchedule.workDays as workDay>
                        <#if workDay??>
                            <tr>
                                <td>${workDay.dayOfWeek}</td>
                                <td>${workDay.startTime}</td>
                                <td>${workDay.endTime}</td>
                            </tr>
                        </#if>
                    </#list>
                    </#if>
                </table>
            </td>
        </tr>
    </#list>
</table>
</body>
<html>