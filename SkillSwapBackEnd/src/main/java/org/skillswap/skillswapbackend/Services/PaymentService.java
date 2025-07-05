package org.skillswap.skillswapbackend.Services;

import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.Models.PaymentRequest;
import org.skillswap.skillswapbackend.Models.Skill;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.Repositories.PaymentRequestRepository;
import org.skillswap.skillswapbackend.Repositories.SkillRepository;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.skillswap.skillswapbackend.dto.PaymentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PaymentRequestDTO requestService(Long requesterId, Long providerId, Long skillId) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("Requester not found"));
        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        if (!skill.getType().equals(Skill.SkillType.OFFERED) || !skill.getUser().getId().equals(providerId)) {
            throw new RuntimeException("Invalid skill or provider");
        }
        PaymentRequest request = new PaymentRequest();
        request.setRequester(requester);
        request.setProvider(provider);
        request.setSkill(skill);
        request.setStatus("PENDING");
        request = paymentRequestRepository.save(request);
        return modelMapper.map(request, PaymentRequestDTO.class);
    }
}