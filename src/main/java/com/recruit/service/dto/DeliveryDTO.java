package com.recruit.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

import com.recruit.domain.enumeration.DeliveryType;

/**
 * A DTO for the Delivery entity.
 */
public class DeliveryDTO implements Serializable {

    private Long id;

    private Long userID;

    private Long positionID;

    private DeliveryType status;

    private Instant timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getPositionID() {
        return positionID;
    }

    public void setPositionID(Long positionID) {
        this.positionID = positionID;
    }

    public DeliveryType getStatus() {
        return status;
    }

    public void setStatus(DeliveryType status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeliveryDTO deliveryDTO = (DeliveryDTO) o;
        if (deliveryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deliveryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeliveryDTO{" +
            "id=" + getId() +
            ", userID=" + getUserID() +
            ", positionID=" + getPositionID() +
            ", status='" + getStatus() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
