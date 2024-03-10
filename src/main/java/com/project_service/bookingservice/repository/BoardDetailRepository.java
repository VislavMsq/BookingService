package com.project_service.bookingservice.repository;

import com.project_service.bookingservice.entity.BoardDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BoardDetailRepository extends JpaRepository<BoardDetail, UUID> {

    @Query("select bd from BoardDetail bd where bd.owner.id = :id and bd.date >= :startDate and bd.date <= :endDate")
    List<BoardDetail> findAllByOwnerInDateRange(@Param("id") UUID id, @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

    @Query("select bd from BoardDetail bd where bd.owner.id = :ownerId and bd.apartment.id = :apartmentId " +
            "and bd.date >= :startDate and bd.date <= :endDate")
    List<BoardDetail> findAllByApartmentInDateRange(@Param("ownerId") UUID ownerId,
                                                    @Param("apartmentId") String apartmentId,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);
}
