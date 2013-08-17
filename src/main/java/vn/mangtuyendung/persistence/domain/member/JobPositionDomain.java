package vn.mangtuyendung.persistence.domain.member;

import java.io.Serializable;
import java.util.Date;

public class JobPositionDomain
        implements Serializable {

    private long id;
    private long profileId;
    private String companyName;
    private String title;
    private String address;
    private boolean currentHere;
    private int startDateMonth;
    private int endDateMonth;
    private int startDateYear;
    private int endDateYear;
    private String description;
    private String time;
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

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isCurrentHere() {
        return this.currentHere;
    }

    public void setCurrentHere(boolean currentHere) {
        this.currentHere = currentHere;
    }

    public int getStartDateMonth() {
        return this.startDateMonth;
    }

    public void setStartDateMonth(int startDateMonth) {
        this.startDateMonth = startDateMonth;
    }

    public int getEndDateMonth() {
        return this.endDateMonth;
    }

    public void setEndDateMonth(int endDateMonth) {
        this.endDateMonth = endDateMonth;
    }

    public int getStartDateYear() {
        return this.startDateYear;
    }

    public void setStartDateYear(int startDateYear) {
        this.startDateYear = startDateYear;
    }

    public int getEndDateYear() {
        return this.endDateYear;
    }

    public void setEndDateYear(int endDateYear) {
        this.endDateYear = endDateYear;
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