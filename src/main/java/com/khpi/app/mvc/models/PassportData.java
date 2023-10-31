package com.khpi.app.mvc.models;

import lombok.*;
import jakarta.persistence.*;

@Entity
@EqualsAndHashCode(of = {"fullName", "passportNumber"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name="passport_data")
public class PassportData {
    @Id
    @Column(name = "passport_data_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Column(name="full_name", length = 80)
    private @NonNull String fullName;

    @Column(name = "passport_number", length = 10)
    private @NonNull String passportNumber;
}
