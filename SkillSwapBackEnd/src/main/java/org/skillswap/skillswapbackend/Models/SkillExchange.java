package org.skillswap.skillswapbackend.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skill_exchanges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne
    @JoinColumn(name = "exchange_skill_id")
    private Skill exchangeSkill;
}
