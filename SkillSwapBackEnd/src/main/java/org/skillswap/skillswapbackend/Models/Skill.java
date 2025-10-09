package org.skillswap.skillswapbackend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String level;
    private String category;

    @Enumerated(EnumType.STRING)
    private SkillStatus status;

    @Enumerated(EnumType.STRING)
    private SkillType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Personne user;

    public enum SkillType {
        OFFERED,
        SEARCHED
    }

    public enum SkillStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}
