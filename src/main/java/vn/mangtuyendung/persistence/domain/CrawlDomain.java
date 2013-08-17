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

/**
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class CrawlDomain implements Serializable {

    private long id;
    private String title;
    private String url;
    private float boost;
    private String domain;
    private String content;
    //Company
    private String companyName;
    private String companyOverview;
    private String companyAddress;
    private String companyRange;
    //Job overview
    private String jobCategory;
    private String jobLocation;
    private String jobTimeWork;
    private String jobMemberLevel;
    private String jobSalary;
    private String jobAge;
    private String jobSex;
    private String jobOverview;
    //Job Requirement
    private String jobEducationLevel;
    private String jobExperienceLevel;
    private String jobRequirement;
    //Job contact
    private String jobLanguage;
    private String jobContactDetail;
    private String jobContactName;
    private String jobContactAddress;
    private String jobContactPerson;
    private String jobContactPhone;
    private String jobContactEmail;
    //Job
    private boolean status;
    private Timestamp created;
    private Timestamp updated;
    private Timestamp published;
    private String jobExpired;
    private Timestamp expired;
    //Info
    private String creater;
    private String updater;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getBoost() {
        return boost;
    }

    public void setBoost(float boost) {
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

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobTimeWork() {
        return jobTimeWork;
    }

    public void setJobTimeWork(String jobTimeWork) {
        this.jobTimeWork = jobTimeWork;
    }

    public String getJobMemberLevel() {
        return jobMemberLevel;
    }

    public void setJobMemberLevel(String jobMemberLevel) {
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

    public Timestamp getPublished() {
        return published;
    }

    public void setPublished(Timestamp publiced) {
        this.published = publiced;
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}
