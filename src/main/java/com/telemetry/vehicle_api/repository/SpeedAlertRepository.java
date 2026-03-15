package com.telemetry.vehicle_api.repository;

import com.telemetry.vehicle_api.model.SpeedAlert;
import com.telemetry.vehicle_api.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpeedAlertRepository extends JpaRepository<SpeedAlert, Long> {
    List<SpeedAlert> findByVehicleOrderByRecordedAtDesc(Vehicle vehicle);
}
