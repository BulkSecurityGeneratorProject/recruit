package com.recruit.service.impl;

import com.recruit.domain.Company;
import com.recruit.repository.CompanyRepository;
import com.recruit.repository.search.CompanySearchRepository;
import com.recruit.service.CompanyService;
import com.recruit.service.UserService;
import com.recruit.service.dto.CompanyDTO;
import com.recruit.service.mapper.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    private final CompanySearchRepository companySearchRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper, CompanySearchRepository companySearchRepository) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.companySearchRepository = companySearchRepository;
    }

    @Autowired
    private UserService userService;

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        CompanyDTO result = companyMapper.toDto(company);
        companySearchRepository.save(company);
        return result;
    }

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable)
            .map(v->{
                CompanyDTO companyDTO= companyMapper.toDto(v);
                userService.getUserWithAuthorities(companyDTO.getUserId())
                    .ifPresent(x-> companyDTO.setUserName(x.getLastName()+x.getFirstName()));
                return companyDTO;
            });
    }

    /**
     * Get one company by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompanyDTO findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        Company company = companyRepository.findOne(id);
        CompanyDTO companyDTO = companyMapper.toDto(company);
        userService.getUserWithAuthorities(companyDTO.getUserId()).ifPresent(v->{
            companyDTO.setUserName(v.getLastName()+v.getFirstName());
        });
        return companyDTO;
    }

    /**
     * Get one company by user id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompanyDTO findByUserId(Long id) {
        log.debug("Request to get Company By UserId: {}", id);
        Company company = companyRepository.findOneByUserId(id);
        CompanyDTO companyDTO = companyMapper.toDto(company);
        userService.getUserWithAuthorities(companyDTO.getUserId()).ifPresent(v->{
            companyDTO.setUserName(v.getLastName()+v.getFirstName());
        });
        return companyDTO;
    }

    /**
     * Delete the company by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.delete(id);
        companySearchRepository.delete(id);
    }

    /**
     * Search for the company corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Companies for query {}", query);
        Page<Company> result = companySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(v->{
            CompanyDTO companyDTO= companyMapper.toDto(v);
            userService.getUserWithAuthorities(companyDTO.getUserId())
                .ifPresent(x-> companyDTO.setUserName(x.getLastName()+x.getFirstName()));
            return companyDTO;
        });
    }
}
