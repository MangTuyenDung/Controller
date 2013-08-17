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
package vn.mangtuyendung.solr.fields;

/**
 * Job Solr fields.
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public interface SearchableJob {

    String ID_FIELD = "id";
    String TITLE_FIELD = "title";
    String URL_FIELD = "url";
    String BOOST_FIELD = "boost";
    String DOMAIN_FIELD = "domain";
    String CONTENT_FIELD = "content";
    String SIGNATURE_FIELD = "signature";
    //Company
    String COMPANY_NAME_FIELD = "companyName";
    String COMPANY_ORVERVIEW_FIELD = "companyOverview";
    String COMPANY_ADDRESS_FIELD = "companyAddress";
    String COMPANY_RANGE_FIELD = "companyRange";
    //Job overview
    String SOURCE_CATEGORY_FIELD = "sourceCategory";
    String JOB_CATEGORY_FIELD = "jobCategory";
    String JOB_LOCATION_FIELD = "jobLocation";
    String JOB_TIME_WORK_FIELD = "jobTimeWork";
    String JOB_MEMBER_LEVEL_FIELD = "jobMemberLevel";
    String JOB_SALARY_FIELD = "jobSalary";
    String JOB_AGE_FIELD = "jobAge";
    String JOB_SEX_FIELD = "jobSex";
    String JOB_OVERVIEW_FIELD = "jobOverview";
    //Job Requirement
    String JOB_EDUCATION_LEVEL_FIELD = "jobEducationLevel";
    String JOB_EXPERIENCE_LEVEL_FIELD = "jobExperienceLevel";
    String JOB_REQUIREMENT_FIELD = "jobRequirement";
    //Job contact
    String JOB_LANGUAGE_FIELD = "jobLanguage";
    String JOB_CONTACT_DETAIL_FIELD = "jobContactDetail";
    String JOB_CONTACT_NAME_FIELD = "jobContactName";
    String JOB_CONTACT_ADDRESS_FIELD = "jobContactAddress";
    String JOB_CONTACT_PERSON_FIELD = "jobContactPerson";
    String JOB_CONTACT_PHONE_FIELD = "jobContactPhone";
    String JOB_CONTACT_EMAIL_FIELD = "jobContactEmail";
    //Job
    String JOB_CREATED_FIELD = "created";
    String JOB_UPDATED_FIELD = "updated";
    String JOB_EXPIRED_FIELD = "expired";
}
