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
package vn.mangtuyendung.persistence.domain.company;

import java.util.Date;

/*
 * CompanyProfileDomain.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Company profile domain
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */
public class CompanyProfileDomain {

    private long id;
    private String companyURL;
    private String companyName;
    private String companyNameEnglish;
    private String companyAddress;
    private String companyPhone;
    private String companyTaxCode;
    private String companyBusinessScope;
    private String companyOverview;
    private String companyRange;
    private String companyWebsite;
    private String companyEmail;
    private boolean verify;
    private long creater;
    private Date created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyURL() {
        return companyURL;
    }

    public void setCompanyURL(String companyURL) {
        this.companyURL = companyURL;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNameEnglish() {
        return companyNameEnglish;
    }

    public void setCompanyNameEnglish(String companyNameEnglish) {
        this.companyNameEnglish = companyNameEnglish;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyTaxCode() {
        return companyTaxCode;
    }

    public void setCompanyTaxCode(String companyTaxCode) {
        this.companyTaxCode = companyTaxCode;
    }

    public String getCompanyBusinessScope() {
        return companyBusinessScope;
    }

    public void setCompanyBusinessScope(String companyBusinessScope) {
        this.companyBusinessScope = companyBusinessScope;
    }

    public String getCompanyOverview() {
        return companyOverview;
    }

    public void setCompanyOverview(String companyOverview) {
        this.companyOverview = companyOverview;
    }

    public String getCompanyRange() {
        return companyRange;
    }

    public void setCompanyRange(String companyRange) {
        this.companyRange = companyRange;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public long getCreater() {
        return creater;
    }

    public void setCreater(long creater) {
        this.creater = creater;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
