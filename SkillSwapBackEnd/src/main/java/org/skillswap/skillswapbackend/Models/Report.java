package org.skillswap.skillswapbackend.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private Personne reporter;

    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    private Personne reportedUser;

    private String reason;

    private LocalDateTime timestamp;
}
