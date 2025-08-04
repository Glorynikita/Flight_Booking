package com.example.demo1.repository;

import com.example.demo1.model.User_Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User_Profile, Long> , JpaSpecificationExecutor<User_Profile> {
}
