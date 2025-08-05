package com.example.demo1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "phone"})
})
public class User_Profile {
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
}
