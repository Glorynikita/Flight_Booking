package com.example.demo1.model;

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

    @OneToMany(mappedBy = "userProfile",  cascade = CascadeType.ALL)
    private Collection<Ticket> ticket;












//    public Collection<Ticket> getTicket() {
//        return ticket;
//    }
//
//    public void setTicket(Collection<Ticket> ticket) {
//        this.ticket = ticket;
//    }
}
