package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reports WHERE reporter_id = :userId OR reported_user_id = :userId", nativeQuery = true)
    void deleteByReporterIdOrReportedUserId(@Param("userId") Long userId);
}
