package com.telemetry.vehicle_api.service;

import com.telemetry.vehicle_api.dto.SpeedAlertDTO;
import com.telemetry.vehicle_api.dto.SpeedThresholdRequestDTO;
import com.telemetry.vehicle_api.dto.TripEventDTO;
import com.telemetry.vehicle_api.model.Vehicle;
import com.telemetry.vehicle_api.repository.SpeedAlertRepository;
import com.telemetry.vehicle_api.repository.TelemetryRepository;
import com.telemetry.vehicle_api.repository.VehicleRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TelemetryRepository telemetryRepository;
    private final SpeedAlertRepository speedAlertRepository;
    private final VehicleRepository vehicleRepository;

    public List<TripEventDTO> getTripHistory(String plate, LocalDateTime from, LocalDateTime to) {
        Vehicle vehicle = findVehicle(plate);

        return telemetryRepository
                .findByVehicleAndRecordedAtBetweenOrderByRecordedAtAsc(vehicle, from, to)
                .stream()
                .map(e -> TripEventDTO.builder()
                        .id(e.getId())
                        .latitude(e.getLatitude())
                        .longitude(e.getLongitude())
                        .speedKmh(e.getSpeedKmh())
                        .fuelPercent(e.getFuelPercent())
                        .engineRpm(e.getEngineRpm())
                        .recordedAt(e.getRecordedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<SpeedAlertDTO> getAlerts(String plate) {
        Vehicle vehicle = findVehicle(plate);

        return speedAlertRepository
                .findByVehicleOrderByRecordedAtDesc(vehicle)
                .stream()
                .map(a -> SpeedAlertDTO.builder()
                        .id(a.getId())
                        .plate(plate)
                        .speedKmh(a.getSpeedKmh())
                        .thresholdKmh(a.getThresholdKmh())
                        .latitude(a.getLatitude())
                        .longitude(a.getLongitude())
                        .recordedAt(a.getRecordedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public void updateSpeedThreshold(String plate, SpeedThresholdRequestDTO request) {
        Vehicle vehicle = findVehicle(plate);
        vehicle.setSpeedThresholdKmh(request.getThresholdKmh());
        vehicleRepository.save(vehicle);
    }

    private Vehicle findVehicle(String plate) {
        return vehicleRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + plate));
    }
}
