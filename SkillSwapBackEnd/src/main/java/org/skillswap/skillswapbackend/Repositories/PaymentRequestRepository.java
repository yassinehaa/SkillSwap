package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
    List<PaymentRequest> findByRequesterId(Long requesterId);
    List<PaymentRequest> findByProviderId(Long providerId);
}
