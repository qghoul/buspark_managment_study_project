package com.khpi.app.mvc.service;
import com.khpi.app.mvc.models.Driver;
import com.khpi.app.mvc.models.Route;
import com.khpi.app.mvc.repositories.DriverRepository;
import com.khpi.app.mvc.repositories.RouteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
    @Autowired
    RouteRepository routeRepository;

    @Transactional
    public void saveRoute(Route route){ routeRepository.save(route); };

    @Transactional
    public void deleteRoute(Route route){ routeRepository.delete(route); }
}
