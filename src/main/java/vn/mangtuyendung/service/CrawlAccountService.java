package vn.mangtuyendung.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.persistence.domain.CrawlAccountDomain;
import vn.mangtuyendung.persistence.repositories.CrawlAccountRepository;

public class CrawlAccountService {

    private final Logger logger = LoggerFactory.getLogger(CrawlAccountService.class);
    private CrawlAccountRepository repository;

    public CrawlAccountRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(CrawlAccountRepository repository) {
        this.repository = repository;
    }

    public boolean save(CrawlAccountDomain member) {
        try {
            this.logger.debug("Save crawl account data to database");

            this.repository.save(member);
            return true;
        } catch (Exception ex) {
            this.logger.error("Cannot save crawl account data to database.", ex);
        }
        return false;
    }
}