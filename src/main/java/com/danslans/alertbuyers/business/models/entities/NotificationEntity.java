package com.danslans.alertbuyers.business.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEntity {
    private String email;
    private String message;
    @Column(nullable = false)
    private LocalDateTime date;
    private boolean status;
    private String idEmail;
    private String location;
    private String weatherForecast;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
