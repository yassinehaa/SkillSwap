package org.skillswap.skillswapbackend.Controllers;

import org.skillswap.skillswapbackend.Services.PayPalService;
import org.skillswap.skillswapbackend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PayPalService payPalService;

    @Autowired
    private UserService userService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> payload) {
        double amount = Double.parseDouble(payload.get("amount").toString());
        String orderId = payPalService.createOrder(amount);
        return ResponseEntity.ok(orderId);
    }

    @PostMapping("/capture-order")
    public ResponseEntity<Void> captureOrder(@RequestBody Map<String, Object> payload, Authentication authentication) {
        String orderId = payload.get("orderId").toString();
        payPalService.captureOrder(orderId);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        userService.updateUserPremiumStatus(username, true);

        return ResponseEntity.ok().build();
    }
}
