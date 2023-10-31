package com.khpi.app.mvc.models;

import lombok.*;
import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name="work_day")
public class WorkDay {
    @Id
    @Column(name = "work_day_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "work_schedule_id")
    private WorkSchedule workSchedule;

    @Column(name = "day_of_week")
    private @NonNull DayOfWeek dayOfWeek;

    @Column(name = "start_time")
    private @NonNull LocalTime startTime;

    @Column(name = "end_time")
    private @NonNull LocalTime endTime;

    public WorkDay(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
