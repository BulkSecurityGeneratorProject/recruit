package com.recruit.service.impl;

import com.recruit.domain.User;
import com.recruit.domain.enumeration.PositionType;
import com.recruit.service.CompanyService;
import com.recruit.service.PositionService;
import com.recruit.domain.Position;
import com.recruit.repository.PositionRepository;
import com.recruit.repository.search.PositionSearchRepository;
import com.recruit.service.UserService;
import com.recruit.service.dto.PositionDTO;
import com.recruit.service.mapper.PositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Position.
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    private final Logger log = LoggerFactory.getLogger(PositionServiceImpl.class);

    private final PositionRepository positionRepository;

    private final PositionMapper positionMapper;

    private final PositionSearchRepository positionSearchRepository;

    public PositionServiceImpl(PositionRepository positionRepository, PositionMapper positionMapper, PositionSearchRepository positionSearchRepository) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
        this.positionSearchRepository = positionSearchRepository;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    /**
     * Save a position.
     *
     * @param positionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PositionDTO save(PositionDTO positionDTO) {
        log.debug("Request to save Position : {}", positionDTO);
        Optional<User> user = userService.getUserWithAuthorities();
        if (positionDTO.getId() == null && user.isPresent()) {
            positionDTO.setCompanyId(companyService.findByUserId(user.get().getId()).getId());
        }
        Position position = positionMapper.toEntity(positionDTO);
        position = positionRepository.save(position);
        PositionDTO result = positionMapper.toDto(position);
        positionSearchRepository.save(position);
        return result;
    }

    /**
     * Get all the positions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Positions");
        return positionRepository.findAll(pageable)
            .map(positionMapper::toDto);
    }

    /**
     * Get all the positions by type.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PositionDTO> findAllByType(PositionType type, Pageable pageable) {
        log.debug("Request to get all Positions by type {}", type);
        return positionRepository.findAllByType(type, pageable)
            .map(positionMapper::toDto);
    }

    /**
     * Get all the positions by login.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PositionDTO> findAllByLogin(Pageable pageable) {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            log.debug("Request to get all Positions by user {}", user.get());
            return positionRepository.findAllByCompany_UserId(user.get().getId(), pageable)
                .map(positionMapper::toDto);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findAllIdsByLogin() {
        List<Long> positions = new ArrayList<>();
        userService.getUserWithAuthorities().ifPresent(user -> {
            log.debug("Request to get all Positions by user {}", user);
            positionRepository.findAllByCompany_UserId(user.getId()).forEach(v -> positions.add(v.getId()));
        });
        return positions;
    }

    /**
     * Get one position by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PositionDTO findOne(Long id) {
        log.debug("Request to get Position : {}", id);
        Position position = positionRepository.findOne(id);
        return positionMapper.toDto(position);
    }

    /**
     * Delete the position by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Position : {}", id);
        positionRepository.delete(id);
        positionSearchRepository.delete(id);
    }

    /**
     * Search for the position corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PositionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Positions for query {}", query);
        Page<Position> result = positionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(positionMapper::toDto);
    }
}
