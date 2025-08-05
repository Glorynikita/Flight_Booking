package com.example.demo1.repository;

import com.example.demo1.model.User_Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User_Profile, Long> , JpaSpecificationExecutor<User_Profile> {
    Optional<User_Profile> findByEmailAndPhone(String email, String phone);
}
