package com.skrzypczyk.meetings.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reset_password")
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    private String token;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime tokenExpiryDate;
}
