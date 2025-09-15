package org.skillswap.skillswapbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private Long id;
    private Long reporterId;
    private Long reportedUserId;
    private String reason;
    private LocalDateTime timestamp;
}
