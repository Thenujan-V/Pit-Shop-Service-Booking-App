package com.example.service_mgnt.Repository;

import com.example.service_mgnt.Entity.ServicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<ServicesEntity, Integer> {
    boolean existsByServiceName(String serviceName);
    @Query("SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END FROM ServicesEntity s WHERE s.serviceId = :serviceId AND s.isAvailable = :isAvailable")
    boolean existsByIdAndIsAvailable(int serviceId, boolean isAvailable);
}
