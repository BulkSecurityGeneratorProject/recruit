package com.recruit.repository.search;

import com.recruit.domain.Position;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Position entity.
 */
public interface PositionSearchRepository extends ElasticsearchRepository<Position, Long> {
    void deleteByCompany_UserId(Long id);

    void deleteByCompanyId(Long id);
}
