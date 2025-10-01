package org.skillswap.skillswapbackend.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Personne requester;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Personne receiver;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private String status;
    private String paymentMethod;
}
