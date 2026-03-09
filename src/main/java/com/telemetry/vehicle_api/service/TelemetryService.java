package com.telemetry.vehicle_api.service;

import com.telemetry.vehicle_api.dto.*;
import com.telemetry.vehicle_api.model.TelemetryEvent;
import com.telemetry.vehicle_api.model.Vehicle;
import com.telemetry.vehicle_api.repository.TelemetryRepository;
import com.telemetry.vehicle_api.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;
    private final VehicleRepository vehicleRepository;
    private final RedisTemplate<String, LatestPositionDTO> redisTemplate;

    private static final String REDIS_KEY_PREFIX = "vehicle:location:";

    public TelemetryResponseDTO ingest(TelemetryRequestDTO request) {
        // 1. Find the vehicle
        Vehicle vehicle = vehicleRepository.findByPlate(request.getPlate())
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + request.getPlate()));

        // 2. Save event to PostgreSQL
        TelemetryEvent event = TelemetryEvent.builder()
                .vehicle(vehicle)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .speedKmh(request.getSpeedKmh())
                .fuelPercent(request.getFuelPercent())
                .engineRpm(request.getEngineRpm())
                .recordedAt(request.getRecordedAt())
                .build();

        TelemetryEvent saved = telemetryRepository.save(event);

        // 3. Update latest position in Redis
        LatestPositionDTO latestPosition = LatestPositionDTO.builder()
                .plate(vehicle.getPlate())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .speedKmh(request.getSpeedKmh())
                .recordedAt(request.getRecordedAt())
                .build();

        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + vehicle.getPlate(), latestPosition);

        return TelemetryResponseDTO.builder()
                .id(saved.getId())
                .plate(vehicle.getPlate())
                .latitude(saved.getLatitude())
                .longitude(saved.getLongitude())
                .speedKmh(saved.getSpeedKmh())
                .fuelPercent(saved.getFuelPercent())
                .engineRpm(saved.getEngineRpm())
                .recordedAt(saved.getRecordedAt())
                .build();
    }

    public LatestPositionDTO getLatestPosition(String plate) {
        // 1. Try Redis first (fast path)
        LatestPositionDTO cached = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + plate);
        if (cached != null) return cached;

        // 2. Fallback to PostgreSQL if not in cache
        Vehicle vehicle = vehicleRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + plate));

        return telemetryRepository.findByVehicleOrderByRecordedAtDesc(vehicle)
                .stream()
                .findFirst()
                .map(e -> LatestPositionDTO.builder()
                        .plate(plate)
                        .latitude(e.getLatitude())
                        .longitude(e.getLongitude())
                        .speedKmh(e.getSpeedKmh())
                        .recordedAt(e.getRecordedAt())
                        .build())
                .orElseThrow(() -> new RuntimeException("No telemetry found for: " + plate));
    }
}