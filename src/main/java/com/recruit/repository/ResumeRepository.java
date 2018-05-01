package com.recruit.repository;

import com.recruit.domain.Resume;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Resume entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("select distinct resume from Resume resume left join fetch resume.positions")
    List<Resume> findAllWithEagerRelationships();

    @Query("select resume from Resume resume left join fetch resume.positions where resume.id =:id")
    Resume findOneWithEagerRelationships(@Param("id") Long id);

}
