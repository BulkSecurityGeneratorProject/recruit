package com.recruit.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Resume entity.
 */
public class ResumeDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String name;

    private String sex;

    private Instant birth;

    private String email;

    private String wechat;

    private String state;

    private String advantage;

    private String place;

    private Instant workTime;

    private String experience;

    private String undergo;

    private String education;

    private String salary;

    private String position;

    private Long userId;

    private String enclosure;

    private Set<PositionDTO> positions = new HashSet<>();

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Instant getBirth() {
        return birth;
    }

    public void setBirth(Instant birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Instant getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Instant workTime) {
        this.workTime = workTime;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getUndergo() {
        return undergo;
    }

    public void setUndergo(String undergo) {
        this.undergo = undergo;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public Set<PositionDTO> getPositions() {
        return positions;
    }

    public void setPositions(Set<PositionDTO> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResumeDTO resumeDTO = (ResumeDTO) o;
        if(resumeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resumeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResumeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sex='" + getSex() + "'" +
            ", birth='" + getBirth() + "'" +
            ", email='" + getEmail() + "'" +
            ", wechat='" + getWechat() + "'" +
            ", state='" + getState() + "'" +
            ", advantage='" + getAdvantage() + "'" +
            ", place='" + getPlace() + "'" +
            ", workTime='" + getWorkTime() + "'" +
            ", experience='" + getExperience() + "'" +
            ", undergo='" + getUndergo() + "'" +
            ", education='" + getEducation() + "'" +
            ", salary='" + getSalary() + "'" +
            ", position='" + getPosition() + "'" +
            ", userId=" + getUserId() +
            ", enclosure='" + getEnclosure() + "'" +
            "}";
    }
}
