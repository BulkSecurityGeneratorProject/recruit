package com.recruit.repository;

import com.recruit.domain.Delivery;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Delivery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findOneByUserIDAndPositionID(Long userId,Long posId);
}
