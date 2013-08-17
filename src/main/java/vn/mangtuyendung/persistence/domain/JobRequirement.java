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
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class JobRequirement {

    private long id;
    private byte educationLevel;
    private byte experienceLevel;
    private String skills;
    private String description;
    private Timestamp created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(byte educationLevel) {
        this.educationLevel = educationLevel;
    }

    public byte getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(byte experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
