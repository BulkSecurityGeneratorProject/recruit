package com.recruit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruit.domain.enumeration.DeliveryType;
import com.recruit.service.DeliveryService;
import com.recruit.web.rest.errors.BadRequestAlertException;
import com.recruit.web.rest.util.HeaderUtil;
import com.recruit.web.rest.util.PaginationUtil;
import com.recruit.service.dto.DeliveryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Delivery.
 */
@RestController
@RequestMapping("/api")
public class DeliveryResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryResource.class);

    private static final String ENTITY_NAME = "delivery";

    private final DeliveryService deliveryService;

    public DeliveryResource(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    /**
     * POST  /deliveries : Create a new delivery.
     *
     * @param deliveryDTO the deliveryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deliveryDTO, or with status 400 (Bad Request) if the delivery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deliveries")
    @Timed
    public ResponseEntity<DeliveryDTO> createDelivery(@RequestBody DeliveryDTO deliveryDTO) throws URISyntaxException {
        log.debug("REST request to save Delivery : {}", deliveryDTO);
        if (deliveryDTO.getId() != null) {
            throw new BadRequestAlertException("A new delivery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        deliveryDTO.setStatus(DeliveryType.DELIVERED);
        DeliveryDTO result = deliveryService.save(deliveryDTO);
        return ResponseEntity.created(new URI("/api/deliveries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deliveries : Updates an existing delivery.
     *
     * @param deliveryDTO the deliveryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deliveryDTO,
     * or with status 400 (Bad Request) if the deliveryDTO is not valid,
     * or with status 500 (Internal Server Error) if the deliveryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deliveries")
    @Timed
    public ResponseEntity<DeliveryDTO> updateDelivery(@RequestBody DeliveryDTO deliveryDTO) throws URISyntaxException {
        log.debug("REST request to update Delivery : {}", deliveryDTO);
        if (deliveryDTO.getId() == null) {
            return createDelivery(deliveryDTO);
        }
        DeliveryDTO result = deliveryService.save(deliveryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deliveryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deliveries : get all the deliveries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deliveries in body
     */
    @GetMapping("/deliveries")
    @Timed
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries(Pageable pageable) {
        log.debug("REST request to get a page of Deliveries");
        Page<DeliveryDTO> page = deliveryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deliveries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/deliveries/company")
    @Timed
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveriesByCompany(Pageable pageable) {
        log.debug("REST request to get a page of Deliveries");
        Page<DeliveryDTO> page = deliveryService.findAllByUserCompany(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deliveries/company");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/deliveries/user")
    @Timed
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveriesByUser(Pageable pageable) {
        log.debug("REST request to get a page of Deliveries");
        Page<DeliveryDTO> page = deliveryService.findAllByUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deliveries/company");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /deliveries/:id : get the "id" delivery.
     *
     * @param id the id of the deliveryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deliveryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/deliveries/{id}")
    @Timed
    public ResponseEntity<DeliveryDTO> getDelivery(@PathVariable Long id) {
        log.debug("REST request to get Delivery : {}", id);
        DeliveryDTO deliveryDTO = deliveryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deliveryDTO));
    }

    @GetMapping("/deliveries/position/user")
    @Timed
    public DeliveryDTO getDeliveryByPositionAndUser(@RequestParam Long userId, @RequestParam Long posId) {
        log.debug("REST request to get Delivery : user id is {}, pos id is {}", userId, posId);
        return deliveryService.findOneByPositionIdAndUserId(posId, userId);
    }

    /**
     * DELETE  /deliveries/:id : delete the "id" delivery.
     *
     * @param id the id of the deliveryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deliveries/{id}")
    @Timed
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        log.debug("REST request to delete Delivery : {}", id);
        deliveryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/deliveries?query=:query : search for the delivery corresponding
     * to the query.
     *
     * @param query    the query of the delivery search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/deliveries")
    @Timed
    public ResponseEntity<List<DeliveryDTO>> searchDeliveries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Deliveries for query {}", query);
        Page<DeliveryDTO> page = deliveryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/deliveries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
