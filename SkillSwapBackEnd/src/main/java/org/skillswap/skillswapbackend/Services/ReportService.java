package org.skillswap.skillswapbackend.Services;

import lombok.RequiredArgsConstructor;
import org.skillswap.skillswapbackend.Models.Report;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.Repositories.ReportRepository;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.skillswap.skillswapbackend.dto.ReportDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportDTO createReport(ReportDTO reportDTO) {
        User reporter = userRepository.findById(reportDTO.getReporterId())
                .orElseThrow(() -> new RuntimeException("Reporter not found"));
        User reportedUser = userRepository.findById(reportDTO.getReportedUserId())
                .orElseThrow(() -> new RuntimeException("Reported user not found"));

        Report report = new Report();
        report.setReporter(reporter);
        report.setReportedUser(reportedUser);
        report.setReason(reportDTO.getReason());
        report.setTimestamp(LocalDateTime.now());

        report = reportRepository.save(report);
        return toDTO(report);
    }

    public List<ReportDTO> getAllReports() {
        return reportRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ReportDTO toDTO(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setReporterId(report.getReporter().getId());
        reportDTO.setReportedUserId(report.getReportedUser().getId());
        reportDTO.setReason(report.getReason());
        reportDTO.setTimestamp(report.getTimestamp());
        return reportDTO;
    }
}
