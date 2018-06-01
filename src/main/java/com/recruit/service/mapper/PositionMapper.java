package com.recruit.service.mapper;

import com.recruit.domain.*;
import com.recruit.service.dto.PositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Position and its DTO PositionDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface PositionMapper extends EntityMapper<PositionDTO, Position> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    PositionDTO toDto(Position position);

    @Mapping(source = "companyId", target = "company")
    Position toEntity(PositionDTO positionDTO);

    default Position fromId(Long id) {
        if (id == null) {
            return null;
        }
        Position position = new Position();
        position.setId(id);
        return position;
    }
}
