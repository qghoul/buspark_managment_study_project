<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/static/css/styles.css" rel="stylesheet"/>
    <title>Update Bus info</title>
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
        <form id="updateBusForm" class="addForm" action="/buses/update" method="post">
            <div class="text-field">
                <label class="text-field__label" for="busId">Enter Bus ID to update:</label>
                <input class="text-field__input" type="number" id="busId" name="busId" required>
            </div>

            <div class="fillInInstruction">Fill in the fields to be updated:</div>

            <div class="text-field">
                <label class="text-field__label" for="driverId">Driver ID:</label>
                <input class="text-field__input" type="number" id="driverId" name="driverId">
            </div>
            <div class="text-field">
                <label class="text-field__label" for="routeNumber">Route Number:</label>
                <input class="text-field__input" type="text" id="routeNumber" name="routeNumber" maxlength="8">
            </div>
            <div class="text-field">
                <label class="text-field__label" for="registrationNumber">License number:</label>
                <input class="text-field__input" type="text" id="registrationNumber" name="registrationNumber"
                       placeholder="AA1234ББ" maxlength="8" pattern="[А-Я]{2}\d{4}[А-Я]{2}">
            </div>
            <span class="updateInstruction" id="registrationNumberError"></span>
            <span class="error-message" id="registrationNumberError"></span>
            <div class="text-field">
                <label class="text-field__label" for="capacity">Capacity:</label>
                <input class="text-field__input" type="number" id="capacity" name="capacity">
            </div>
            <div class="text-field">
                <label class="text-field__label" for="imageUrl">Image URL:</label>
                <input class="text-field__input" type="text" id="imageUrl" name="imageUrl">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <br>
            <input class="submit-form" type="submit" value="Update Bus info">
        </form>
        <script>
            // Получаем элементы
            const registrationNumberInput = document.getElementById("registrationNumber");
            const registrationNumberError = document.getElementById("registrationNumberError");

            // Функция для проверки формата регистрационного номера
            function validateRegistrationNumber() {
                const value = registrationNumberInput.value.trim(); // Удаляем лишние пробелы
                if (value === "") {
                    registrationNumberError.textContent = ""; // Если поле пустое, очищаем ошибку
                    registrationNumberInput.setCustomValidity(""); // Сбрасываем кастомную валидность
                    return;
                }
                const pattern = /^[А-Я]{2}\d{4}[А-Я]{2}$/;
                if (!pattern.test(registrationNumberInput.value)) {
                    registrationNumberError.textContent = "Введите номер в формате АА1234ББ.";
                    registrationNumberInput.setCustomValidity("Invalid registration number format.");
                } else {
                    registrationNumberError.textContent = "";
                    registrationNumberInput.setCustomValidity("");
                }
            }

            // Добавляем обработчик события на изменение поля ввода
            registrationNumberInput.addEventListener("input", validateRegistrationNumber);
        </script>
    </div>
</div>
</body>
</html>