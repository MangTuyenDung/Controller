package vn.mangtuyendung.form;

import java.util.Date;

public class MyApplyForm {

    private long id;
    private String jobId;
    private String jobName;
    private String jobCompany;
    private Date applyCreated;
    private Date jobExpired;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobId() {
        return this.jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return this.jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobCompany() {
        return this.jobCompany;
    }

    public void setJobCompany(String jobCompany) {
        this.jobCompany = jobCompany;
    }

    public Date getApplyCreated() {
        return this.applyCreated;
    }

    public void setApplyCreated(Date applyCreated) {
        this.applyCreated = applyCreated;
    }

    public Date getJobExpired() {
        return this.jobExpired;
    }

    public void setJobExpired(Date jobExpired) {
        this.jobExpired = jobExpired;
    }
}