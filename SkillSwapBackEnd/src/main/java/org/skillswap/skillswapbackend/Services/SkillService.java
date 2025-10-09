package org.skillswap.skillswapbackend.Services;

import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.Models.Request;
import org.skillswap.skillswapbackend.Models.Skill;
import org.skillswap.skillswapbackend.Models.Personne;
import org.skillswap.skillswapbackend.Repositories.PersonneRepository;
import org.skillswap.skillswapbackend.Repositories.SkillRepository;
import org.skillswap.skillswapbackend.Repositories.RequestRepository;
import org.skillswap.skillswapbackend.dto.SkillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.skillswap.skillswapbackend.dto.SkillSearchResultDTO;
import org.skillswap.skillswapbackend.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private PersonneRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    public void deleteSkillsByUserId(Long userId) {
        List<Skill> skills = skillRepository.findByUserId(userId);
        for (Skill skill : skills) {
            List<Request> requests = requestRepository.findBySkillId(skill.getId());
            requestRepository.deleteBySkillId(skill.getId());
        }
        skillRepository.deleteByUserId(userId);
    }

    public List<SkillDTO> getSkillsByUserIdAndType(Long userId, Skill.SkillType type) {
        return skillRepository.findByUserIdAndType(userId, type)
                .stream()
                .map(skill -> modelMapper.map(skill, SkillDTO.class))
                .toList();
    }

//    public List<SkillDTO> getMatchingSkills(Long userId) {
//        List<Skill> searchedSkills = skillRepository.findByUserId(userId)
//                .stream()
//                .filter(skill -> skill.getType() == Skill.SkillType.SEARCHED)
//                .toList();
//
//        return skillRepository.findByType(Skill.SkillType.OFFERED)
//                .stream()
//                .filter(offeredSkill -> searchedSkills.stream()
//                        .anyMatch(searchedSkill -> searchedSkill.getName().equals(offeredSkill.getName())))
//                .map(skill -> modelMapper.map(skill, SkillDTO.class))
//                .toList();
//    }

    public List<SkillDTO> addSkills(Long userId, List<SkillDTO> skillDTOs) {
        Personne user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Skill> skills = skillDTOs.stream()
                .map(skillDTO -> {
                    Skill skill = modelMapper.map(skillDTO, Skill.class);
                    skill.setDescription(skillDTO.getDescription());
                    skill.setLevel(skillDTO.getLevel());
                    skill.setCategory(skillDTO.getCategory());
                    skill.setStatus(Skill.SkillStatus.PENDING);
                    skill.setUser(user);
                    return skill;
                })
                .collect(Collectors.toList());
        skills = skillRepository.saveAll(skills);
        return skills.stream()
                .map(skill -> modelMapper.map(skill, SkillDTO.class))
                .toList();
    }
    public List<SkillSearchResultDTO> getSkillsByName(String name) {
        return skillRepository.findByNameAndType(name, Skill.SkillType.OFFERED)
                .stream()
                .map(skill -> {
                    SkillDTO skillDTO = modelMapper.map(skill, SkillDTO.class);
                    UserDTO userDTO = modelMapper.map(skill.getUser(), UserDTO.class);
                    return new SkillSearchResultDTO(skillDTO, userDTO);
                })
                .toList();
    }

    public List<SkillDTO> getUserSkills(Long userId) {
        return skillRepository.findByUserId(userId)
                .stream()
                .map(skill -> modelMapper.map(skill, SkillDTO.class))
                .collect(Collectors.toList());
    }

    public List<Skill> getSkillObjectsByUserId(Long userId) {
        return skillRepository.findByUserId(userId);
    }

    public SkillDTO approveSkill(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        skill.setStatus(Skill.SkillStatus.APPROVED);
        return modelMapper.map(skillRepository.save(skill), SkillDTO.class);
    }

    public SkillDTO rejectSkill(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        skill.setStatus(Skill.SkillStatus.REJECTED);
        return modelMapper.map(skillRepository.save(skill), SkillDTO.class);
    }

    public List<SkillDTO> getPendingSkills() {
        return skillRepository.findByStatus(Skill.SkillStatus.PENDING)
                .stream()
                .map(skill -> modelMapper.map(skill, SkillDTO.class))
                .toList();
    }
}