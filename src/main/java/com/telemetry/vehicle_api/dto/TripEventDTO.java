package com.telemetry.vehicle_api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class TripEventDTO {
    private Long id;
    private Double latitude;
    private Double longitude;
    private Double speedKmh;
    private Double fuelPercent;
    private Integer engineRpm;
    private LocalDateTime recordedAt;
}