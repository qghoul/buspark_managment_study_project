package com.khpi.app.mvc.controllers;

import com.khpi.app.mvc.models.*;
import com.khpi.app.mvc.repositories.DriverRepository;
import com.khpi.app.mvc.repositories.BusRepository;
import com.khpi.app.mvc.service.BusService;
import com.khpi.app.mvc.service.DriverService;
import com.khpi.app.mvc.service.ValidateService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/drivers"})
public class DriverController {
    @Autowired
    DriverRepository driverRepository;
    @Autowired
    BusRepository busRepository;
    @Autowired
    BusService busService;
    @Autowired
    DriverService driverService;


    @GetMapping
    public String getAllDrivers(Model model) {
        List<Driver> drivers = driverRepository.findAllByOrderById();
        model.addAttribute("drivers", drivers);
        return "allDrivers";
    }
    @GetMapping("/update")
    public String showUpdateDriverForm(Model model){return "updateDriverForm";}
    @PostMapping("/update")
    public String updateDriver(@RequestParam("driverId") Integer driverId,
                               @RequestParam(value = "fullName", required = false) String fullName,
                               @RequestParam(value = "busId", required = false) Integer busId,
                               @RequestParam(value = "passportNumber", required = false) String passportNumber,
                               @RequestParam(value = "experience", required = false) Integer experience,
                               @RequestParam(value = "driverClass", required = false) String driverClass,
                               @RequestParam(value = "mondayStartTime", required = false) String mondayStartTime,
                               @RequestParam(value = "mondayEndTime", required = false) String mondayEndTime,
                               @RequestParam(value = "tuesdayStartTime", required = false) String tuesdayStartTime,
                               @RequestParam(value = "tuesdayEndTime", required = false) String tuesdayEndTime,
                               @RequestParam(value = "wednesdayStartTime", required = false) String wednesdayStartTime,
                               @RequestParam(value = "wednesdayEndTime", required = false) String wednesdayEndTime,
                               @RequestParam(value = "thursdayStartTime", required = false) String thursdayStartTime,
                               @RequestParam(value = "thursdayEndTime", required = false) String thursdayEndTime,
                               @RequestParam(value = "fridayStartTime", required = false) String fridayStartTime,
                               @RequestParam(value = "fridayEndTime", required = false) String fridayEndTime,
                               @RequestParam(value = "saturdayStartTime", required = false) String saturdayStartTime,
                               @RequestParam(value = "saturdayEndTime", required = false) String saturdayEndTime,
                               @RequestParam(value = "sundayStartTime", required = false) String sundayStartTime,
                               @RequestParam(value = "sundayEndTime", required = false) String sundayEndTime,
                               RedirectAttributes redirectAttributes,
                               Model model){
        Optional<Driver> driverOptional = driverRepository.findById(driverId);
        if(driverOptional.isEmpty()){
            model.addAttribute("errorMessage", "Update Error: Incorrect Driver ID");
            return "updateDriverForm";
        }
        Driver driver = driverOptional.get();
        if(busId != null) {
            Optional<Bus> bus = busRepository.findById(busId);
            if (bus.isEmpty()) {
                model.addAttribute("errorMessage", "Add Error: Incorrect Bus ID");
                return "addDriverForm";
            }
            if(bus.get().getDriver() != null) {
                Driver oldDriver = bus.get().getDriver();
                oldDriver.setBus(null);
                driverService.saveWithAutoSalaryIdentity(oldDriver);
            }
            driver.setBus(bus.get());
        }
        PassportData passportData = driver.getPassportData();
        if(StringUtils.isNotBlank(fullName)) { passportData.setFullName(fullName); }
        Integer count = driverRepository.checkPassportNumber(passportNumber);
        if(count > 0){
            model.addAttribute("errorMessage", "Add Error: Passport number already used");
            return "updateDriverForm";
        }
        if(StringUtils.isNotBlank(passportNumber)) { passportData.setPassportNumber(passportNumber); }
        driver.setPassportData(passportData);
        if(experience != null) { driver.setExperience(experience); }
        if(StringUtils.isNotBlank(driverClass)) { driver.setDriverClass(DriverClass.valueOf(driverClass));}

        WorkSchedule workSchedule = driver.getWorkSchedule();
        List<WorkDay> existingWorkDays = workSchedule.getWorkDays();
        List<WorkDay> workDays = new ArrayList<>();
        boolean updatesInWorkScheduleBool = false;
        if (StringUtils.isNotBlank(mondayStartTime) && StringUtils.isNotBlank(mondayEndTime)) {
            if (ValidateService.validateDayTimes(mondayStartTime, mondayEndTime)) {
                WorkDay monday = new WorkDay(DayOfWeek.MONDAY, LocalTime.parse(mondayStartTime), LocalTime.parse(mondayEndTime));
                monday.setWorkSchedule(workSchedule);
                workDays.add(monday);
                updatesInWorkScheduleBool = true;
            }
            else {
                model.addAttribute("errorMessage", "Update Error: Incorrect Time format");
                return "updateDriverForm";
            }
        }
        if (StringUtils.isNotBlank(tuesdayStartTime) && StringUtils.isNotBlank(tuesdayEndTime)) {
            if (ValidateService.validateDayTimes(tuesdayStartTime, tuesdayEndTime)) {
                WorkDay tuesday = new WorkDay(DayOfWeek.TUESDAY, LocalTime.parse(tuesdayStartTime), LocalTime.parse(tuesdayEndTime));
                tuesday.setWorkSchedule(workSchedule);
                workDays.add(tuesday);
                updatesInWorkScheduleBool = true;
            }
            else {
                model.addAttribute("errorMessage", "Update Error: Incorrect Time format");
                return "updateDriverForm";
            }
        }
        if(StringUtils.isNotBlank(wednesdayStartTime) && StringUtils.isNotBlank(wednesdayEndTime)){
            if(ValidateService.validateDayTimes(wednesdayStartTime, wednesdayEndTime)){
                WorkDay wednesday = new WorkDay(DayOfWeek.WEDNESDAY, LocalTime.parse(wednesdayStartTime), LocalTime.parse(wednesdayEndTime));
                wednesday.setWorkSchedule(workSchedule);
                workDays.add(wednesday);
                updatesInWorkScheduleBool = true;
            }
            else {
                model.addAttribute("errorMessage", "Update Error: Incorrect Time format");
                return "updateDriverForm";
            }
        }
        if(StringUtils.isNotBlank(thursdayStartTime) && StringUtils.isNotBlank(thursdayEndTime)){
            if(ValidateService.validateDayTimes(thursdayStartTime, thursdayEndTime)){
                WorkDay thursday = new WorkDay(DayOfWeek.THURSDAY, LocalTime.parse(thursdayStartTime), LocalTime.parse(thursdayEndTime));
                thursday.setWorkSchedule(workSchedule);
                workDays.add(thursday);
                updatesInWorkScheduleBool = true;
            }
            else {
                model.addAttribute("errorMessage", "Update Error: Incorrect Time format");
                return "updateDriverForm";
            }
        }
        if(StringUtils.isNotBlank(fridayStartTime) && StringUtils.isNotBlank(fridayEndTime)){
            if(ValidateService.validateDayTimes(fridayStartTime, fridayEndTime)){
                WorkDay friday = new WorkDay(DayOfWeek.FRIDAY, LocalTime.parse(fridayStartTime), LocalTime.parse(fridayEndTime));
                friday.setWorkSchedule(workSchedule);
                workDays.add(friday);
                updatesInWorkScheduleBool = true;
            }
            else {
                model.addAttribute("errorMessage", "Update Error: Incorrect Time format");
                return "updateDriverForm";
            }
        }
        if(StringUtils.isNotBlank(saturdayStartTime) && StringUtils.isNotBlank(saturdayEndTime)){
            if(ValidateService.validateDayTimes(saturdayStartTime, saturdayEndTime)){
                WorkDay saturday = new WorkDay(DayOfWeek.SATURDAY, LocalTime.parse(saturdayStartTime), LocalTime.parse(saturdayEndTime));
                saturday.setWorkSchedule(workSchedule);
                workDays.add(saturday);
                updatesInWorkScheduleBool = true;
            }
            else {
                model.addAttribute("errorMessage", "Update Error: Incorrect Time format");
                return "updateDriverForm";
            }
        }
        if(StringUtils.isNotBlank(sundayStartTime) && StringUtils.isNotBlank(sundayEndTime)){
            if(ValidateService.validateDayTimes(sundayStartTime, sundayEndTime)){
                WorkDay sunday = new WorkDay(DayOfWeek.SUNDAY, LocalTime.parse(sundayStartTime), LocalTime.parse(sundayEndTime));
                sunday.setWorkSchedule(workSchedule);
                workDays.add(sunday);
                updatesInWorkScheduleBool = true;
            }
            else {
                model.addAttribute("errorMessage", "Update Error: Incorrect Time format");
                return "updateDriverForm";
            }
        }
        if(updatesInWorkScheduleBool) {
            if (existingWorkDays != null) {
                existingWorkDays.clear();
            }
            existingWorkDays.addAll(workDays);
            workSchedule.setWorkDays(existingWorkDays);
            driver.setWorkSchedule(workSchedule);
        }
        driverService.saveWithAutoSalaryIdentity(driver);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction successful complete");
        return "redirect:/drivers";
    }

