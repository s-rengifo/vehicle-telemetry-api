package com.telemetry.vehicle_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpeedThresholdRequestDTO {

    @NotNull
    @DecimalMin("0.0")
    private Double thresholdKmh;
}
