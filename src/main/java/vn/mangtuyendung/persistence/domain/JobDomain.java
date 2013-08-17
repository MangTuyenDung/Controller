/*
 * Copyright 2012 Hadoop Vietnam <admin@hadoopvietnam.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vn.mangtuyendung.persistence.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import org.apache.solr.client.solrj.beans.Field;
import vn.mangtuyendung.solr.fields.SearchableJob;

/**
 * Job
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class JobDomain implements SearchableJob, Serializable {

    @Field(ID_FIELD)
    private Long id;
    @Field(TITLE_FIELD)
    private String title;
    private String higthligthTitle;
    @Field(URL_FIELD)
    private String url;
    @Field(BOOST_FIELD)
    private Float boost;
    @Field(DOMAIN_FIELD)
    private String domain;
    @Field(CONTENT_FIELD)
    private String content;
    @Field(SIGNATURE_FIELD)
    private String signature;
    //Company
    @Field(COMPANY_NAME_FIELD)
    private String companyName;
    @Field(COMPANY_ORVERVIEW_FIELD)
    private String companyOverview;
    @Field(COMPANY_ADDRESS_FIELD)
    private String companyAddress;
    @Field(COMPANY_RANGE_FIELD)
    private String companyRange;
    //Job overview
    @Field(SOURCE_CATEGORY_FIELD)
    private String sourceCategory;
    @Field(JOB_CATEGORY_FIELD)
    private List<String> jobCategory;
    @Field(JOB_LOCATION_FIELD)
    private List<String> jobLocation;
    @Field(JOB_TIME_WORK_FIELD)
    private String jobTimeWork;
    @Field(JOB_MEMBER_LEVEL_FIELD)
    private List<String> jobMemberLevel;
    @Field(JOB_SALARY_FIELD)
    private String jobSalary;
    @Field(JOB_AGE_FIELD)
    private String jobAge;
    @Field(JOB_SEX_FIELD)
    private String jobSex;
    @Field(JOB_OVERVIEW_FIELD)
    private String jobOverview;
    //Job Requirement
    @Field(JOB_EDUCATION_LEVEL_FIELD)
    private String jobEducationLevel;
    @Field(JOB_EXPERIENCE_LEVEL_FIELD)
    private String jobExperienceLevel;
    @Field(JOB_REQUIREMENT_FIELD)
    private String jobRequirement;
    //Job contact
    @Field(JOB_LANGUAGE_FIELD)
    private String jobLanguage;
    @Field(JOB_CONTACT_DETAIL_FIELD)
    private String jobContactDetail;
    @Field(JOB_CONTACT_NAME_FIELD)
    private String jobContactName;
    @Field(JOB_CONTACT_ADDRESS_FIELD)
    private String jobContactAddress;
    @Field(JOB_CONTACT_PERSON_FIELD)
    private String jobContactPerson;
    @Field(JOB_CONTACT_PHONE_FIELD)
    private String jobContactPhone;
    @Field(JOB_CONTACT_EMAIL_FIELD)
    private String jobContactEmail;
    //Job
    private byte status;
    @Field(JOB_CREATED_FIELD)
    private Timestamp created;
    @Field(JOB_UPDATED_FIELD)
    private Timestamp updated;
    private Timestamp publiced;
    private String jobExpired;
    @Field(JOB_EXPIRED_FIELD)
    private Timestamp expired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHigthligthTitle() {
        return higthligthTitle;
    }

    public void setHigthligthTitle(String higthligthTitle) {
        this.higthligthTitle = higthligthTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getBoost() {
        return boost;
    }

    public void setBoost(Float boost) {
        this.boost = boost;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyOverview() {
        return companyOverview;
    }

    public void setCompanyOverview(String companyOverview) {
        this.companyOverview = companyOverview;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyRange() {
        return companyRange;
    }

    public void setCompanyRange(String companyRange) {
        this.companyRange = companyRange;
    }

    public String getSourceCategory() {
        return sourceCategory;
    }

    public void setSourceCategory(String sourceCategory) {
        this.sourceCategory = sourceCategory;
    }

    public List<String> getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(List<String> jobCategory) {
        this.jobCategory = jobCategory;
    }

    public List<String> getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(List<String> jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobTimeWork() {
        return jobTimeWork;
    }

    public void setJobTimeWork(String jobTimeWork) {
        this.jobTimeWork = jobTimeWork;
    }

    public List<String> getJobMemberLevel() {
        return jobMemberLevel;
    }

    public void setJobMemberLevel(List<String> jobMemberLevel) {
        this.jobMemberLevel = jobMemberLevel;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobAge() {
        return jobAge;
    }

    public void setJobAge(String jobAge) {
        this.jobAge = jobAge;
    }

    public String getJobSex() {
        return jobSex;
    }

    public void setJobSex(String jobSex) {
        this.jobSex = jobSex;
    }

    public String getJobOverview() {
        return jobOverview;
    }

    public void setJobOverview(String jobOverview) {
        this.jobOverview = jobOverview;
    }

    public String getJobEducationLevel() {
        return jobEducationLevel;
    }

    public void setJobEducationLevel(String jobEducationLevel) {
        this.jobEducationLevel = jobEducationLevel;
    }

    public String getJobExperienceLevel() {
        return jobExperienceLevel;
    }

    public void setJobExperienceLevel(String jobExperienceLevel) {
        this.jobExperienceLevel = jobExperienceLevel;
    }

    public String getJobRequirement() {
        return jobRequirement;
    }

    public void setJobRequirement(String jobRequirement) {
        this.jobRequirement = jobRequirement;
    }

    public String getJobLanguage() {
        return jobLanguage;
    }

    public void setJobLanguage(String jobLanguage) {
        this.jobLanguage = jobLanguage;
    }

    public String getJobContactDetail() {
        return jobContactDetail;
    }

    public void setJobContactDetail(String jobContactDetail) {
        this.jobContactDetail = jobContactDetail;
    }

    public String getJobContactName() {
        return jobContactName;
    }

    public void setJobContactName(String jobContactName) {
        this.jobContactName = jobContactName;
    }

    public String getJobContactAddress() {
        return jobContactAddress;
    }

    public void setJobContactAddress(String jobContactAddress) {
        this.jobContactAddress = jobContactAddress;
    }

    public String getJobContactPerson() {
        return jobContactPerson;
    }

    public void setJobContactPerson(String jobContactPerson) {
        this.jobContactPerson = jobContactPerson;
    }

    public String getJobContactPhone() {
        return jobContactPhone;
    }

    public void setJobContactPhone(String jobContactPhone) {
        this.jobContactPhone = jobContactPhone;
    }

    public String getJobContactEmail() {
        return jobContactEmail;
    }

    public void setJobContactEmail(String jobContactEmail) {
        this.jobContactEmail = jobContactEmail;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Timestamp getPubliced() {
        return publiced;
    }

    public void setPubliced(Timestamp publiced) {
        this.publiced = publiced;
    }

    public String getJobExpired() {
        return jobExpired;
    }

    public void setJobExpired(String jobExpired) {
        this.jobExpired = jobExpired;
    }

    public Timestamp getExpired() {
        return expired;
    }

    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }
}
