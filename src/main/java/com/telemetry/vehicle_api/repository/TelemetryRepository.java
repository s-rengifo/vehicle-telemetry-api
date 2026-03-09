package com.telemetry.vehicle_api.repository;

import com.telemetry.vehicle_api.model.TelemetryEvent;
import com.telemetry.vehicle_api.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelemetryRepository extends JpaRepository<TelemetryEvent, Long> {
    List<TelemetryEvent> findByVehicleOrderByRecordedAtDesc(Vehicle vehicle);
}
