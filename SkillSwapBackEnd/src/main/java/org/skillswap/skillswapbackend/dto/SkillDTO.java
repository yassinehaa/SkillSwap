package org.skillswap.skillswapbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.skillswap.skillswapbackend.Models.Skill;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {
    private Long id;
    private String name;
    private Skill.SkillType type;
    private Long userId;
}