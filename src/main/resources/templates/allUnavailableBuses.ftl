<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/static/css/styles.css" rel="stylesheet" type="text/css">
    <title>Unavailable buses</title>
</head>
<body>
<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>

<ul class="button-list">
    <li><a href="/buses/send_report">Send new bus unavailability report </a></li>
</ul>

<div class="form-list">
    <form class="deleteById" action="/buses/make_available" method="POST">
        <button type="submit">Make bus available by bus ID</button>
        <label for="busId">Enter bus ID:</label>
        <input type="number" id="busId" name="busId" required>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
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
        <th>Bus ID</th>
        <th>Route number</th>
        <th>Issue date</th>
        <th>Reason</th>
    </tr>
    <#list buses as bus>
        <tr>
            <td>${bus.id}</td>
            <td>${bus.route.routeNumber}</td>
            <td>${bus.unavailableBusDetails.issueDate}</td>
            <td>${bus.unavailableBusDetails.reason}</td>
        </tr>
    </#list>
</table>
</body>
<html>