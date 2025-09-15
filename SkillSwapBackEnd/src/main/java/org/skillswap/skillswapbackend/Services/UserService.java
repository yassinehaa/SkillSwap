package org.skillswap.skillswapbackend.Services;

import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.Models.Skill;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.Repositories.AvailabilityRepository;
import org.skillswap.skillswapbackend.Repositories.EvaluationRepository;
import org.skillswap.skillswapbackend.Repositories.MessageRepository;
import org.skillswap.skillswapbackend.Repositories.ReportRepository;
import org.skillswap.skillswapbackend.Repositories.RequestRepository;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.skillswap.skillswapbackend.dto.SkillDTO;
import org.skillswap.skillswapbackend.dto.UserDTO;
import org.skillswap.skillswapbackend.dto.UserDTO2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
    private MessageRepository messageRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private RequestRepository requestRepository;

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encode password
        
        user.setAdmin(false); // Default to non-Admin
        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setProposedSkills(skillService.getSkillsByUserIdAndType(id, Skill.SkillType.OFFERED));
        userDTO.setSearchedSkills(skillService.getSkillsByUserIdAndType(id, Skill.SkillType.SEARCHED));
        userDTO.setEvaluations(evaluationService.getEvaluationsByRatedUserId(id));
        return userDTO;
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setProposedSkills(skillService.getSkillsByUserIdAndType(user.getId(), Skill.SkillType.OFFERED));
        userDTO.setSearchedSkills(skillService.getSkillsByUserIdAndType(user.getId(), Skill.SkillType.SEARCHED));
        userDTO.setEvaluations(evaluationService.getEvaluationsByRatedUserId(user.getId()));
        return userDTO;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userDTO.getId() != null && !userDTO.getId().equals(id)) {
            throw new RuntimeException("User ID in request body does not match path ID");
        }
        // Update basic user information
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        
        user.setAdmin(userDTO.isAdmin());

        // Update skills
        skillService.deleteSkillsByUserId(id);
        if (userDTO.getProposedSkills() != null && !userDTO.getProposedSkills().isEmpty()) {
            skillService.addSkills(id, userDTO.getProposedSkills());
        }
        if (userDTO.getSearchedSkills() != null && !userDTO.getSearchedSkills().isEmpty()) {
            skillService.addSkills(id, userDTO.getSearchedSkills());
        }

        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public void deleteUser(Long id) {
        evaluationRepository.deleteByRaterIdOrRatedUserId(id);
        reportRepository.deleteByReporterIdOrReportedUserId(id);
        messageRepository.deleteBySenderIdOrReceiverId(id);
        availabilityRepository.deleteByUserId(id);

        List<Skill> userSkills = skillService.getSkillObjectsByUserId(id);
        for (Skill skill : userSkills) {
            requestRepository.deleteBySkillId(skill.getId());
        }
        skillService.deleteSkillsByUserId(id);
        requestRepository.deleteByRequesterIdOrReceiverId(id);

        userRepository.deleteById(id);
    }
    public List<UserDTO2> countSkills(){
     List<Object[]> results = userRepository.countSkills();
     return results.stream().map(result -> new UserDTO2((String) result[0], (Long) result[1])).collect(Collectors.toList());
    }

    

    

    
}

