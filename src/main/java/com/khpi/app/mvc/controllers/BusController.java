package com.khpi.app.mvc.controllers;

import com.khpi.app.mvc.repositories.DriverRepository;
import com.khpi.app.mvc.models.Bus;
import com.khpi.app.mvc.models.UnavailableBusDetails;
import com.khpi.app.mvc.models.Driver;
import com.khpi.app.mvc.models.Route;
import com.khpi.app.mvc.repositories.BusRepository;
import com.khpi.app.mvc.repositories.RouteRepository;
import com.khpi.app.mvc.repositories.UnavailableBusDetailsRepository;
import com.khpi.app.mvc.service.BusService;
import com.khpi.app.mvc.service.DriverService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/buses")
public class BusController {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private BusService busService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private UnavailableBusDetailsRepository unavailableBusDetailsRepository;

    @GetMapping
    public String getAllBuses(Model model) {
        List<Bus> buses = busRepository.findAllByOrderById();
        model.addAttribute("buses", buses);
        return "allBuses";
    }
    @GetMapping("/update")
    public String showUpdateBusForm(Model model) {
        return "updateBusForm";
    }
    @PostMapping("/update")
    public String updateBus(@RequestParam("busId") Integer busId,
                            @RequestParam(value = "driverId", required = false) Integer driverId,
                            @RequestParam(value = "routeNumber", required = false) String routeNumber,
                            @RequestParam(value = "registrationNumber", required = false) String registrationNumber,
                            @RequestParam(value = "capacity", required = false) Integer capacity,
                            @RequestParam(value = "imageUrl", required = false) String imageUrl,
                            RedirectAttributes redirectAttributes,
                            Model model){

        if(StringUtils.isNotBlank(registrationNumber)){
            Optional <Bus> busInBd = busRepository.findByRegistrationNumber(registrationNumber);
            if(busInBd.isPresent()){
                model.addAttribute("errorMessage", "Update Error: License number already used");
                return "updateBusForm";
        }
        }
        Optional<Bus> busOptional = busRepository.findById(busId);
        if(busOptional.isEmpty()){
            model.addAttribute("errorMessage", "Update Error: Incorrect Bus ID");
            return "updateBusForm";
        }
        Bus bus = busOptional.get();
        if(driverId != null){
            Optional<Driver> driverOptional = driverRepository.findById(driverId);
            if(driverOptional.isEmpty()){
                model.addAttribute("errorMessage", "Update Error: Incorrect Driver ID");
                return "updateBusForm";
            }
            if(bus.getDriver() != null) {
                Driver oldDriver = bus.getDriver();
                oldDriver.setBus(null);
                driverService.saveWithAutoSalaryIdentity(oldDriver);
            }
            Driver driver = driverOptional.get();
            driver.setBus(bus);
            driver = driverService.saveWithAutoSalaryIdentity(driver);
            bus.setDriver(driver);
        }
        if(StringUtils.isNotBlank(routeNumber)){
            Optional<Route> route = routeRepository.findByRouteNumberIgnoreCase(routeNumber);
            if(route.isEmpty()){
                model.addAttribute("errorMessage", "Update Error: Incorrect Route number");
                return "updateBusForm";
            }
            bus.setRoute(route.get());
        }
        if(StringUtils.isNotBlank(registrationNumber)){
            bus.setRegistrationNumber(registrationNumber);
        }
        if(capacity != null){ bus.setCapacity(capacity); }
        if(StringUtils.isNotBlank(imageUrl)){ bus.setImageUrl(imageUrl); }
        if(driverId != null){
            Optional<Driver> driverOptional = driverRepository.findById(driverId);
            if(driverOptional.isEmpty()){
                model.addAttribute("errorMessage", "Update Error: Incorrect Driver ID");
                return "updateBusForm";
            }
            Driver driver = driverOptional.get();
            bus.setDriver(driver);
            driver.setBus(bus);
        }
        busService.saveWithAutoTypeIdentity(bus);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction successful complete");
        return "redirect:/buses";
    }
    @GetMapping("/add")
    public String showAddBusForm(Model model) {
        return "addBusForm"; // Это представление для отображения формы
    }
    @PostMapping("/add")
    public String addBus(@RequestParam(value = "driverId", required = false) Integer driverId,
                         @RequestParam("routeNumber") String routeNumber,
                         @RequestParam("registrationNumber") String registrationNumber,
                         @RequestParam("capacity") Integer capacity,
                         @RequestParam(value = "imageUrl", required = false) String imageUrl,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        Optional <Bus> busInBd = busRepository.findByRegistrationNumber(registrationNumber);
        if(busInBd.isPresent()){
            model.addAttribute("errorMessage", "Add Error: License number already used");
            return "addBusForm";
        }
        Bus bus = new Bus();
        Optional <Route> route = routeRepository.findByRouteNumberIgnoreCase(routeNumber);
        if(route.isEmpty()){
            model.addAttribute("errorMessage", "Add Error: Incorrect Route Number");
            return "addBusForm";
        }
        bus.setRoute(route.get());
        if(StringUtils.isNotBlank(imageUrl)){
            bus.setImageUrl(imageUrl);
        }
        else{
            bus.setImageUrl("https://st.depositphotos.com/1007330/4001/i/600/depositphotos_40017959-stock-photo-bus-isolated-on-white-background.jpg");
        }
        if(registrationNumber.length() != 8){
            model.addAttribute("errorMessage", "Add Error: Incorrect Registration number format");
            return "addBusForm";
        }
        bus.setRegistrationNumber(registrationNumber);
        bus.setCapacity(capacity);
        if(driverId != null){
            /*Driver currentDriver = bus.getDriver();
            if (currentDriver != null) {
                currentDriver.setBus(null);
                driverService.saveWithAutoSalaryIdentity(currentDriver);
            }*/
            Optional<Driver> driverOptional = driverRepository.findById(driverId);
            if(driverOptional.isEmpty()){
                model.addAttribute("errorMessage", "Update Error: Incorrect Driver ID");
                return "updateBusForm";
            }
            Driver driver = driverOptional.get();
            bus.setDriver(driver);
            driver.setBus(bus);
        }
        busService.saveWithAutoTypeIdentity(bus);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction successful complete");
        return "redirect:/buses";
    }

