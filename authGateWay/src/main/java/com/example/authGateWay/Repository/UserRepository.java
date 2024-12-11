package com.example.authGateWay.Repository;

import com.example.authGateWay.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUserEmail(String userEmail);

    UserEntity findByUserName(String username);
}
