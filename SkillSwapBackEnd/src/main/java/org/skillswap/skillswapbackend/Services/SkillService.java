package org.skillswap.skillswapbackend.Services;

import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.Models.Skill;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.Repositories.SkillRepository;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.skillswap.skillswapbackend.dto.SkillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SkillDTO addSkill(Long userId, SkillDTO skillDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Skill skill = modelMapper.map(skillDTO, Skill.class);
        skill.setUser(user);
        skill = skillRepository.save(skill);
        return modelMapper.map(skill, SkillDTO.class);
    }

    public List<SkillDTO> getMatchingSkills(Long userId) {
        List<Skill> searchedSkills = skillRepository.findByUserId(userId)
                .stream()
                .filter(skill -> skill.getType() == Skill.SkillType.SEARCHED)
                .toList();

        return skillRepository.findByType(Skill.SkillType.OFFERED)
                .stream()
                .filter(offeredSkill -> searchedSkills.stream()
                        .anyMatch(searchedSkill -> searchedSkill.getName().equals(offeredSkill.getName())))
                .map(skill -> modelMapper.map(skill, SkillDTO.class))
                .toList();
    }
}