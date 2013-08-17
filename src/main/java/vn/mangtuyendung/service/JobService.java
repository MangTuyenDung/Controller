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
package vn.mangtuyendung.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.persistence.domain.JobDomain;
import vn.mangtuyendung.persistence.repositories.JobRepository;

/**
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class JobService {

    private static Logger logger = LoggerFactory.getLogger(JobService.class);
    /**
     * Job repository.
     */
    private JobRepository repository;

    public JobRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(JobRepository repository) {
        this.repository = repository;
    }

    public boolean save(JobDomain domain) {
        try {
            repository.save(domain);
            return true;
        } catch (Exception ex) {
            logger.error("Save job to database error.", ex);
        }
        return false;
    }
}
