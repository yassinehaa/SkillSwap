package org.skillswap.skillswapbackend.Controllers;

import org.skillswap.skillswapbackend.Services.SkillService;
import org.skillswap.skillswapbackend.dto.SkillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skillswap.skillswapbackend.dto.SkillSearchResultDTO;

import java.util.List;

@RestController
@RequestMapping("/api/skills")

public class SkillController {
    @Autowired
    private SkillService skillService;

    

    @GetMapping("/match/{userId}")
    public ResponseEntity<List<SkillDTO>> getMatchingSkills(@PathVariable Long userId) {
        return ResponseEntity.ok(skillService.getMatchingSkills(userId));
    }
        @GetMapping("/search")
    public ResponseEntity<List<SkillSearchResultDTO>> getByName(@RequestParam String name)    {
        return ResponseEntity.ok(skillService.getSkillsByName(name));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SkillDTO>> getUserSkills(@PathVariable Long userId) {
        return ResponseEntity.ok(skillService.getUserSkills(userId));
    }

    @PutMapping("/approve/{skillId}")
    public ResponseEntity<SkillDTO> approveSkill(@PathVariable Long skillId) {
        return ResponseEntity.ok(skillService.approveSkill(skillId));
    }

    @PutMapping("/reject/{skillId}")
    public ResponseEntity<SkillDTO> rejectSkill(@PathVariable Long skillId) {
        return ResponseEntity.ok(skillService.rejectSkill(skillId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<SkillDTO>> getPendingSkills() {
        return ResponseEntity.ok(skillService.getPendingSkills());
    }

}