package com.khpi.app.mvc.repositories;

import com.khpi.app.mvc.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository
    extends JpaRepository<Route, Integer>
{
    @Query("SELECT r FROM Route r WHERE r.startLocation = :location OR r.endLocation = :location")
    List<Route> findRoutesByLocation(@Param("location") String location);
    Optional<Route> findByRouteNumberIgnoreCase(String routeNumber);
    List<Route> findAllByOrderById();
    List<Route> findAllByOrderByRouteNumber();
    Optional<Route> findRouteByRouteNumber(String routeNumber);
    @Query("SELECT SUM(r.duration) FROM Route r")
    Integer totalDuration();
}
