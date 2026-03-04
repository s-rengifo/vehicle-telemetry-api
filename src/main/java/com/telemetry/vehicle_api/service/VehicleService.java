package com.telemetry.vehicle_api.service;

import com.telemetry.vehicle_api.dto.VehicleRequestDTO;
import com.telemetry.vehicle_api.dto.VehicleResponseDTO;
import com.telemetry.vehicle_api.model.Vehicle;
import com.telemetry.vehicle_api.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleResponseDTO registerVehicle(VehicleRequestDTO request){
        if (vehicleRepository.existsByPlate(request.getPlate())){
            throw new RuntimeException("Vehicle with the plate " + request.getPlate() + " already exists");
        }

        Vehicle vehicle = Vehicle.builder()
                .plate(request.getPlate())
                .ownerName(request.getOwnerName())
                .vehicleType(request.getVehicleType())
                .build();

        Vehicle saved = vehicleRepository.save(vehicle);

        return VehicleResponseDTO.builder()
                .id(saved.getId())
                .plate(saved.getPlate())
                .ownerName(saved.getOwnerName())
                .vehicleType(saved.getVehicleType())
                .registeredAt(saved.getRegisteredAt())
                .build();
    }

    public VehicleResponseDTO getVehiclePlate(String plate){
        Vehicle vehicle = vehicleRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + plate));

        return VehicleResponseDTO.builder()
                .id(vehicle.getId())
                .plate(vehicle.getPlate())
                .ownerName(vehicle.getOwnerName())
                .vehicleType(vehicle.getVehicleType())
                .registeredAt(vehicle.getRegisteredAt())
                .build();
    }
}
