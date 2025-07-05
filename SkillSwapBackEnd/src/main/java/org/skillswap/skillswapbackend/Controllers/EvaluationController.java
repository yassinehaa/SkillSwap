package org.skillswap.skillswapbackend.Controllers;

import org.skillswap.skillswapbackend.Services.EvaluationService;
import org.skillswap.skillswapbackend.dto.EvaluationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/{raterId}/{ratedUserId}")
    public ResponseEntity<EvaluationDTO> addEvaluation(
            @PathVariable Long raterId,
            @PathVariable Long ratedUserId,
            @RequestBody EvaluationDTO evaluationDTO) {
        return ResponseEntity.ok(evaluationService.addEvaluation(raterId, ratedUserId, evaluationDTO));
    }

    @GetMapping("/average/{userId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long userId) {
        return ResponseEntity.ok(evaluationService.getAverageRating(userId));
    }
}