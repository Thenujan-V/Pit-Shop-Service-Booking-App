package com.example.service_mgnt.Repository;

import com.example.service_mgnt.Entity.ServicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<ServicesEntity, Integer> {
}
