package org.skillswap.skillswapbackend.Services;

import lombok.RequiredArgsConstructor;
import org.skillswap.skillswapbackend.Models.Evaluation;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.Repositories.EvaluationRepository;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.skillswap.skillswapbackend.dto.EvaluationDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final UserRepository userRepository;

    public EvaluationDTO createEvaluation(EvaluationDTO evaluationDTO) {
        User rater = userRepository.findById(evaluationDTO.getRaterId())
                .orElseThrow(() -> new RuntimeException("Rater not found"));
        User ratedUser = userRepository.findById(evaluationDTO.getRatedUserId())
                .orElseThrow(() -> new RuntimeException("Rated user not found"));

        Evaluation evaluation = new Evaluation();
        evaluation.setRater(rater);
        evaluation.setRatedUser(ratedUser);
        evaluation.setRating(evaluationDTO.getRating());
        evaluation.setComment(evaluationDTO.getComment());
        evaluation.setTimestamp(LocalDateTime.now());

        evaluation = evaluationRepository.save(evaluation);
        return toDTO(evaluation);
    }

    public List<EvaluationDTO> getEvaluationsByRatedUserId(Long ratedUserId) {
        return evaluationRepository.findByRatedUserId(ratedUserId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private EvaluationDTO toDTO(Evaluation evaluation) {
        EvaluationDTO evaluationDTO = new EvaluationDTO();
        evaluationDTO.setId(evaluation.getId());
        evaluationDTO.setRaterId(evaluation.getRater().getId());
        evaluationDTO.setRaterFirstName(evaluation.getRater().getFirstName());
        evaluationDTO.setRaterLastName(evaluation.getRater().getLastName());
        evaluationDTO.setRatedUserId(evaluation.getRatedUser().getId());
        evaluationDTO.setRating(evaluation.getRating());
        evaluationDTO.setComment(evaluation.getComment());
        evaluationDTO.setTimestamp(evaluation.getTimestamp());
        return evaluationDTO;
    }
}
