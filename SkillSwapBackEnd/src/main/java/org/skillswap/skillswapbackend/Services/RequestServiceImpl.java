
package org.skillswap.skillswapbackend.Services;

import lombok.RequiredArgsConstructor;

import org.skillswap.skillswapbackend.dto.MessageDTO;
import org.skillswap.skillswapbackend.dto.RequestDetailsDTO;
import org.skillswap.skillswapbackend.mappers.RequestMapper;
import org.skillswap.skillswapbackend.models.Request;
import org.skillswap.skillswapbackend.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements org.skillswap.skillswapbackend.services.RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final MessageService messageService;

    @Override
    public RequestDetailsDTO createRequest(Request request) {
        if ("paypal".equals(request.getPaymentMethod())) {
            // Send a message to the receiver to ask for their PayPal address
            messageService.saveMessage(new MessageDTO(null, request.getRequester().getId(), request.getReceiver().getId(), "I would like to pay with PayPal. Please provide your PayPal address.", null));
        }
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDetailsDTO(savedRequest);
    }

    @Override
    public List<RequestDetailsDTO> getRequestsByReceiverId(Long receiverId) {
        return requestRepository.findByReceiverId(receiverId).stream()
                .filter(request -> !request.getRequester().getId().equals(receiverId))
                .map(requestMapper::toDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDetailsDTO getRequestById(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));
        return requestMapper.toDetailsDTO(request);
    }

    @Override
    public RequestDetailsDTO acceptRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("ACCEPTED");
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDetailsDTO(savedRequest);
    }

    @Override
    public RequestDetailsDTO rejectRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("REJECTED");
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDetailsDTO(savedRequest);
    }

    @Override
    public RequestDetailsDTO acceptRequestWithSkillExchange(Long requestId, Long skillId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("ACCEPTED");
        request.setPaymentMethod("SKILL_EXCHANGE");
        // You might want to store the exchanged skill id somewhere
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDetailsDTO(savedRequest);
    }

    @Override
    public RequestDetailsDTO acceptRequestWithPayPal(Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("ACCEPTED");
        request.setPaymentMethod("PAYPAL");
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDetailsDTO(savedRequest);
    }
}

