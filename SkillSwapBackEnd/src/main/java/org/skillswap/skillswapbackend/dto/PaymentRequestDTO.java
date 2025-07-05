package org.skillswap.skillswapbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {
    private Long id;
    private Long requesterId;
    private Long providerId;
    private Long skillId;
    private String status;
}