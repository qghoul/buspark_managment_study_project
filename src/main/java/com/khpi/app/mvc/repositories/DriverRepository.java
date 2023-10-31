package com.khpi.app.mvc.repositories;

import com.khpi.app.mvc.models.Bus;
import com.khpi.app.mvc.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository
    extends JpaRepository<Driver, Integer>
{
    /*@Query(value = "SELECT DISTINCT d.* " +
            "FROM driver d " +
            "JOIN bus b ON d.bus_id = b.bus_id " +
            "JOIN route r ON b.route_id = r.route_id " +
            "JOIN work_schedule ws ON d.work_schedule_id = ws.work_schedule_id " +
            "JOIN work_day wd ON ws.work_schedule_id = wd.work_schedule_id " +
            "WHERE r.route_number = :routeNumber", nativeQuery = true)
    List<Driver> findDriversByRoute(@Param("routeNumber") String routeNumber);*/

    @Query(nativeQuery = true, value =
            "SELECT * FROM driver d " +
                    "WHERE d.bus_id IN (" +
                    "SELECT b.bus_id FROM bus b " +
                    "WHERE b.route_id = (" +
                    "SELECT r.route_id FROM route r WHERE r.route_number = :routeNumber" +
                    ")" +
                    ")"
    )
    List<Driver> findDriversByRoute(@Param("routeNumber") String routeNumber);
    List<Driver> findAllByOrderById();

    @Query(value = "SELECT COUNT(*) FROM passport_data WHERE passport_number = :passportNumber", nativeQuery = true)
    Integer checkPassportNumber(@Param("passportNumber") String passportNumber);

    /*@Query("SELECT d, ws, wd " +
            "FROM Driver d " +
            "JOIN d.workSchedule ws " +
            "JOIN ws.workDays wd " +
            "WHERE d.bus.route = :route")
    List<Object[]> findDriversByRoute(@Param("route") String route);*/
}
