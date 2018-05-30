package com.recruit.service.mapper;

import com.recruit.domain.*;
import com.recruit.service.dto.DeliveryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Delivery and its DTO DeliveryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeliveryMapper extends EntityMapper<DeliveryDTO, Delivery> {



    default Delivery fromId(Long id) {
        if (id == null) {
            return null;
        }
        Delivery delivery = new Delivery();
        delivery.setId(id);
        return delivery;
    }
}
