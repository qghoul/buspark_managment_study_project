<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/static/css/styles.css" rel="stylesheet" type="text/css">
    <title>Buses</title>
</head>
<body>

<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>

<ul class="button-list">
    <li><a href="/buses/add">Add new bus</a></li>
    <li><a href="/buses/update">Update information about bus</a></li>
    <li><a href="/buses/send_report">Send a bus unavailability report </a></li>
    <li><a href="/buses/unavailable_buses">Unavailable buses </a></li>
</ul>

<div class="form-list">
    <form class="findByParam" action="/buses/searchByRouteNumber" method="GET">
        <button type="submit">Search buses by route number</button>
        <label for="routeNumber">Enter route number:</label>
        <input type="text" id="routeNumber" name="routeNumber"
               <#if routeNumber??>value="${routeNumber}" </#if> required>
    </form>
    <form class="deleteById" action="/buses/deleteById" method="POST">
        <button type="submit">Delete bus by ID</button>
        <label for="busId">Enter bus ID:</label>
        <input type="text" id="busId" name="busId" required>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</div>
<#if errorMessage??>
    <div class="errorMessage">${errorMessage}</div>
</#if>
<#if successMessage??>
    <div class="successMessage">${successMessage}</div>
</#if>
<#if buses?size == 0>
    <div id="emptyMessage">The list of buses is empty. To add a bus use the "Add new bus" button.</div>
<#else>
    <div class="catalog">
    <#list buses as bus>
            <div class="catalog-item">
                <img src="${bus.imageUrl}" alt="busImage">
                <div class="bus-info">
                    <p>ID: ${bus.id}</p>
                    <p>Route Number: ${bus.route.routeNumber}</p>
                    <p>${bus.registrationNumber}</p>
                    <p>${bus.busType}</p>
                    <p>Capacity: ${bus.capacity}</p>
                    <p>${bus.getAvailabilityString()}</p>
                </div>
            </div>
    </#list>
    </div>
</#if>
</body>
</html>