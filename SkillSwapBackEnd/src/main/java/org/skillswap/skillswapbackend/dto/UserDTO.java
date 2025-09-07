package org.skillswap.skillswapbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isPremium;
    private boolean isAdmin;
    private List<SkillDTO> proposedSkills;
    private List<SkillDTO> searchedSkills;
}
