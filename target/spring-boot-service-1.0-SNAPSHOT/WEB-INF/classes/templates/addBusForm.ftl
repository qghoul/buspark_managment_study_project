<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/static/css/styles.css" rel="stylesheet"/>
    <title>Buses</title>
</head>
<body>
<ul class="menu">
    <li><a href="/routes">Routes</a></li>
    <li><a href="/buses">Buses</a></li>
    <li><a href="/drivers">Drivers</a></li>
</ul>
<div class="center-global-container">
    <div class="addForm-container">
        <div class="errorMessage">
            <#if errorMessage?has_content>
                ${errorMessage}
            </#if>
        </div>
        <form class="addForm" action="/buses/add" method="post">
            <div class="text-field">
                <label class="text-field__label" for="routeNumber">Route Number:</label>
                <input class="text-field__input" type="text" id="routeNumber" name="routeNumber" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="registrationNumber">Registration number:</label>
                <input class="text-field__input" type="text" id="registrationNumber" name="registrationNumber" placeholder="AA1234BB" maxlength="8" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="capacity">Capacity:</label>
                <input class="text-field__input" type="number" id="capacity" name="capacity" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="driverId">Driver Id:</label>
                <input class="text-field__input" type="number" id="driverId" name="driverId" placeholder="Unrequired">
            </div>
            <div class="text-field">
                <label class="text-field__label" for="imageUrl">Image URL:</label>
                <input class="text-field__input" type="text" id="imageUrl" name="imageUrl" placeholder="Unrequired">
            </div>
            <br>
            <input class="submit-form" type="submit" value="Add Bus">
        </form>
    </div>
</div>
</body>
</html>