<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/static/css/styles.css" rel="stylesheet" type="text/css">
    <title>Add driver form</title>
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
        <form class="addForm" action="/drivers/add" method="post">
            <div class="text-field">
                <label class="text-field__label" for="fullName">Full Name</label>
                <input class="text-field__input" type="text" id="fullName" name="fullName" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="busId">Bus ID</label>
                <input class="text-field__input" type="number" id="busId" name="busId" placeholder="Unrequired">
            </div>
            <div class="text-field">
                <label class="text-field__label" for="passportNumber">Passport number</label>
                <input class="text-field__input" type="text" id="passportNumber" name="passportNumber" maxlength="9" placeholder="123456789 or AO 123456" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="experience"> Years of experience</label>
                <input class="text-field__input" type="number" id="experience" name="experience" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="driverClass">Driver class</label>
                <select class="text-field__select" id="driverClass" name="driverClass" required>
                    <option value="A"> A </option>
                    <option value="B"> B </option>
                    <option value="C"> C </option>
                    <option value="D"> D </option>
                    <option value="E"> E </option>
                </select>
            </div>
            <label class="table__label" for="scheduleTable">
                Work schedule</label>
            <table class="table" id="scheduleTable">
                <tr>
                    <th>Day</th>
                    <th>Start time</th>
                    <th>End time</th>
                </tr>
                <tr>
                    <td>Monday</td>
                    <td><input type="text" name="mondayStartTime" maxlength="5" placeholder="00:00"></td>
                    <td><input type="text" name="mondayEndTime" maxlength="5" placeholder="23:59"></td>
                </tr>
                <tr>
                    <td>Tuesday</td>
                    <td><input type="text" name="tuesdayStartTime" maxlength="5" placeholder="00:00"></td>
                    <td><input type="text" name="tuesdayEndTime" maxlength="5" placeholder="23:59"></td>
                </tr>
                <tr>
                    <td>Thursday</td>
                    <td><input type="text" name="thursdayStartTime" maxlength="5" placeholder="00:00"></td>
                    <td><input type="text" name="thursdayEndTime" maxlength="5" placeholder="23:59"></td>
                </tr>
                <tr>
                    <td>Wednesday</td>
                    <td><input type="text" name="wednesdayStartTime" maxlength="5" placeholder="00:00"></td>
                    <td><input type="text" name="wednesdayEndTime" maxlength="5" placeholder="23:59"></td>
                </tr>
                <tr>
                    <td>Friday</td>
                    <td><input type="text" name="fridayStartTime" maxlength="5" placeholder="00:00"></td>
                    <td><input type="text" name="fridayEndTime" maxlength="5" placeholder="23:59"></td>
                </tr>
                <tr>
                    <td>Saturday</td>
                    <td><input type="text" name="saturdayStartTime" maxlength="5" placeholder="00:00"></td>
                    <td><input type="text" name="saturdayEndTime" maxlength="5" placeholder="23:59"></td>
                </tr>
                <tr>
                    <td>Sunday</td>
                    <td><input type="text" name="sundayStartTime" maxlength="5" placeholder="00:00"></td>
                    <td><input type="text" name="sundayEndTime" maxlength="5" placeholder="23:59"></td>
                </tr>
            </table>
            <br>
            <input class="submit-form" type="submit" value="Add driver">
        </form>
    </div>
</div>
</body>
</html>