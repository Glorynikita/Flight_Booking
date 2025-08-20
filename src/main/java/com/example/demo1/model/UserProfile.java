package com.example.demo1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldNameConstants
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "phone"})
})
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "passanger_name", nullable = false)
    private String name;
    private String gender;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;
    private String password;
    private String role;

    @OneToMany(mappedBy = "userProfile")
    @JsonIgnore
    private Collection<Ticket> ticket;





}
