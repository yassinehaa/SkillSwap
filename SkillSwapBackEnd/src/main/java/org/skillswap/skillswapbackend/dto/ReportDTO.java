package org.skillswap.skillswapbackend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDTO {
    private Long id;
    private Long reporterId;
    private Long reportedUserId;
    private String reason;
    private LocalDateTime timestamp;
}
