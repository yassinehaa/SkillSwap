package org.skillswap.skillswapbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
