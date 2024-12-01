package com.example.auth.Repository;

import com.example.auth.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    UserEntity findByUserName(String userName);
    UserEntity findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

    Optional<UserEntity> findByVerificationCode(String confirmationToken);
}
