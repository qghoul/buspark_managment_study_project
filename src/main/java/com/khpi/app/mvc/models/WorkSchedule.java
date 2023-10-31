package com.khpi.app.mvc.models;

import lombok.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(of = "work_schedule_id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name="work_schedule")
public class WorkSchedule {
    @Id
    @Column(name = "work_schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "workSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkDay> workDays = new ArrayList<>();
}
