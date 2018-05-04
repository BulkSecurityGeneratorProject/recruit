package com.recruit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Resume.
 */
@Entity
@Table(name = "resume")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "resume")
public class Resume extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birth")
    private Instant birth;

    @Column(name = "email")
    private String email;

    @Column(name = "wechat")
    private String wechat;

    @Column(name = "state")
    private String state;

    @Column(name = "advantage")
    private String advantage;

    @Column(name = "target_place")
    private String targetPlace;

    @Column(name = "work_time")
    private Instant workTime;

    @Column(name = "experience")
    private String experience;

    @Column(name = "undergo")
    private String undergo;

    @Column(name = "education")
    private String education;

    @Column(name = "target_salary")
    private String targetSalary;

    @Column(name = "target_position")
    private String targetPosition;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "enclosure")
    private String enclosure;

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

    public Resume name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public Resume sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Instant getBirth() {
        return birth;
    }

    public Resume birth(Instant birth) {
        this.birth = birth;
        return this;
    }

    public void setBirth(Instant birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public Resume email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public Resume wechat(String wechat) {
        this.wechat = wechat;
        return this;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getState() {
        return state;
    }

    public Resume state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAdvantage() {
        return advantage;
    }

    public Resume advantage(String advantage) {
        this.advantage = advantage;
        return this;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getTargetPlace() {
        return targetPlace;
    }

    public Resume targetPlace(String targetPlace) {
        this.targetPlace = targetPlace;
        return this;
    }

    public void setTargetPlace(String targetPlace) {
        this.targetPlace = targetPlace;
    }

    public Instant getWorkTime() {
        return workTime;
    }

    public Resume workTime(Instant workTime) {
        this.workTime = workTime;
        return this;
    }

    public void setWorkTime(Instant workTime) {
        this.workTime = workTime;
    }

    public String getExperience() {
        return experience;
    }

    public Resume experience(String experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getUndergo() {
        return undergo;
    }

    public Resume undergo(String undergo) {
        this.undergo = undergo;
        return this;
    }

    public void setUndergo(String undergo) {
        this.undergo = undergo;
    }

    public String getEducation() {
        return education;
    }

    public Resume education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTargetSalary() {
        return targetSalary;
    }

    public Resume targetSalary(String targetSalary) {
        this.targetSalary = targetSalary;
        return this;
    }

    public void setTargetSalary(String targetSalary) {
        this.targetSalary = targetSalary;
    }

    public String getTargetPosition() {
        return targetPosition;
    }

    public Resume targetPosition(String targetPosition) {
        this.targetPosition = targetPosition;
        return this;
    }

    public void setTargetPosition(String targetPosition) {
        this.targetPosition = targetPosition;
    }

    public Long getUserId() {
        return userId;
    }

    public Resume userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public Resume enclosure(String enclosure) {
        this.enclosure = enclosure;
        return this;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
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
        Resume resume = (Resume) o;
        if (resume.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resume.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resume{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sex='" + getSex() + "'" +
            ", birth='" + getBirth() + "'" +
            ", email='" + getEmail() + "'" +
            ", wechat='" + getWechat() + "'" +
            ", state='" + getState() + "'" +
            ", advantage='" + getAdvantage() + "'" +
            ", targetPlace='" + getTargetPlace() + "'" +
            ", workTime='" + getWorkTime() + "'" +
            ", experience='" + getExperience() + "'" +
            ", undergo='" + getUndergo() + "'" +
            ", education='" + getEducation() + "'" +
            ", targetSalary='" + getTargetSalary() + "'" +
            ", targetPosition='" + getTargetPosition() + "'" +
            ", userId=" + getUserId() +
            ", enclosure='" + getEnclosure() + "'" +
            "}";
    }
}
