package org.skillswap.skillswapbackend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EvaluationDTO {
    private Long id;
    private Long raterId;
    private String raterFirstName;
    private String raterLastName;
    private Long ratedUserId;
    private int rating;
    private String comment;
    private LocalDateTime timestamp;
}
