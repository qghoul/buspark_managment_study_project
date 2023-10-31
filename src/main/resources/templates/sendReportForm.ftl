<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/static/css/styles.css" rel="stylesheet"/>
    <title>Send report</title>
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
        <form class="addForm" action="/buses/send_report" method="post">
            <div class="text-field">
                <label class="text-field__label" for="busId">Bus ID:</label>
                <input class="text-field__input" type="number" id="busId" name="busId" required>
            </div>
            <div class="text-field">
                <label class="text-field__label" for="issueDate">Issue Date:</label>
                <input class="text-field__input" type="text" id="issueDate" name="issueDate"
                       placeholder="YYYY-MM-DD" maxlength="12" required pattern="\d{4}-\d{2}-\d{2}">
            </div>
            <span class="errorMessage" id="issueDateError"></span>
            <div class="text-field">
                <label class="text-field__label" for="reason">Reason:</label>
                <input class="text-field__input" type="text" id="reason" name="reason" required>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <br>
            <input class="submit-form" type="submit" value="Submit">
        </form>
        <script>

            // Получаем элементы
            const issueDateInput = document.getElementById("issueDate");
            const issueDateError = document.getElementById("issueDateError");

            // Функция для проверки формата даты
            function validateIssueDate() {
                const pattern = /^\d{4}-\d{2}-\d{2}$/;
                if (!pattern.test(issueDateInput.value)) {
                    issueDateError.textContent = "Введите дату в формате YYYY-MM-DD.";
                    issueDateInput.setCustomValidity("Invalid date format.");
                } else {
                    issueDateError.textContent = "";
                    issueDateInput.setCustomValidity("");
                }
            }

            // Добавляем обработчик события на изменение поля ввода
            issueDateInput.addEventListener("input", validateIssueDate);
        </script>
    </div>
</div>
</body>
</html>