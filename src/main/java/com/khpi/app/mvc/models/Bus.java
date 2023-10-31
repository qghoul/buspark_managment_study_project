package com.khpi.app.mvc.models;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.*;

@Entity
@EqualsAndHashCode(of = "registrationNumber")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "driver")
@Table(name="bus")
public class Bus {
    @Id
    @Column(name = "bus_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     //maybe should add CascadeType.PERSIST
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bus", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "route_id")
    private Route route;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bus", cascade = CascadeType.ALL)
    private UnavailableBusDetails unavailableBusDetails;

    @Column(name = "registration_number", length = 8)
    private @NonNull String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "bus_type", length = 20)
    private @NonNull BusType busType;

    @Column
    private @NonNull Integer capacity;

    @Column(name = "image_url", columnDefinition = "varchar(255) default 'https://st.depositphotos.com/1007330/4001/i/600/depositphotos_40017959-stock-photo-bus-isolated-on-white-background.jpg'")
    private String imageUrl;

    @Column(name = "available")
    private boolean available = true; // Значение true по умолчанию

    public String getAvailabilityString() {
        return available ? "Available" : "Unavailable";
    }
}
