package com.example.authGateWay.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer user_id;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    private String role;
    @Column(name = "phone_no", nullable = false)
    private String phone_no;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "verification_code", nullable = true)
    private String verificationCode;
    @Column(name = "enabled", nullable = true, columnDefinition = "TINYINT DEFAULT 0")
    private boolean enabled;
    @Column(name = "status", nullable = false)
    private boolean status = true;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date created_at;
    @Column(name = "updated_at", nullable = true)
    private Date updated_at;

}
