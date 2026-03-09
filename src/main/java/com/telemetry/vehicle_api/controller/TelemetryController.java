package com.telemetry.vehicle_api.controller;

import com.telemetry.vehicle_api.dto.LatestPositionDTO;
import com.telemetry.vehicle_api.dto.TelemetryRequestDTO;
import com.telemetry.vehicle_api.dto.TelemetryResponseDTO;
import com.telemetry.vehicle_api.service.TelemetryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;

    @PostMapping
    public ResponseEntity<TelemetryResponseDTO> ingest(@Valid @RequestBody TelemetryRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(telemetryService.ingest(request));
    }

    @GetMapping("/location/{plate}")
    public ResponseEntity<LatestPositionDTO> getLatestPosition(@PathVariable String plate) {
        return ResponseEntity.ok(telemetryService.getLatestPosition(plate));
    }
}
