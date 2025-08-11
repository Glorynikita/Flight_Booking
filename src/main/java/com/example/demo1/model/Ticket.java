package com.example.demo1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserProfile userProfile;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
