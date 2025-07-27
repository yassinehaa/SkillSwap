package org.skillswap.skillswapbackend.Services;

import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.Models.Evaluation;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.Repositories.EvaluationRepository;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.skillswap.skillswapbackend.dto.EvaluationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EvaluationDTO addEvaluation(Long raterId, Long ratedUserId, EvaluationDTO evaluationDTO) {
        if (evaluationDTO.getRating() < 1 || evaluationDTO.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        User rater = userRepository.findById(raterId)
                .orElseThrow(() -> new RuntimeException("Rater not found"));
        User ratedUser = userRepository.findById(ratedUserId)
                .orElseThrow(() -> new RuntimeException("Rated user not found"));
        Evaluation evaluation = modelMapper.map(evaluationDTO, Evaluation.class);
        evaluation.setRater(rater);
        evaluation.setRatedUser(ratedUser);
        evaluation = evaluationRepository.save(evaluation);
        return modelMapper.map(evaluation, EvaluationDTO.class);
    }

    public Double getAverageRating(Long userId) {
        List<Evaluation> evaluations = evaluationRepository.findByRatedUserId(userId);
        return evaluations.isEmpty() ? 0.0 : evaluations.stream()
                .mapToDouble(Evaluation::getRating)
                .average()
                .orElse(0.0);
    }
}