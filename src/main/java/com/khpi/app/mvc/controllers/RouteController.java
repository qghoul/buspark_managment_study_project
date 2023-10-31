package com.khpi.app.mvc.controllers;

import com.khpi.app.mvc.service.RouteService;
import com.khpi.app.mvc.service.ValidateService;
import com.khpi.app.mvc.models.Route;
import com.khpi.app.mvc.repositories.RouteRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/routes", "/"})
public class RouteController {
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    RouteService routeService;
    @GetMapping
    public String getAllRoutes(Model model){
        List<Route> routes = routeRepository.findAllByOrderById();
        model.addAttribute("routes", routes);
        Integer totalDuration = routeRepository.totalDuration();
        model.addAttribute("totalDuration", totalDuration);
        return "allRoutes";
    }
    @GetMapping("/timeMode")
    public String onOnlyTimeMode(Model model){
        List<Route> routes = routeRepository.findAllByOrderByRouteNumber();
        model.addAttribute("routes", routes);
        return "timeMode";
    }
    @GetMapping("/update")
    public String showUpdateRouteForm(Model model){ return "updateRouteForm"; }
    @PostMapping("/update")
    public String updateRoute(@RequestParam("routeNumber") String routeNumber,
                              @RequestParam(value = "startLocation", required = false) String startLocation,
                              @RequestParam(value = "endLocation", required = false) String endLocation,
                              @RequestParam(value = "startTime", required = false) String startTime,
                              @RequestParam(value = "endTime", required = false) String endTime,
                              @RequestParam(value = "travelInterval", required = false) Integer travelInterval,
                              @RequestParam(value = "duration", required = false) Integer duration,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        Optional<Route> routeOptional = routeRepository.findByRouteNumberIgnoreCase(routeNumber);
        if (routeOptional.isEmpty()) {
            model.addAttribute("errorMessage", "Update Error: Incorrect Route number");
            return "updateRouteForm";
        }
        Route route = routeOptional.get();
        if(StringUtils.isNotBlank(startLocation)) { route.setStartLocation(startLocation); }
        if(StringUtils.isNotBlank(endLocation)) { route.setEndLocation(endLocation); }
        if(StringUtils.isNotBlank(startTime)) {
            if (ValidateService.validateTime(startTime)) {
                route.setStartTime(LocalTime.parse(startTime));
            }
            else {
                model.addAttribute("errorMessage", "Update Error: Incorrect Time format");
                return "updateRouteForm";
            }
        }
        if(StringUtils.isNotBlank(endTime)){
            if (ValidateService.validateTime(endTime)) {
                route.setEndTime(LocalTime.parse(endTime));
            }
            else {
                model.addAttribute("errorMessage", "Update Error: Incorrect Time format");
                return "updateRouteForm";
            }
        }
        if(travelInterval != null) { route.setTravelInterval(travelInterval); }
        if(duration != null) { route.setDuration(duration); }
        routeService.saveRoute(route);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction ssuccessful complete");
        return "redirect:/routes";
    }
    // Need to Impl Add get and Post request
    @GetMapping("/add")
    public String showAddRouteForm(Model model){ return "addRouteForm"; }
    @PostMapping("/add")
    public String addRoute(@RequestParam("routeNumber") String routeNumber,
                           @RequestParam("startLocation") String startLocation,
                           @RequestParam("endLocation") String endLocation,
                           @RequestParam("startTime") String startTime,
                           @RequestParam("endTime") String endTime,
                           @RequestParam("travelInterval") Integer travelInterval,
                           @RequestParam("duration") Integer duration,
                           Model model,
                           RedirectAttributes redirectAttributes){
        Optional<Route> routeOptional = routeRepository.findByRouteNumberIgnoreCase(routeNumber);
        if(routeOptional.isPresent()) {
            model.addAttribute("errorMessage", "Add Error: Route with this Route number already in DB");
            return "addRouteForm";
        }
        Route route = new Route();
        if(ValidateService.validateDayTimes(startTime, endTime)){
            route.setStartTime(LocalTime.parse(startTime));
            route.setEndTime(LocalTime.parse(endTime));
        }
        else {
            model.addAttribute("errorMessage", "Add Error: Incorrect Time format");
            return "addRouteForm";
        }

        route.setRouteNumber(routeNumber);
        route.setStartLocation(startLocation);
        route.setEndLocation(endLocation);
        route.setTravelInterval(travelInterval);
        route.setDuration(duration);
        routeService.saveRoute(route);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction successful complete");
        return "redirect:/routes";
    }

    @PostMapping("/deleteById")
    public String deleteRouteById(@RequestParam("routeId") Integer routeId,
                                  RedirectAttributes redirectAttributes,
                                  Model model){
        Optional<Route> route = routeRepository.findById(routeId);
        if(route.isEmpty()){
            model.addAttribute("errorMessage", "Delete Error: Incorrect Route ID");
            List<Route> routes = routeRepository.findAll();
            model.addAttribute("routes", routes);
            Integer totalDuration = routeRepository.totalDuration();
            model.addAttribute("totalDuration", totalDuration);
            return "allRoutes";
        }
        routeService.deleteRoute(route.get());
        redirectAttributes.addFlashAttribute("successMessage", "Transaction successful complete");
        return "redirect:/routes";
    }

    @GetMapping("/searchByLocation")
    public String searchByLocation(@RequestParam("location") String location, Model model){
        List<Route> routes = routeRepository.findRoutesByLocation(location);
        if(routes.isEmpty()){
            model.addAttribute("errorMessage", "Search Error: Incorrect Location");
        }
        model.addAttribute("location", location);
        model.addAttribute("routes", routes);
        Integer totalDuration = routeRepository.totalDuration();
        model.addAttribute("totalDuration", totalDuration);
        return "allRoutes";
    }

    @GetMapping("/time")
    public String getTimeInfo(Model model){
        List<Route> routes = routeRepository.findAll();
        model.addAttribute("routes", routes);
        return "routesTimeInfo";
    }

    @GetMapping("/getDurationByRouteNumber")
    public String getRouteDurationByRouteNumber(@RequestParam("routeNumber") String routeNumber,
                                                Model model){
        Optional<Route> routeOptional = routeRepository.findByRouteNumberIgnoreCase(routeNumber);
        if(routeOptional.isEmpty()) {
            model.addAttribute("durationByRouteNumberError", "Error: Incorrect Route Number");
        }
        else {
            model.addAttribute("durationByRouteNumber", "Route " + routeOptional.get().getRouteNumber()
                    + " has duration " + routeOptional.get().getDuration() + " minutes");
        }
        model.addAttribute("routeNumber", routeNumber);
        List<Route> routes = routeRepository.findAll();
        model.addAttribute("routes", routes);
        Integer totalDuration = routeRepository.totalDuration();
        model.addAttribute("totalDuration", totalDuration);
        return "allRoutes";
    }
}
