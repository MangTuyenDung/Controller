package vn.mangtuyendung.persistence.domain;

import java.util.Date;
import java.util.List;
import vn.mangtuyendung.persistence.domain.member.JobEducationDomain;
import vn.mangtuyendung.persistence.domain.member.JobPositionDomain;

public class JobMemberDomain {

    private long id;
    private long profileId;
    private String url;
    private Float boost;
    private String domain;
    private String title;
    private String content;
    private String signature;
    private String sourceCategory;
    private String firstName;
    private String middleName;
    private String lastName;
    private String personName;
    private String personBirthDay;
    private String personSex;
    private String personMarried;
    private String personAddress;
    private String personMobile;
    private String personEmail;
    private String jobTitle;
    private String jobCompany;
    private String jobMemberLevel;
    private String jobDesired;
    private String jobSkills;
    private String jobSalaryCurrent;
    private String jobSalaryDesired;
    private String jobSex;
    private String jobWorkForm;
    private List<String> jobCategory;
    private List<String> jobLocation;
    private String skillEducation;
    private String skillIT;
    private String skillComunication;
    private String skillEnglish;
    private List<JobEducationDomain> educations;
    private List<JobPositionDomain> positions;
    private Date created;
    private Date updated;
    private long creater;
    private long updater;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProfileId() {
        return this.profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getBoost() {
        return this.boost;
    }

    public void setBoost(Float boost) {
        this.boost = boost;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSourceCategory() {
        return this.sourceCategory;
    }

    public void setSourceCategory(String sourceCategory) {
        this.sourceCategory = sourceCategory;
    }

    public List<String> getJobCategory() {
        return this.jobCategory;
    }

    public void setJobCategory(List<String> jobCategory) {
        this.jobCategory = jobCategory;
    }

    public List<String> getJobLocation() {
        return this.jobLocation;
    }

    public void setJobLocation(List<String> jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonBirthDay() {
        return this.personBirthDay;
    }

    public void setPersonBirthDay(String personBirthDay) {
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

    public String getPersonEmail() {
        return this.personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobCompany() {
        return this.jobCompany;
    }

    public void setJobCompany(String jobCompany) {
        this.jobCompany = jobCompany;
    }

    public String getJobMemberLevel() {
        return this.jobMemberLevel;
    }

    public void setJobMemberLevel(String jobMemberLevel) {
        this.jobMemberLevel = jobMemberLevel;
    }

    public String getJobDesired() {
        return this.jobDesired;
    }

    public void setJobDesired(String jobDesired) {
        this.jobDesired = jobDesired;
    }

    public String getJobSkills() {
        return this.jobSkills;
    }

    public void setJobSkills(String jobSkills) {
        this.jobSkills = jobSkills;
    }

    public String getJobSalaryCurrent() {
        return this.jobSalaryCurrent;
    }

    public void setJobSalaryCurrent(String jobSalaryCurrent) {
        this.jobSalaryCurrent = jobSalaryCurrent;
    }

    public String getJobSalaryDesired() {
        return this.jobSalaryDesired;
    }

    public void setJobSalaryDesired(String jobSalaryDesired) {
        this.jobSalaryDesired = jobSalaryDesired;
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

    public void setJobWorkForm(String jobWorkForm) {
        this.jobWorkForm = jobWorkForm;
    }

    public String getSkillEducation() {
        return this.skillEducation;
    }

    public void setSkillEducation(String skillEducation) {
        this.skillEducation = skillEducation;
    }

    public String getSkillIT() {
        return this.skillIT;
    }

    public void setSkillIT(String skillIT) {
        this.skillIT = skillIT;
    }

    public String getSkillComunication() {
        return this.skillComunication;
    }

    public void setSkillComunication(String skillComunication) {
        this.skillComunication = skillComunication;
    }

    public String getSkillEnglish() {
        return this.skillEnglish;
    }

    public void setSkillEnglish(String skillEnglish) {
        this.skillEnglish = skillEnglish;
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

    public long getCreater() {
        return this.creater;
    }

    public void setCreater(long creater) {
        this.creater = creater;
    }

    public long getUpdater() {
        return this.updater;
    }

    public void setUpdater(long updater) {
        this.updater = updater;
    }
}