package com.khpi.app.mvc.models;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name="unavailable_bus_details")
public class UnavailableBusDetails {
    @Id
    @Column(name = "unavailable_bus_details_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @Column
    private String reason;

    @Column(name = "issue_date")
    private LocalDate issueDate;
}
