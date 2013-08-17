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
 * Job company.
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class JobCompany {

    /**
     * Range 0-10 staff.
     */
    public static final int VERRY_SMALL = 0;
    /**
     * Range 10-24 staff.
     */
    public static final int SMALL = 1;
    /**
     * Range 25-99 staff.
     */
    public static final int MEDIUM = 2;
    /**
     * Range 100-499 staff.
     */
    public static final int LARGE = 3;
    /**
     * Over 500 staff.
     */
    public static final int VERRY_LARGE = 4;
    private long id;
    private String name;
    private String overview;
    private int range;
    private Timestamp created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
