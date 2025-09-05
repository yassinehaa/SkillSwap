
package org.skillswap.skillswapbackend.mappers;

import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.dto.RequestDTO;
import org.skillswap.skillswapbackend.dto.RequestDetailsDTO;
import org.skillswap.skillswapbackend.models.Request;
import org.skillswap.skillswapbackend.Repositories.SkillRepository;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;

    public RequestMapper(UserRepository userRepository, SkillRepository skillRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.modelMapper = modelMapper;
    }

    public Request toEntity(RequestDTO requestDTO) {
        Request request = new Request();
        request.setRequester(userRepository.findById(requestDTO.getRequesterId()).orElseThrow(() -> new RuntimeException("Requester not found")));
        request.setReceiver(userRepository.findById(requestDTO.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found")));
        request.setSkill(skillRepository.findById(requestDTO.getSkillId()).orElseThrow(() -> new RuntimeException("Skill not found")));
        request.setStatus(requestDTO.getStatus());
        request.setPaymentMethod(requestDTO.getPaymentMethod());
        return request;
    }

    public RequestDetailsDTO toDetailsDTO(Request request) {
        return modelMapper.map(request, RequestDetailsDTO.class);
    }
}
