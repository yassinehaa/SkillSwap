package org.skillswap.skillswapbackend.Services;

import org.skillswap.skillswapbackend.Models.Personne;
import lombok.RequiredArgsConstructor;
import org.skillswap.skillswapbackend.Models.Evaluation;
import org.skillswap.skillswapbackend.Repositories.EvaluationRepository;
import org.skillswap.skillswapbackend.Repositories.PersonneRepository;
import org.skillswap.skillswapbackend.dto.EvaluationDTO;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final PersonneRepository userRepository;
    private final ModelMapper modelMapper;

    public EvaluationDTO createEvaluation(EvaluationDTO evaluationDTO) {
        Personne rater = userRepository.findById(evaluationDTO.getRaterId())
                .orElseThrow(() -> new RuntimeException("Rater not found"));
        Personne ratedUser = userRepository.findById(evaluationDTO.getRatedUserId())
                .orElseThrow(() -> new RuntimeException("Rated user not found"));

        Evaluation evaluation = new Evaluation();
        evaluation.setRater(rater);
        evaluation.setRatedUser(ratedUser);
        evaluation.setRating(evaluationDTO.getRating());
        evaluation.setComment(evaluationDTO.getComment());
        evaluation.setTimestamp(LocalDateTime.now());

        evaluation = evaluationRepository.save(evaluation);
        return modelMapper.map(evaluation, EvaluationDTO.class);
    }

    public List<EvaluationDTO> getEvaluationsByRatedUserId(Long ratedUserId) {
        return evaluationRepository.findByRatedUserId(ratedUserId).stream()
                .map(evaluation -> modelMapper.map(evaluation, EvaluationDTO.class))
                .collect(Collectors.toList());
    }
    public Long countEvaluationByRatedUserId(Long ratedUserId){
        return evaluationRepository.countEvaluationByRatedUserId(ratedUserId);
    }

}