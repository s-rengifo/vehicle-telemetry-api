package com.telemetry.vehicle_api.controller;

import com.telemetry.vehicle_api.dto.SpeedAlertDTO;
import com.telemetry.vehicle_api.dto.SpeedThresholdRequestDTO;
import com.telemetry.vehicle_api.dto.TripEventDTO;
import com.telemetry.vehicle_api.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping("/{plate}/trips")
    public ResponseEntity<List<TripEventDTO>> getTripHistory(
            @PathVariable String plate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(tripService.getTripHistory(plate, from, to));
    }

    @GetMapping("/{plate}/alerts")
    public ResponseEntity<List<SpeedAlertDTO>> getAlerts(@PathVariable String plate) {
        return ResponseEntity.ok(tripService.getAlerts(plate));
    }

    @PatchMapping("/{plate}/alerts/config")
    public ResponseEntity<Void> updateThreshold(
            @PathVariable String plate,
            @Valid @RequestBody SpeedThresholdRequestDTO request) {
        tripService.updateSpeedThreshold(plate, request);
        return ResponseEntity.noContent().build();
    }
}