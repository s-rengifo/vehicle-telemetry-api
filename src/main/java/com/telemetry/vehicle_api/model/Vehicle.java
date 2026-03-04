package com.telemetry.vehicle_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String plate;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String vehicleType; // CAR, TRUCK...

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    @PrePersist
    public void prePersist(){
        this.registeredAt = LocalDateTime.now();
    }
}
