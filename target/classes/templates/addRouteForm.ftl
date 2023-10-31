<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/static/css/styles.css" rel="stylesheet" type="text/css">
    <title>Add route</title>
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
        <form class="addForm" action="/routes/add" method="post">
            <div class="text-field">
                <label class="text-field__label" for="routeNumber">Route Number:</label>
                <input class="text-field__input" type="text" id="routeNumber" name="routeNumber" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="startLocation">Start location:</label>
                <input class="text-field__input" type="text" id="startLocation" name="startLocation" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="endLocation">End location:</label>
                <input class="text-field__input" type="text" id="endLocation" name="endLocation" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="startTime">Start time:</label>
                <input class="text-field__input" type="text" id="startTime" name="startTime" placeholder="00:00" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="endTime">End time:</label>
                <input class="text-field__input" type="text" id="endTime" name="endTime" placeholder="23:59" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="travelInterval">Travel interval:</label>
                <input class="text-field__input" type="Number" id="travelInterval" name="travelInterval" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="duration">Duration:</label>
                <input class="text-field__input" type="Number" id="duration" name="duration" required>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <br>
            <input class="submit-form" type="submit" value="Add route">
        </form>
    </div>
</div>
</body>
</html>