package org.skillswap.skillswapbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    
    private boolean isAdmin;
    private List<SkillDTO> proposedSkills;
    private List<SkillDTO> searchedSkills;
    private List<EvaluationDTO> evaluations;
}
