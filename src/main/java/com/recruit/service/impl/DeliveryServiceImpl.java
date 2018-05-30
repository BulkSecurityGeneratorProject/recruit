package com.recruit.service.impl;

import com.recruit.service.DeliveryService;
import com.recruit.domain.Delivery;
import com.recruit.repository.DeliveryRepository;
import com.recruit.repository.search.DeliverySearchRepository;
import com.recruit.service.dto.DeliveryDTO;
import com.recruit.service.mapper.DeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Delivery.
 */
@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private final Logger log = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    private final DeliveryRepository deliveryRepository;

    private final DeliveryMapper deliveryMapper;

    private final DeliverySearchRepository deliverySearchRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper, DeliverySearchRepository deliverySearchRepository) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
        this.deliverySearchRepository = deliverySearchRepository;
    }

    /**
     * Save a delivery.
     *
     * @param deliveryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DeliveryDTO save(DeliveryDTO deliveryDTO) {
        deliveryDTO.setTimestamp(Instant.now());
        log.debug("Request to save Delivery : {}", deliveryDTO);
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);
        delivery = deliveryRepository.save(delivery);
        DeliveryDTO result = deliveryMapper.toDto(delivery);
        deliverySearchRepository.save(delivery);
        return result;
    }

    /**
     * Get all the deliveries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Deliveries");
        return deliveryRepository.findAll(pageable)
            .map(deliveryMapper::toDto);
    }

    /**
     * Get one delivery by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DeliveryDTO findOne(Long id) {
        log.debug("Request to get Delivery : {}", id);
        Delivery delivery = deliveryRepository.findOne(id);
        return deliveryMapper.toDto(delivery);
    }

    /**
     * Delete the delivery by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Delivery : {}", id);
        deliveryRepository.delete(id);
        deliverySearchRepository.delete(id);
    }

    /**
     * Search for the delivery corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Deliveries for query {}", query);
        Page<Delivery> result = deliverySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(deliveryMapper::toDto);
    }

    @Override
    public DeliveryDTO findOneByPositionIdAndUserId(Long posId, Long userId) {
        Delivery delivery = deliveryRepository.findOneByUserIDAndPositionID(userId,posId);
        return deliveryMapper.toDto(delivery);
    }
}
