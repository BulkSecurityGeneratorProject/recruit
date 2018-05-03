package com.recruit.service.mapper;

import com.recruit.domain.*;
import com.recruit.service.dto.ResumeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Resume and its DTO ResumeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResumeMapper extends EntityMapper<ResumeDTO, Resume> {



    default Resume fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resume resume = new Resume();
        resume.setId(id);
        return resume;
    }
}
