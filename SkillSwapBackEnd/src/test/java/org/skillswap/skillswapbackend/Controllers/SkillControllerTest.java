package org.skillswap.skillswapbackend.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skillswap.skillswapbackend.Services.SkillService;
import org.skillswap.skillswapbackend.dto.SkillDTO;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SkillControllerTest {

    @InjectMocks
    private SkillController skillController;

    @Mock
    private SkillService skillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserSkills() {
        Long userId = 1L;
        SkillDTO skill1 = new SkillDTO();
        skill1.setId(1L);
        skill1.setName("Java");
        SkillDTO skill2 = new SkillDTO();
        skill2.setId(2L);
        skill2.setName("Spring");
        List<SkillDTO> skills = Arrays.asList(skill1,skill2);

        when(skillService.getUserSkills(userId)).thenReturn(skills);

        ResponseEntity<List<SkillDTO>> response = skillController.getUserSkills(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }
}