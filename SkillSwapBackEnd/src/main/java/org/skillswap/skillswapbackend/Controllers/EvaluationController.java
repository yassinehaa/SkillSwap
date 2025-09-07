package org.skillswap.skillswapbackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.skillswap.skillswapbackend.Services.EvaluationService;
import org.skillswap.skillswapbackend.dto.EvaluationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
public class EvaluationController {
    private final EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<EvaluationDTO> createEvaluation(@RequestBody EvaluationDTO evaluationDTO) {
        return ResponseEntity.ok(evaluationService.createEvaluation(evaluationDTO));
    }
}
