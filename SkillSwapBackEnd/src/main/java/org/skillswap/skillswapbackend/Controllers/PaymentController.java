package org.skillswap.skillswapbackend.Controllers;

import org.skillswap.skillswapbackend.Services.PaymentService;
import org.skillswap.skillswapbackend.dto.PaymentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{requesterId}/{providerId}/{skillId}")
    public ResponseEntity<PaymentRequestDTO> requestService(
            @PathVariable Long requesterId,
            @PathVariable Long providerId,
            @PathVariable Long skillId) {
        return ResponseEntity.ok(paymentService.requestService(requesterId, providerId, skillId));
    }
}