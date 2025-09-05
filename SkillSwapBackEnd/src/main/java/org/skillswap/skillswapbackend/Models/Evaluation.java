package org.skillswap.skillswapbackend.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evaluations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rater_id", nullable = false)
    @JsonIgnoreProperties("evaluations")
    private User rater;

    @ManyToOne
    @JoinColumn(name = "rated_user_id", nullable = false)
    @JsonIgnoreProperties("evaluations")
    private User ratedUser;

    @Column(nullable = false)
    private int rating;

    private String comment;
}
