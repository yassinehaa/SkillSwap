package org.skillswap.skillswapbackend.Services;

import lombok.RequiredArgsConstructor;
import org.skillswap.skillswapbackend.Models.Report;
import org.skillswap.skillswapbackend.Models.Personne;
import org.skillswap.skillswapbackend.Repositories.PersonneRepository;
import org.skillswap.skillswapbackend.Repositories.ReportRepository;
import org.skillswap.skillswapbackend.dto.ReportDTO;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final PersonneRepository userRepository;
    private final ModelMapper modelMapper;

    public ReportDTO createReport(ReportDTO reportDTO) {
        Personne reporter = userRepository.findById(reportDTO.getReporterId())
                .orElseThrow(() -> new RuntimeException("Reporter not found"));
        Personne reportedUser = userRepository.findById(reportDTO.getReportedUserId())
                .orElseThrow(() -> new RuntimeException("Reported user not found"));

        Report report = new Report();
        report.setReporter(reporter);
        report.setReportedUser(reportedUser);
        report.setReason(reportDTO.getReason());
        report.setTimestamp(LocalDateTime.now());

        report = reportRepository.save(report);
        return modelMapper.map(report, ReportDTO.class);
    }

    public List<ReportDTO> getAllReports() {
        return reportRepository.findAll().stream()
                .map(report -> modelMapper.map(report, ReportDTO.class))
                .collect(Collectors.toList());
    }

    
}
