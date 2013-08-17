package vn.mangtuyendung.form;

import java.util.Date;

public class AdminApplyForm
{
  private long id;
  private long profileId;
  private String username;
  private String jobId;
  private String jobName;
  private String jobCompany;
  private Date applyCreated;
  private Date jobExpired;
  private String title;
  private String description;

  public long getId()
  {
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
}