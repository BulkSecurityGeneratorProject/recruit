package com.recruit.repository;

import com.recruit.domain.Resume;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Resume entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Resume findOneByUserId(Long id);

    void deleteByUserId(Long id);
}
