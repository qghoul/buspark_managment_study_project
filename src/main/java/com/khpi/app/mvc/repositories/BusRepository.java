package com.khpi.app.mvc.repositories;

import com.khpi.app.mvc.models.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository
    extends JpaRepository<Bus, Integer>
{
    @Query("SELECT b FROM Bus b JOIN b.route r WHERE r.routeNumber = :routeNumber")
    List<Bus> findBusesByRouteNumber(@Param("routeNumber") String routeNumber);
    //Bus save(Bus bus);

    List<Bus> findBusesByAvailableIsFalse();
    List<Bus> findAllByOrderById();
    Optional<Bus> findByRegistrationNumber(String registrationNumber);
}
