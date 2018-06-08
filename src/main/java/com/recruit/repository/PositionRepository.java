package com.recruit.repository;

import com.recruit.domain.Position;
import com.recruit.domain.enumeration.PositionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Position entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Page<Position> findAllByCompany_UserId(Long userId, Pageable pageable);

    List<Position> findAllByCompany_UserId(Long userId);
    List<Position> findAllByCompanyId(Long id);

    Page<Position> findAllByType(PositionType type, Pageable pageable);

    void deleteByCompany_UserId(Long userId);

    void deleteByCompanyId(Long id);
}
