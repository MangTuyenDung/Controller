package vn.mangtuyendung.persistence.domain.member;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProfileDomain
        implements Serializable {

    private long id;
    private long userId;
    private String personName;
    private Date personBirthDay;
    private String personSex;
    private String personMarried;
    private String personAddress;
    private String personMobile;
    private String currentTitle;
    private String currentCompany;
    private String resumeOverview;
    private List<JobEducationDomain> educations;
    private List<JobPositionDomain> positions;
    private int profileLevel;
    private Date created;
    private Date updated;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Date getPersonBirthDay() {
        return this.personBirthDay;
    }

    public void setPersonBirthDay(Date personBirthDay) {
        this.personBirthDay = personBirthDay;
    }

    public String getPersonSex() {
        return this.personSex;
    }

    public void setPersonSex(String personSex) {
        this.personSex = personSex;
    }

    public String getPersonMarried() {
        return this.personMarried;
    }

    public void setPersonMarried(String personMarried) {
        this.personMarried = personMarried;
    }

    public String getPersonAddress() {
        return this.personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public String getPersonMobile() {
        return this.personMobile;
    }

    public void setPersonMobile(String personMobile) {
        this.personMobile = personMobile;
    }

    public String getCurrentTitle() {
        return this.currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }

    public String getCurrentCompany() {
        return this.currentCompany;
    }

    public void setCurrentCompany(String currentCompany) {
        this.currentCompany = currentCompany;
    }

    public String getResumeOverview() {
        return this.resumeOverview;
    }

    public void setResumeOverview(String resumeOverview) {
        this.resumeOverview = resumeOverview;
    }

    public List<JobEducationDomain> getEducations() {
        return this.educations;
    }

    public void setEducations(List<JobEducationDomain> educations) {
        this.educations = educations;
    }

    public List<JobPositionDomain> getPositions() {
        return this.positions;
    }

    public void setPositions(List<JobPositionDomain> positions) {
        this.positions = positions;
    }

    public int getProfileLevel() {
        return this.profileLevel;
    }

    public void setProfileLevel(int profileLevel) {
        this.profileLevel = profileLevel;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}