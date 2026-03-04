package com.telemetry.vehicle_api.controller;

import com.telemetry.vehicle_api.dto.VehicleRequestDTO;
import com.telemetry.vehicle_api.dto.VehicleResponseDTO;
import com.telemetry.vehicle_api.model.Vehicle;
import com.telemetry.vehicle_api.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> register(@Valid @RequestBody VehicleRequestDTO request){
        VehicleResponseDTO response = vehicleService.registerVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{plate}")
    public ResponseEntity<VehicleResponseDTO> getByPlate(@PathVariable String plate){
        return ResponseEntity.ok(vehicleService.getVehiclePlate(plate));
    }
}
