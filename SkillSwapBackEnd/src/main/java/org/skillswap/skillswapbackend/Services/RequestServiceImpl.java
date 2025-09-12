
package org.skillswap.skillswapbackend.Services;

import lombok.RequiredArgsConstructor;

import org.skillswap.skillswapbackend.dto.MessageDTO;
import org.skillswap.skillswapbackend.dto.RequestDetailsDTO;
import org.skillswap.skillswapbackend.mappers.RequestMapper;
import org.skillswap.skillswapbackend.Models.Request;
import org.skillswap.skillswapbackend.Repositories.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final MessageService messageService;

    
    public RequestDetailsDTO createRequest(Request request) {
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDetailsDTO(savedRequest);
    }

    
    public List<RequestDetailsDTO> getRequestsByReceiverId(Long receiverId) {
        return requestRepository.findByReceiverId(receiverId).stream()
                .filter(request -> !request.getRequester().getId().equals(receiverId))
                .map(requestMapper::toDetailsDTO)
                .collect(Collectors.toList());
    }

    
    public RequestDetailsDTO getRequestById(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));
        return requestMapper.toDetailsDTO(request);
    }

    
    public RequestDetailsDTO acceptRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("ACCEPTED");
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDetailsDTO(savedRequest);
    }

    
    public RequestDetailsDTO rejectRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("REJECTED");
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDetailsDTO(savedRequest);
    }

    
    public RequestDetailsDTO acceptRequestWithSkillExchange(Long requestId, Long skillId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("ACCEPTED");
        request.setPaymentMethod("SKILL_EXCHANGE");
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDetailsDTO(savedRequest);
    }

    
}

