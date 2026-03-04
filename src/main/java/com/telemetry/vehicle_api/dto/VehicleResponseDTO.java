package com.telemetry.vehicle_api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VehicleResponseDTO {
    private Long id;
    private String plate;
    private String ownerName;
    private String vehicleType;
    private LocalDateTime registeredAt;
}
