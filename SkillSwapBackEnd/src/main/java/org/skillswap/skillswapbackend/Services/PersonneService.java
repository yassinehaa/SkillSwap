package org.skillswap.skillswapbackend.Services;

import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.Models.*;

import org.skillswap.skillswapbackend.Repositories.EvaluationRepository;

import org.skillswap.skillswapbackend.Repositories.PersonneRepository;
import org.skillswap.skillswapbackend.Repositories.ReportRepository;
import org.skillswap.skillswapbackend.Repositories.RequestRepository;
import org.skillswap.skillswapbackend.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonneService {
    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SkillService skillService;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ReportRepository reportRepository;

    

    

    @Autowired
    private RequestRepository requestRepository;

    public UserDTO createUser(UserDTO userDTO) {
        if (personneRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (userDTO.getRole() == null) {
            userDTO.setRole(Role.USER);
        }

        Personne user;
        if (userDTO.getRole() == Role.ADMIN) {
            user = modelMapper.map(userDTO, Admin.class);
        } else {
            user = modelMapper.map(userDTO, User.class);
        }

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user = personneRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }


    public UserDTO getUserById(Long id) {
        Personne user = personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setProposedSkills(skillService.getSkillsByUserIdAndType(id, Skill.SkillType.OFFERED));
        userDTO.setSearchedSkills(skillService.getSkillsByUserIdAndType(id, Skill.SkillType.SEARCHED));
        userDTO.setEvaluations(evaluationService.getEvaluationsByRatedUserId(id));
        return userDTO;
    }

    public UserDTO getUserByEmail(String email) {
        Personne user = personneRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setProposedSkills(skillService.getSkillsByUserIdAndType(user.getId(), Skill.SkillType.OFFERED));
        userDTO.setSearchedSkills(skillService.getSkillsByUserIdAndType(user.getId(), Skill.SkillType.SEARCHED));
        userDTO.setEvaluations(evaluationService.getEvaluationsByRatedUserId(user.getId()));
        return userDTO;
    }

    public List<UserDTO> getAllUsers() {
        return personneRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Personne user = personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userDTO.getId() != null && !userDTO.getId().equals(id)) {
            throw new RuntimeException("User ID in request body does not match path ID");
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        

        skillService.deleteSkillsByUserId(id);
        if (userDTO.getProposedSkills() != null && !userDTO.getProposedSkills().isEmpty()) {
            skillService.addSkills(id, userDTO.getProposedSkills());
        }
        if (userDTO.getSearchedSkills() != null && !userDTO.getSearchedSkills().isEmpty()) {
            skillService.addSkills(id, userDTO.getSearchedSkills());
        }

        user = personneRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public void deleteUser(Long id) {
        evaluationRepository.deleteByRaterIdOrRatedUserId(id);
        reportRepository.deleteByReporterIdOrReportedUserId(id);
        

        List<Skill> userSkills = skillService.getSkillObjectsByUserId(id);
        for (Skill skill : userSkills) {
            requestRepository.deleteBySkillId(skill.getId());
        }
        skillService.deleteSkillsByUserId(id);
        requestRepository.deleteByRequesterIdOrReceiverId(id);

        personneRepository.deleteById(id);
    }
}