    //Need to Impl Add new driver action (with schedule)
    @GetMapping("/add")
    public String showAddDriverForm(Model model) {
        return "addDriverForm"; // Это представление для отображения формы
    }
    @PostMapping("/add")
    public String addDriver(@RequestParam("fullName") String fullName,
                            @RequestParam(value = "busId", required = false) Integer busId,
                            @RequestParam("passportNumber") String passportNumber,
                            @RequestParam("experience") Integer experience,
                            @RequestParam("driverClass") String driverClass,
                            @RequestParam(value = "mondayStartTime", required = false) String mondayStartTime,
                            @RequestParam(value = "mondayEndTime", required = false) String mondayEndTime,
                            @RequestParam(value = "tuesdayStartTime", required = false) String tuesdayStartTime,
                            @RequestParam(value = "tuesdayEndTime", required = false) String tuesdayEndTime,
                            @RequestParam(value = "wednesdayStartTime", required = false) String wednesdayStartTime,
                            @RequestParam(value = "wednesdayEndTime", required = false) String wednesdayEndTime,
                            @RequestParam(value = "thursdayStartTime", required = false) String thursdayStartTime,
                            @RequestParam(value = "thursdayEndTime", required = false) String thursdayEndTime,
                            @RequestParam(value = "fridayStartTime", required = false) String fridayStartTime,
                            @RequestParam(value = "fridayEndTime", required = false) String fridayEndTime,
                            @RequestParam(value = "saturdayStartTime", required = false) String saturdayStartTime,
                            @RequestParam(value = "saturdayEndTime", required = false) String saturdayEndTime,
                            @RequestParam(value = "sundayStartTime", required = false) String sundayStartTime,
                            @RequestParam(value = "sundayEndTime", required = false) String sundayEndTime,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        Driver driver = new Driver();

        if(busId != null) {
            Optional<Bus> bus = busRepository.findById(busId);
            if (bus.isEmpty()) {
                model.addAttribute("errorMessage", "Add Error: Incorrect Bus ID");
                return "addDriverForm";
            }
            if(bus.get().getDriver() != null) {
                Driver oldDriver = bus.get().getDriver();
                oldDriver.setBus(null);
                driverService.saveWithAutoSalaryIdentity(oldDriver);
            }
            driver.setBus(bus.get());
        }

        driver.setExperience(experience);
        driver.setDriverClass(DriverClass.valueOf(driverClass));

        WorkSchedule workSchedule = new WorkSchedule();
        List<WorkDay> workDays = new ArrayList<>();

        if (StringUtils.isNotBlank(mondayStartTime) && StringUtils.isNotBlank(mondayEndTime)) {
            if (ValidateService.validateDayTimes(mondayStartTime, mondayEndTime)) {
                WorkDay monday = new WorkDay(DayOfWeek.MONDAY, LocalTime.parse(mondayStartTime), LocalTime.parse(mondayEndTime));
                monday.setWorkSchedule(workSchedule);
                workDays.add(monday);
            }
            else {
                model.addAttribute("errorMessage", "Add Error: Incorrect Time format");
                return "addDriverForm";
            }
        }
        if (StringUtils.isNotBlank(tuesdayStartTime) && StringUtils.isNotBlank(tuesdayEndTime)) {
            if (ValidateService.validateDayTimes(tuesdayStartTime, tuesdayEndTime)) {
                WorkDay tuesday = new WorkDay(DayOfWeek.TUESDAY, LocalTime.parse(tuesdayStartTime), LocalTime.parse(tuesdayEndTime));
                tuesday.setWorkSchedule(workSchedule);
                workDays.add(tuesday);
            }
            else {
                model.addAttribute("errorMessage", "Add Error: Incorrect Time format");
                return "addDriverForm";
            }
        }
        if(StringUtils.isNotBlank(wednesdayStartTime) && StringUtils.isNotBlank(wednesdayEndTime)){
            if(ValidateService.validateDayTimes(wednesdayStartTime, wednesdayEndTime)){
                WorkDay wednesday = new WorkDay(DayOfWeek.WEDNESDAY, LocalTime.parse(wednesdayStartTime), LocalTime.parse(wednesdayEndTime));
                wednesday.setWorkSchedule(workSchedule);
                workDays.add(wednesday);
            }
            else {
                model.addAttribute("errorMessage", "Add Error: Incorrect Time format");
                return "addDriverForm";
            }
        }
        if(StringUtils.isNotBlank(thursdayStartTime) && StringUtils.isNotBlank(thursdayEndTime)){
            if(ValidateService.validateDayTimes(thursdayStartTime, thursdayEndTime)){
                WorkDay thursday = new WorkDay(DayOfWeek.THURSDAY, LocalTime.parse(thursdayStartTime), LocalTime.parse(thursdayEndTime));
                thursday.setWorkSchedule(workSchedule);
                workDays.add(thursday);
            }
            else {
                model.addAttribute("errorMessage", "Add Error: Incorrect Time format");
                return "addDriverForm";
            }
        }
        if(StringUtils.isNotBlank(fridayStartTime) && StringUtils.isNotBlank(fridayEndTime)){
            if(ValidateService.validateDayTimes(fridayStartTime, fridayEndTime)){
                WorkDay friday = new WorkDay(DayOfWeek.FRIDAY, LocalTime.parse(fridayStartTime), LocalTime.parse(fridayEndTime));
                friday.setWorkSchedule(workSchedule);
                workDays.add(friday);
            }
            else {
                model.addAttribute("errorMessage", "Add Error: Incorrect Time format");
                return "addDriverForm";
            }
        }
        if(StringUtils.isNotBlank(saturdayStartTime) && StringUtils.isNotBlank(saturdayEndTime)){
            if(ValidateService.validateDayTimes(saturdayStartTime, saturdayEndTime)){
                WorkDay saturday = new WorkDay(DayOfWeek.SATURDAY, LocalTime.parse(saturdayStartTime), LocalTime.parse(saturdayEndTime));
                saturday.setWorkSchedule(workSchedule);
                workDays.add(saturday);
            }
            else {
                model.addAttribute("errorMessage", "Add Error: Incorrect Time format");
                return "addDriverForm";
            }
        }
        if(StringUtils.isNotBlank(sundayStartTime) && StringUtils.isNotBlank(sundayEndTime)){
            if(ValidateService.validateDayTimes(sundayStartTime, sundayEndTime)){
                WorkDay sunday = new WorkDay(DayOfWeek.SUNDAY, LocalTime.parse(sundayStartTime), LocalTime.parse(sundayEndTime));
                sunday.setWorkSchedule(workSchedule);
                workDays.add(sunday);
            }
            else {
                model.addAttribute("errorMessage", "Add Error: Incorrect Time formats");
                return "addDriverForm";
            }
        }
        workSchedule.setWorkDays(workDays);
        workSchedule.setDriver(driver);//
        //workScheduleRepository.save(workSchedule);

        driver.setWorkSchedule(workSchedule);

        Integer count = driverRepository.checkPassportNumber(passportNumber);
        if(count > 0){
            model.addAttribute("errorMessage", "Add Error: Passport number already used");
            return "addDriverForm";
        }
        PassportData passportData = new PassportData();
        passportData.setFullName(fullName);
        passportData.setPassportNumber(passportNumber);
        passportData.setDriver(driver);//
        //passportDataRepository.save(passportData);

        driver.setPassportData(passportData);//

        //driverService.saveWithAutoSalaryIdentityAndSchedule(driver, passportData, workSchedule);
        driverService.saveWithAutoSalaryIdentity(driver);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction successful complete");
        return "redirect:/drivers";
    }

