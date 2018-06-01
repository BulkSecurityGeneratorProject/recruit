package com.recruit.repository;

import com.recruit.domain.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Delivery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findOneByUserIDAndPositionID(Long userId,Long posId);

    Page<Delivery> findAllByPositionIDIn(List<Long> positionID, Pageable pageable);
    Page<Delivery> findAllByUserID(Long userId, Pageable pageable);
}
