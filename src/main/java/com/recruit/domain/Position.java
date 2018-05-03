package com.recruit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.recruit.domain.enumeration.PositionType;

/**
 * A Position.
 */
@Entity
@Table(name = "position")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "position")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_describe")
    private String describe;

    @Column(name = "place")
    private String place;

    @Column(name = "experience")
    private String experience;

    @Column(name = "education")
    private String education;

    @Column(name = "salary")
    private String salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private PositionType type;

    @Column(name = "jhi_order")
    private Long order;

    @ManyToOne
    private Company company;

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

    public Position name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public Position describe(String describe) {
        this.describe = describe;
        return this;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPlace() {
        return place;
    }

    public Position place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getExperience() {
        return experience;
    }

    public Position experience(String experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public Position education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSalary() {
        return salary;
    }

    public Position salary(String salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public PositionType getType() {
        return type;
    }

    public Position type(PositionType type) {
        this.type = type;
        return this;
    }

    public void setType(PositionType type) {
        this.type = type;
    }

    public Long getOrder() {
        return order;
    }

    public Position order(Long order) {
        this.order = order;
        return this;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Company getCompany() {
        return company;
    }

    public Position company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        Position position = (Position) o;
        if (position.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), position.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Position{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", describe='" + getDescribe() + "'" +
            ", place='" + getPlace() + "'" +
            ", experience='" + getExperience() + "'" +
            ", education='" + getEducation() + "'" +
            ", salary='" + getSalary() + "'" +
            ", type='" + getType() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
