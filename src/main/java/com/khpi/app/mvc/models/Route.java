package com.khpi.app.mvc.models;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@EqualsAndHashCode(of = "routeNumber")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name="route")
public class Route {
    @Id
    @Column(name = "route_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "route_number",length = 5, unique = true)
    private @NonNull String routeNumber;

    @Column(name = "start_location", length = 80)
    private @NonNull String startLocation;

    @Column(name = "end_location", length = 80)
    private @NonNull String endLocation;

    @Column(name = "start_time")
    private @NonNull LocalTime startTime;

    @Column(name = "end_time")
    private @NonNull LocalTime endTime;

    @Column(name = "travel_interval")
    private @NonNull Integer  travelInterval;

    @Column
    private @NonNull Integer duration;

    @PrePersist
    private void normalizeRouteNumber() {
        if (routeNumber != null) {
            routeNumber = routeNumber.toLowerCase(); // Нормализация к нижнему регистру (вы можете использовать toUpperCase() для верхнего регистра)
        }
    }
}