    @PostMapping("/deleteById")
    public String deleteBusById(@RequestParam("busId") Integer busId,
                                Model model,
                                RedirectAttributes redirectAttributes){
        Optional<Bus> bus = busRepository.findById(busId);
        if(bus.isEmpty()){
            model.addAttribute("errorMessage", "Delete Error: Incorrect Bus ID");
            List<Bus> buses = busRepository.findAll();
            model.addAttribute("buses", buses);
            return "allBuses";
        }
        busService.deleteBus(bus.get());
        redirectAttributes.addFlashAttribute("successMessage", "Transaction successful complete");
        return "redirect:/buses";
    }

    @GetMapping("/searchByRouteNumber")
    public String searchByRouteNumber(Model model,
                                      @RequestParam("routeNumber") String routeNumber)
    {
        List<Bus> buses = busRepository.findBusesByRouteNumber(routeNumber);
        model.addAttribute("routeNumber", routeNumber);
        if(buses.isEmpty()){
            model.addAttribute("errorMessage", "Search Error: Incorrect Route Number");
        }
        model.addAttribute("buses", buses);
        return "allBuses";
    }

    //Maybe incorrect Impl, should be in "/unavailable_buses" URL directory
    @GetMapping("/unavailable_buses")
    public String getAllUnavailableBuses(Model model){
        List<Bus> unavailableBuses = busRepository.findBusesByAvailableIsFalse();
        model.addAttribute("buses", unavailableBuses);
        return "allUnavailableBuses";
    }
    @GetMapping("/send_report")
    public String showBusUnavailabilityReportForm(Model model){
        return "sendReportForm";
    }
    @PostMapping("/send_report")
    public String sendBusUnavailabilityReport(@RequestParam("busId") Integer busId,
                                              @RequestParam("issueDate") LocalDate issueDate,
                                              @RequestParam("reason") String reason,
                                              RedirectAttributes redirectAttributes,
                                              Model model){
        Optional<Bus> busOptional = busRepository.findById(busId);
        if(busOptional.isEmpty()){
            model.addAttribute("errorMessage", "Error: Incorrect Bus ID");
            return "sendReportForm";
        }
        Bus bus = busOptional.get();
        if(!bus.isAvailable()){
            model.addAttribute("errorMessage", "Error: Bus already unavailable");
            return "sendReportForm";
        }
        UnavailableBusDetails unavailableBusDetails = new UnavailableBusDetails();
        unavailableBusDetails.setIssueDate(issueDate);
        unavailableBusDetails.setReason(reason);
        unavailableBusDetails.setBus(bus);

        bus.setAvailable(false);
        bus.setUnavailableBusDetails(unavailableBusDetails);
        busService.saveWithAutoTypeIdentity(bus);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction successful complete");
        return "redirect:/buses/unavailable_buses";
    }
    @PostMapping("make_available")
    public String makeAvailable(@RequestParam("busId") Integer busId,
                                RedirectAttributes redirectAttributes,
                                Model model){
        Optional<Bus> busOptional = busRepository.findById(busId);
        if(busOptional.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Incorrect Bus ID");
            return "redirect:/unavailable_buses";
        }
        Bus bus = busOptional.get();
        if(bus.isAvailable()){
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Bus already available");
            return "redirect:/unavailable_buses";
        }
        Optional<UnavailableBusDetails> unavailableBusDetailsOptional = unavailableBusDetailsRepository.findFirstByBus_id(busId);
        if(unavailableBusDetailsOptional.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Bus already available");
            return "redirect:/unavailable_buses";
        }
        UnavailableBusDetails unavailableBusDetails = unavailableBusDetailsOptional.get();
        bus.setAvailable(true);
        busService.deleteUnavailableBusDetails(unavailableBusDetails);

        busService.saveWithAutoTypeIdentity(bus);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction successful complete");
        return "redirect:/buses";
    }
}
