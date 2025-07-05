package org.skillswap.skillswapbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationDTO {
    private Long id;
    private Long raterId;
    private Long ratedUserId;
    private int rating;
    private String comment;
}