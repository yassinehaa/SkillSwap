
package org.skillswap.skillswapbackend.controller;

import lombok.RequiredArgsConstructor;
import org.skillswap.skillswapbackend.dto.RequestDTO;
import org.skillswap.skillswapbackend.dto.RequestDetailsDTO;
import org.skillswap.skillswapbackend.mappers.RequestMapper;
import org.skillswap.skillswapbackend.models.Request;
import org.skillswap.skillswapbackend.services.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PostMapping
    public ResponseEntity<RequestDetailsDTO> createRequest(@RequestBody RequestDTO requestDTO) {
        Request request = requestMapper.toEntity(requestDTO);
        return ResponseEntity.ok(requestService.createRequest(request));
    }

    @GetMapping("/received/{receiverId}")
    public ResponseEntity<List<RequestDetailsDTO>> getRequestsByReceiverId(@PathVariable Long receiverId) {
        return ResponseEntity.ok(requestService.getRequestsByReceiverId(receiverId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDetailsDTO> getRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.getRequestById(id));
    }

    @PutMapping("/{requestId}/accept")
    public ResponseEntity<RequestDetailsDTO> acceptRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.acceptRequest(requestId));
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<RequestDetailsDTO> rejectRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.rejectRequest(requestId));
    }

    @PutMapping("/{requestId}/accept-with-exchange")
    public ResponseEntity<RequestDetailsDTO> acceptRequestWithSkillExchange(@PathVariable Long requestId, @RequestBody Map<String, Long> payload) {
        Long skillId = payload.get("skillId");
        return ResponseEntity.ok(requestService.acceptRequestWithSkillExchange(requestId, skillId));
    }

    @PutMapping("/{requestId}/accept-with-paypal")
    public ResponseEntity<RequestDetailsDTO> acceptRequestWithPayPal(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.acceptRequestWithPayPal(requestId));
    }
}
