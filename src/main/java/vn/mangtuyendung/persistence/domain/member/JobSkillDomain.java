package vn.mangtuyendung.persistence.domain.member;

import java.io.Serializable;
import java.util.Date;

public class JobSkillDomain
        implements Serializable {

    private long id;
    private long profileId;
    private String skillName;
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

    public String getSkillName() {
        return this.skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
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