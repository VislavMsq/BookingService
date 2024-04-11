package com.project_service.repository;

import com.project_service.entity.BoardDetail;
import com.project_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BoardDetailRepository extends JpaRepository<BoardDetail, UUID> {

    @Query("select bd from BoardDetail bd where bd.owner = :owner and bd.date >= :startDate and bd.date <= :endDate")
    List<BoardDetail> findAllByOwnerInDateRange(@Param("owner") User owner,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}
