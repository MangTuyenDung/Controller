package vn.mangtuyendung.persistence.domain;

import java.io.Serializable;
import java.util.Date;

public class ApplyDomain
        implements Serializable {

    private long id;
    private String username;
    private String jobId;
    private String title;
    private String description;
    private boolean isViewed;
    private Date created;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJobId() {
        return this.jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsViewed() {
        return this.isViewed;
    }

    public void setIsViewed(boolean isViewed) {
        this.isViewed = isViewed;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}