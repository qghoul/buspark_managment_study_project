package com.khpi.app.mvc.models;

import lombok.*;
import jakarta.persistence.*;

@Entity(name = "'driver'")
@EqualsAndHashCode(of = "passportData")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "bus")
@Table(name="driver")
public class Driver {
    @Id
    @Column(name = "driver_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "bus_id", nullable = true)
    private Bus bus;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private WorkSchedule workSchedule;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private PassportData passportData;

    @Enumerated(EnumType.STRING)
    @Column(name = "driver_class",length = 2)
    private @NonNull DriverClass driverClass;

    @Column
    private @NonNull Integer experience;

    @Column
    private @NonNull Integer salary;
}