    @PostMapping("/deleteById")
    public String deleteDriverById(@RequestParam("driverId") Integer driverId,
                                   RedirectAttributes redirectAttributes,
                                   Model model){
        Optional<Driver> driver = driverRepository.findById(driverId);
        if(driver.isEmpty()){
            model.addAttribute("errorMessage", "Delete Error: Incorrect Driver ID");
            List<Driver> drivers = driverRepository.findAll();
            model.addAttribute("drivers", drivers);
            return "allDrivers";
        }
        if (driver.get().getBus() != null) {
            Bus bus = driver.get().getBus();
            bus.setDriver(null);
            busService.saveWithAutoTypeIdentity(bus);
        }
        driverService.deleteDriver(driver.get());
        redirectAttributes.addFlashAttribute("successMessage", "Transaction successful complete");
        return "redirect:/drivers";
    }
    @GetMapping("/searchByRouteWithSchedule")
    public String searchDriversByRoute(@RequestParam("routeNumber") String routeNumber,
                                       Model model) {
        List<Driver> drivers = driverRepository.findDriversByRoute(routeNumber);
        model.addAttribute("routeNumber", routeNumber);
        if(drivers.isEmpty()){
            model.addAttribute("drivers", drivers);
            model.addAttribute("errorMessage", "Search Error: Incorrect Route Number");
            return "allDrivers";
        }
        model.addAttribute("drivers", drivers);
        return "driversByRouteWithSchedule";
    }

}
