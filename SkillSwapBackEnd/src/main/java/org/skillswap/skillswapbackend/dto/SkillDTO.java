package org.skillswap.skillswapbackend.dto;

import lombok.Getter;
import lombok.Setter;
import org.skillswap.skillswapbackend.Models.Skill;

@Getter
@Setter
public class SkillDTO {
    private Long id;
    private String name;
    private Skill.SkillType type;
    private Long userId;
}