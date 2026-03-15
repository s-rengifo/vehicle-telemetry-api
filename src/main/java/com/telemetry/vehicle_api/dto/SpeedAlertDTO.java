package com.telemetry.vehicle_api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class SpeedAlertDTO {
    private Long id;
    private String plate;
    private Double speedKmh;
    private Double thresholdKmh;
    private Double latitude;
    private Double longitude;
    private LocalDateTime recordedAt;
}