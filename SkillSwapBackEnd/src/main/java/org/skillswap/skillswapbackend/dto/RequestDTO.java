
package org.skillswap.skillswapbackend.dto;

import lombok.Data;

@Data
public class RequestDTO {
    private Long requesterId;
    private Long receiverId;
    private Long skillId;
    private String status;
    private String paymentMethod;
}
