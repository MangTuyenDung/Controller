package vn.mangtuyendung.persistence.domain;

import java.sql.Timestamp;

public class CrawlMemberDomain {

    private long id;
    private String url;
    private float boost;
    private String domain;
    private String content;
    private String title;
    private String personName;
    private String personBirthDay;
    private String personSex;
    private String personMarried;
    private String personAddress;
    private String personPhone;
    private String personMobile;
    private String personEmail;
    private String jobTitle;
    private String jobMemberLevel;
    private String jobDesired;
    private String jobSkills;
    private String jobSalaryCurrent;
    private String jobSalaryDesired;
    private String jobWorkForm;
    private String jobSex;
    private String jobCategory;
    private String jobLocation;
    private String skillEducation;
    private String skillIT;
    private String skillComunication;
    private String skillEnglish;
    private String jobEducation;
    private String jobExperience;
    private Timestamp created;
    private Timestamp updated;

    public long getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    public float getBoost() {
        return this.boost;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getContent() {
        return this.content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPersonName() {
        return this.personName;
    }

    public String getPersonBirthDay() {
        return this.personBirthDay;
    }

    public String getPersonSex() {
        return this.personSex;
    }

    public String getPersonMarried() {
        return this.personMarried;
    }

    public String getPersonAddress() {
        return this.personAddress;
    }

    public String getPersonPhone() {
        return this.personPhone;
    }

    public String getPersonMobile() {
        return this.personMobile;
    }

    public String getPersonEmail() {
        return this.personEmail;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public String getJobMemberLevel() {
        return this.jobMemberLevel;
    }

    public String getJobDesired() {
        return this.jobDesired;
    }

    public String getJobSkills() {
        return this.jobSkills;
    }

    public String getJobSalaryCurrent() {
        return this.jobSalaryCurrent;
    }

    public String getJobSalaryDesired() {
        return this.jobSalaryDesired;
    }

    public String getJobSex() {
        return this.jobSex;
    }

    public void setJobSex(String jobSex) {
        this.jobSex = jobSex;
    }

    public String getJobWorkForm() {
        return this.jobWorkForm;
    }

    public String getJobCategory() {
        return this.jobCategory;
    }

    public String getJobLocation() {
        return this.jobLocation;
    }

    public String getSkillEducation() {
        return this.skillEducation;
    }

    public String getSkillIT() {
        return this.skillIT;
    }

    public String getSkillComunication() {
        return this.skillComunication;
    }

    public String getSkillEnglish() {
        return this.skillEnglish;
    }

    public String getJobEducation() {
        return this.jobEducation;
    }

    public String getJobExperience() {
        return this.jobExperience;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBoost(float boost) {
        this.boost = boost;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setPersonBirthDay(String personBirthDay) {
        this.personBirthDay = personBirthDay;
    }

    public void setPersonSex(String personSex) {
        this.personSex = personSex;
    }

    public void setPersonMarried(String personMarried) {
        this.personMarried = personMarried;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    public void setPersonMobile(String personMobile) {
        this.personMobile = personMobile;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setJobMemberLevel(String jobMemberLevel) {
        this.jobMemberLevel = jobMemberLevel;
    }

    public void setJobDesired(String jobDesired) {
        this.jobDesired = jobDesired;
    }

    public void setJobSkills(String jobSkills) {
        this.jobSkills = jobSkills;
    }

    public void setJobSalaryCurrent(String jobSalaryCurrent) {
        this.jobSalaryCurrent = jobSalaryCurrent;
    }

    public void setJobSalaryDesired(String jobSalaryDesired) {
        this.jobSalaryDesired = jobSalaryDesired;
    }

    public void setJobWorkForm(String jobWorkForm) {
        this.jobWorkForm = jobWorkForm;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public void setSkillEducation(String skillEducation) {
        this.skillEducation = skillEducation;
    }

    public void setSkillIT(String skillIT) {
        this.skillIT = skillIT;
    }

    public void setSkillComunication(String skillComunication) {
        this.skillComunication = skillComunication;
    }

    public void setSkillEnglish(String skillEnglish) {
        this.skillEnglish = skillEnglish;
    }

    public void setJobEducation(String jobEducation) {
        this.jobEducation = jobEducation;
    }

    public void setJobExperience(String jobExperience) {
        this.jobExperience = jobExperience;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return this.updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}