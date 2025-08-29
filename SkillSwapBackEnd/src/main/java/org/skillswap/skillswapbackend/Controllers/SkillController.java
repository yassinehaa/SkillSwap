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

    @PostMapping("/{userId}")
    public ResponseEntity<SkillDTO> addSkill(@PathVariable Long userId, @RequestBody SkillDTO skillDTO) {
        return ResponseEntity.ok(skillService.addSkill(userId, skillDTO));
    }

    @GetMapping("/match/{userId}")
    public ResponseEntity<List<SkillDTO>> getMatchingSkills(@PathVariable Long userId) {
        return ResponseEntity.ok(skillService.getMatchingSkills(userId));
    }
        @GetMapping("/search")
    public ResponseEntity<List<SkillSearchResultDTO>> getByName(@RequestParam String name)    {
        return ResponseEntity.ok(skillService.getSkillsByName(name));
    }

}