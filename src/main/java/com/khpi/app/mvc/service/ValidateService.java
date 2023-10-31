package com.khpi.app.mvc.service;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ValidateService {
    static public boolean validateDayTimes(String startTime, String endTime) {
        try {
            // Попытка преобразовать строку в LocalTime
            LocalTime.parse(startTime);
            LocalTime.parse(endTime);
            return true; // Вернет true, если преобразование успешно
        } catch (DateTimeParseException e) {
            return false; // Вернет false, если преобразование не удалось
        }
    }
    static public boolean validateTime(String time){
        try{
            LocalTime.parse(time);
            return true;
        } catch (DateTimeParseException e){
            return false;
        }
    }
}
