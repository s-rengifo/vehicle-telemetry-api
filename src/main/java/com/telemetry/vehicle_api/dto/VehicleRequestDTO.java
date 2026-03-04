package com.telemetry.vehicle_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehicleRequestDTO {

    @NotBlank(message = "Plate is required")
    private String plate;

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;
}
