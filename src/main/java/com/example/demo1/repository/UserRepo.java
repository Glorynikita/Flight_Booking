package com.example.demo1.repository;

import com.example.demo1.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserProfile, Long> , JpaSpecificationExecutor<UserProfile> {


    // to validate duplicate entries for user
    Optional<UserProfile> findByEmailAndPhone(String email, String phone);

    // to validate user login
    Optional<UserProfile> findByEmailAndPassword(String email, String password);

    //to get user details by giving ticket id
    @Query("SELECT t.userProfile FROM Ticket t WHERE t.id = :ticketId")
    Optional<UserProfile> findUserProfileByTicketId(Long ticketId);




}
