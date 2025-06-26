package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
}
