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
import org.springframework.dao.DuplicateKeyException;
import vn.mangtuyendung.persistence.domain.CrawlCompanyDomain;
import vn.mangtuyendung.persistence.repositories.CrawlCompanyRepository;

/**
 *
 * @author Tran Anh Tuan
 */
public class CrawlCompanyService {

    private final Logger logger = LoggerFactory.getLogger(CrawlCompanyService.class);
    private CrawlCompanyRepository repository;

    public CrawlCompanyRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(CrawlCompanyRepository repository) {
        this.repository = repository;
    }

    public boolean save(CrawlCompanyDomain member) {
        try {
            this.logger.debug("Save crawl company data to database");

            this.repository.save(member);
            return true;
        } catch (DuplicateKeyException ex) {
            logger.debug("Insert error: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("Cannot save crawl company data to database.", ex);
        }
        return false;
    }
}