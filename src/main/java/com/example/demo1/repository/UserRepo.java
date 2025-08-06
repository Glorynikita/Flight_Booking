package com.example.demo1.repository;

import com.example.demo1.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserProfile, Long> , JpaSpecificationExecutor<UserProfile> {
    // to validate duplicate entries for user
    Optional<UserProfile> findByEmailAndPhone(String email, String phone);
}
