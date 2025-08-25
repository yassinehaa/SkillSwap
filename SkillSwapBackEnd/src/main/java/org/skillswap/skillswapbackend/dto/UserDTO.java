package org.skillswap.skillswapbackend.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import org.skillswap.skillswapbackend.dto.SkillDTO;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isPremium;
    private List<SkillDTO> proposedSkills;
    private List<SkillDTO> searchedSkills;
}
