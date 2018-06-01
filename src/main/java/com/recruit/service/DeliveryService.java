package com.recruit.service;

import com.recruit.service.dto.DeliveryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Delivery.
 */
public interface DeliveryService {

    /**
     * Save a delivery.
     *
     * @param deliveryDTO the entity to save
     * @return the persisted entity
     */
    DeliveryDTO save(DeliveryDTO deliveryDTO);

    /**
     * Get all the deliveries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DeliveryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" delivery.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DeliveryDTO findOne(Long id);

    /**
     * Delete the "id" delivery.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the delivery corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DeliveryDTO> search(String query, Pageable pageable);

    DeliveryDTO findOneByPositionIdAndUserId(Long posId, Long userId);

    Page<DeliveryDTO> findAllByUserCompany(Pageable pageable);

    Page<DeliveryDTO> findAllByUser(Pageable pageable);
}
