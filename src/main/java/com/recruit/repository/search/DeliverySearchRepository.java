package com.recruit.repository.search;

import com.recruit.domain.Delivery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Delivery entity.
 */
public interface DeliverySearchRepository extends ElasticsearchRepository<Delivery, Long> {
    void deleteByPositionID(Long id);

    void deleteByUserID(Long id);
}
