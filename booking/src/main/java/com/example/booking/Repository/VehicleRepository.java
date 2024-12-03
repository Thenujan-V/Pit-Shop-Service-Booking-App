package com.example.booking.Repository;

import com.example.booking.Entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {
    @Query("SELECT v FROM VehicleEntity v WHERE v.userId = :userId")
    List<VehicleEntity> findVehicleDetails(@Param("userId") Integer userId);

//    @Query("SELECT v FROM VehicleEntity v WHERE v.vehicleId = :vehicleNo")
//    Optional<VehicleEntity> findDetails(@Param("vehicleNo") String vehicleNo);
}
