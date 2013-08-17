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

import java.sql.Timestamp;

/**
 * Job detail.
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class Job {

    /**
     * Tin cho duyet.
     */
    public static final byte PEDING = 0;
    /**
     * Tin can xuat ban.
     */
    public static final byte PUBLICED = 2;
    /**
     * Tin da duyet.
     */
    public static final byte VERRYFIED = 3;
    /**
     * Tin tu choi duyet.
     */
    public static final byte DENIED = 3;
    /**
     * Tin het han dang.
     */
    public static final byte EXPIRED = 4; //expires
    private long id;
    private long company;
    private long contact;
    private long requirement;
    private long overview;
    private String title;
    private byte status;
    private Timestamp created;
    private Timestamp updated;
    private Timestamp publiced;
    private Timestamp expired;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompany() {
        return company;
    }

    public void setCompany(long company) {
        this.company = company;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public long getRequirement() {
        return requirement;
    }

    public void setRequirement(long requirement) {
        this.requirement = requirement;
    }

    public long getOverview() {
        return overview;
    }

    public void setOverview(long overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
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

    public Timestamp getPubliced() {
        return publiced;
    }

    public void setPubliced(Timestamp publiced) {
        this.publiced = publiced;
    }

    public Timestamp getExpired() {
        return expired;
    }

    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }
}
