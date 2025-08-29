package org.skillswap.skillswapbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillSearchResultDTO {
    private SkillDTO skill;
    private UserDTO user;
}
