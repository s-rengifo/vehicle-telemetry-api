package com.telemetry.vehicle_api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TelemetryRequestDTO {

    @NotBlank(message = "Plate is required")
    private String plate;

    @NotNull
    @DecimalMin("-90.0") @DecimalMax("90.0")
    private Double latitude;

    @NotNull
    @DecimalMin("-180.0") @DecimalMax("180.0")
    private Double longitude;

    @NotNull
    @DecimalMin("0.0")
    private Double speedKmh;

    @NotNull
    @DecimalMin("0.0") @DecimalMax("100.0")
    private Double fuelPercent;

    @NotNull
    @Min(0) @Max(10000)
    private Integer engineRpm;

    @NotNull
    private LocalDateTime recordedAt;
}
