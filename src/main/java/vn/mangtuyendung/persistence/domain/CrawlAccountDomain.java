package vn.mangtuyendung.persistence.domain;

import java.util.Date;

public class CrawlAccountDomain {

    private long id;
    private long preferenceId;
    private String domain;
    private String email;
    private String mobile;
    private String preferenceMobile;
    private boolean isCreated;
    private Date created;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPreferenceId() {
        return this.preferenceId;
    }

    public void setPreferenceId(long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferenceMobile() {
        return this.preferenceMobile;
    }

    public void setPreferenceMobile(String preferenceMobile) {
        this.preferenceMobile = preferenceMobile;
    }

    public boolean isIsCreated() {
        return this.isCreated;
    }

    public void setIsCreated(boolean isCreated) {
        this.isCreated = isCreated;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
