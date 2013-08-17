package vn.mangtuyendung.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.persistence.domain.JobSearchDomain;
import vn.mangtuyendung.persistence.repositories.JobSearchRepository;

public class JobSearchService {

    private static Logger logger = LoggerFactory.getLogger(JobSearchService.class);
    private JobSearchRepository repository;

    public JobSearchRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(JobSearchRepository repository) {
        this.repository = repository;
    }

    public boolean save(JobSearchDomain domain) {
        try {
            this.repository.save(domain);
            return true;
        } catch (Exception ex) {
            logger.error("Log query to database error.", ex);
        }
        return false;
    }
}