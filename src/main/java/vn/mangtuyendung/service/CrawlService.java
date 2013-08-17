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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.persistence.domain.CrawlDomain;
import vn.mangtuyendung.persistence.repositories.CrawlRepository;

/**
 * Crawl service.
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class CrawlService {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(CrawlService.class);
    private CrawlRepository repository;

    public CrawlRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(CrawlRepository repository) {
        this.repository = repository;
    }

    public List<CrawlDomain> findBy(String domain, int start, int end) {
        List<CrawlDomain> account = null;
        try {
            logger.debug("Find list jobs from " + start + " to " + end + " in database.");
            account = repository.findBy(domain, start, end);
        } catch (Exception ex) {
            logger.error("Find list items crawl error.", ex);
        }
        if (account != null && !account.isEmpty()) {
            logger.debug("Total get " + account.size() + " jobs.");
            return account;
        }
        return new ArrayList<CrawlDomain>();
    }

    public List<CrawlDomain> findByUsername(String domain, String username, int start, int end) {
        List<CrawlDomain> account = null;
        try {
            logger.debug("Find list jobs by username " + username + " from " + start + " to " + end + " in database.");
            account = repository.findByUsername(domain, username, start, end);
        } catch (Exception ex) {
            logger.error("Find list items jobs by username " + username + " error.", ex);
        }
        if (account != null && !account.isEmpty()) {
            logger.debug("Total get " + account.size() + " jobs.");
            return account;
        }
        return new ArrayList<CrawlDomain>();
    }

    public CrawlDomain findById(long id) {
        try {
            logger.debug("Find by id crawl in database.");
            return repository.findById(id);
        } catch (Exception ex) {
            logger.error("Find by id crawl error.", ex);
        }
        return null;
    }

    public long count(String domain) {
        try {
            logger.debug("Count jobs in database.");
            return repository.countBy(domain);
        } catch (Exception ex) {
            logger.error("Count jobs error.", ex);
        }
        return 0;
    }

    public long countByUsername(String domain, String username) {
        try {
            logger.debug("Count jobs by username " + username + " in database.");
            return repository.countByUsername(domain, username);
        } catch (Exception ex) {
            logger.error("Count jobs error.", ex);
        }
        return 0;
    }

    public boolean save(CrawlDomain crawl) {
        try {
            logger.debug("Save job to database");
            //Save to database
            repository.save(crawl);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot save job to database.", ex);
        }
        return false;
    }

    public boolean update(CrawlDomain crawl) {
        try {
            logger.debug("Update job to database");
            //Save to database
            repository.update(crawl);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot update job to database.", ex);
        }
        return false;
    }

    public boolean updateCategory(CrawlDomain crawl) {
        try {
            logger.debug("Update job category to database");
            //Save to database
            repository.updateCategory(crawl);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot update job category to database.", ex);
        }
        return false;
    }

    public boolean updateLocation(CrawlDomain crawl) {
        try {
            logger.debug("Update job location to database");
            //Save to database
            repository.updateLocation(crawl);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot update job location to database.", ex);
        }
        return false;
    }

    public boolean updateDetail(CrawlDomain crawl) {
        try {
            logger.debug("Update job detail to database");
            //Save to database
            repository.updateDetail(crawl);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot update job detail to database.", ex);
        }
        return false;
    }

    public boolean updateCompany(CrawlDomain crawl) {
        try {
            logger.debug("Update job company to database");
            //Save to database
            repository.updateCompany(crawl);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot update job company to database.", ex);
        }
        return false;
    }

    public boolean updateJob(CrawlDomain crawl) {
        try {
            logger.debug("Update job info to database");
            //Save to database
            repository.updateJob(crawl);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot update job info to database.", ex);
        }
        return false;
    }

    public boolean updateContact(CrawlDomain crawl) {
        try {
            logger.debug("Update job contact to database");
            //Save to database
            repository.updateContact(crawl);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot update job contact to database.", ex);
        }
        return false;
    }

    public boolean publish(CrawlDomain crawl) {
        try {
            logger.debug("Publish job to database");
            //Save to database
            repository.publish(crawl);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot publish job to database.", ex);
        }
        return false;
    }

    public boolean vip(CrawlDomain crawl) {
        try {
            logger.debug("Update job vip to database");
            //Save to database
            repository.vip(crawl);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot update job vip to database.", ex);
        }
        return false;
    }
}
