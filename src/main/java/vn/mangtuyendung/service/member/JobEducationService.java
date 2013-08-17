package vn.mangtuyendung.service.member;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.persistence.domain.member.JobEducationDomain;
import vn.mangtuyendung.persistence.repositories.member.JobEducationRepository;

public class JobEducationService {

    private final Logger logger = LoggerFactory.getLogger(JobEducationService.class);
    private JobEducationRepository repository;

    public JobEducationRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(JobEducationRepository repository) {
        this.repository = repository;
    }

    public JobEducationDomain findById(long id) {
        try {
            return this.repository.findById(id);
        } catch (Exception ex) {
            this.logger.error("Get job education " + id + " from database error.", ex);
        }
        return null;
    }

    public List<JobEducationDomain> findByProfile(long profileId) {
        try {
            return this.repository.findByProfileId(profileId);
        } catch (Exception ex) {
            this.logger.error("Find education by profile id " + profileId, ex);
        }
        return new ArrayList();
    }

    public boolean save(JobEducationDomain domain) {
        try {
            this.repository.save(domain);
            return true;
        } catch (Exception ex) {
            this.logger.error("Save job education to database error.", ex);
        }
        return false;
    }

    public boolean edit(JobEducationDomain domain) {
        try {
            this.repository.update(domain);
            return true;
        } catch (Exception ex) {
            this.logger.error("Edit job education to database error.", ex);
        }
        return false;
    }

    public boolean delete(long id, long profileId) {
        try {
            this.repository.delete(id, profileId);
            return true;
        } catch (Exception ex) {
            this.logger.error("Delete job education from database error.", ex);
        }
        return false;
    }
}