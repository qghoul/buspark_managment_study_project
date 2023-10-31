package com.khpi.app.mvc.service;
import com.khpi.app.mvc.models.Driver;
import com.khpi.app.mvc.repositories.DriverRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepository;
    public boolean validateDayTimes(String startTime, String endTime) {
        try {
            // Попытка преобразовать строку в LocalTime
            LocalTime.parse(startTime);
            LocalTime.parse(endTime);
            return true; // Вернет true, если преобразование успешно
        } catch (DateTimeParseException e) {
            return false; // Вернет false, если преобразование не удалось
        }
    }
    @Transactional
    public Driver saveWithAutoSalaryIdentity(Driver driver){
        Integer salary = 7000;
        float experienceMultiplier = (float) (driver.getExperience().floatValue() * 0.05);
        switch (driver.getDriverClass()){
            case A -> salary = (int) ((1 + experienceMultiplier) * 11000);
            case B -> salary = (int) ((1 + experienceMultiplier) * 10000);
            case C -> salary = (int) ((1 + experienceMultiplier) * 9000);
            case D -> salary = (int) ((1 + experienceMultiplier) * 8000);
            case E -> salary = (int) ((1 + experienceMultiplier) * 7000);
        }
        driver.setSalary(salary);
        return driverRepository.save(driver);
    }

    @Transactional
    public void deleteDriver(Driver driver){ driverRepository.delete(driver); }
}
