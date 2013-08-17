package vn.mangtuyendung.form.profile;

import java.util.Date;

public class PersonEducationForm {

    private long id;
    private long profileId;
    private String schoolName;
    private String schoolFieldOfStudy;
    private int startYear;
    private int endYear;
    private String description;
    private String time;
    private String schoolSuccess;
    private Date created;
    private Date updated;

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

    public String getSchoolName() {
        return this.schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolFieldOfStudy() {
        return this.schoolFieldOfStudy;
    }

    public void setSchoolFieldOfStudy(String schoolFieldOfStudy) {
        this.schoolFieldOfStudy = schoolFieldOfStudy;
    }

    public int getStartYear() {
        return this.startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return this.endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSchoolSuccess() {
        return this.schoolSuccess;
    }

    public void setSchoolSuccess(String schoolSuccess) {
        this.schoolSuccess = schoolSuccess;
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
}