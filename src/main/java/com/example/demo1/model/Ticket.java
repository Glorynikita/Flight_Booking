package com.example.demo1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String passangerName;
    private String travelClass;
    private Long seat;
    private String source;
    private String destination;
    private String fare;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User_Profile userProfile;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
