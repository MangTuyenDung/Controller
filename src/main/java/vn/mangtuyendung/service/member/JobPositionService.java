package vn.mangtuyendung.service.member;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.persistence.domain.member.JobPositionDomain;
import vn.mangtuyendung.persistence.repositories.member.JobPositionRepository;

public class JobPositionService {

    private final Logger logger = LoggerFactory.getLogger(JobPositionService.class);
    private JobPositionRepository repository;

    public JobPositionRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(JobPositionRepository repository) {
        this.repository = repository;
    }

    public JobPositionDomain findById(long id) {
        try {
            return this.repository.findById(id);
        } catch (Exception ex) {
            this.logger.error("Get job position " + id + " from database error.", ex);
        }
        return null;
    }

    public List<JobPositionDomain> findByProfile(long profileId) {
        try {
            return this.repository.findByProfileId(profileId);
        } catch (Exception ex) {
            this.logger.error("Find position by profile id " + profileId, ex);
        }
        return new ArrayList();
    }

    public boolean save(JobPositionDomain domain) {
        try {
            this.repository.save(domain);
            return true;
        } catch (Exception ex) {
            this.logger.error("Save job position to database error.", ex);
        }
        return false;
    }

    public boolean edit(JobPositionDomain domain) {
        try {
            this.repository.update(domain);
            return true;
        } catch (Exception ex) {
            this.logger.error("Save job position to database error.", ex);
        }
        return false;
    }

    public boolean delete(long id, long profileId) {
        try {
            this.repository.delete(id, profileId);
            return true;
        } catch (Exception ex) {
            this.logger.error("Delete job position from database error.", ex);
        }
        return false;
    }
}