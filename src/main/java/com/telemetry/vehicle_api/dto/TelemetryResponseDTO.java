package com.telemetry.vehicle_api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TelemetryResponseDTO {
    private Long id;
    private String plate;
    private Double latitude;
    private Double longitude;
    private Double speedKmh;
    private Double fuelPercent;
    private Integer engineRpm;
    private LocalDateTime recordedAt;
}