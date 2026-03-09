package com.telemetry.vehicle_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LatestPositionDTO implements Serializable {
    private String plate;
    private Double latitude;
    private Double longitude;
    private Double speedKmh;
    private LocalDateTime recordedAt;
}