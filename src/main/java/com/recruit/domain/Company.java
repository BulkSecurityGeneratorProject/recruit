package com.recruit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.recruit.domain.enumeration.CompanyType;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "company")
public class Company extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "financing")
    private Boolean financing;

    @Column(name = "person_number")
    private String personNumber;

    @Column(name = "jhi_describe")
    private String describe;

    @Column(name = "address")
    private String address;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private CompanyType type;

    @Column(name = "jhi_order")
    private Long order;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Position> positions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isFinancing() {
        return financing;
    }

    public Company financing(Boolean financing) {
        this.financing = financing;
        return this;
    }

    public void setFinancing(Boolean financing) {
        this.financing = financing;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public Company personNumber(String personNumber) {
        this.personNumber = personNumber;
        return this;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public String getDescribe() {
        return describe;
    }

    public Company describe(String describe) {
        this.describe = describe;
        return this;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAddress() {
        return address;
    }

    public Company address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public Company userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CompanyType getType() {
        return type;
    }

    public Company type(CompanyType type) {
        this.type = type;
        return this;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }

    public Long getOrder() {
        return order;
    }

    public Company order(Long order) {
        this.order = order;
        return this;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public Company positions(Set<Position> positions) {
        this.positions = positions;
        return this;
    }

    public Company addPosition(Position position) {
        this.positions.add(position);
        position.setCompany(this);
        return this;
    }

    public Company removePosition(Position position) {
        this.positions.remove(position);
        position.setCompany(null);
        return this;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", financing='" + isFinancing() + "'" +
            ", personNumber='" + getPersonNumber() + "'" +
            ", describe='" + getDescribe() + "'" +
            ", address='" + getAddress() + "'" +
            ", userId=" + getUserId() +
            ", type='" + getType() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
