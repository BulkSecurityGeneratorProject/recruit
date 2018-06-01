package com.recruit.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.recruit.domain.enumeration.CompanyType;

/**
 * A DTO for the Company entity.
 */
public class CompanyDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean financing;

    private String personNumber;

    private String describe;

    private String address;

    private Long userId;

    private String userName;

    private CompanyType type;

    private Long order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isFinancing() {
        return financing;
    }

    public void setFinancing(Boolean financing) {
        this.financing = financing;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CompanyType getType() {
        return type;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if(companyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", financing='" + isFinancing() + "'" +
            ", personNumber='" + getPersonNumber() + "'" +
            ", describe='" + getDescribe() + "'" +
            ", address='" + getAddress() + "'" +
            ", userId=" + getUserId() +
            ", userName=" + getUserName() +
            ", type='" + getType() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
