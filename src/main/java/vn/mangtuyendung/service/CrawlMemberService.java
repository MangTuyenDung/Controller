package vn.mangtuyendung.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.persistence.domain.CrawlMemberDomain;
import vn.mangtuyendung.persistence.repositories.CrawlMemberRepository;

public class CrawlMemberService {

    private final Logger logger = LoggerFactory.getLogger(CrawlMemberService.class);
    private CrawlMemberRepository repository;

    public CrawlMemberRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(CrawlMemberRepository repository) {
        this.repository = repository;
    }

    public boolean save(CrawlMemberDomain member) {
        try {
            this.logger.debug("Save crawl member data to database");

            this.repository.save(member);
            return true;
        } catch (Exception ex) {
            this.logger.error("Cannot save crawl member data to database.", ex);
        }
        return false;
    }
}