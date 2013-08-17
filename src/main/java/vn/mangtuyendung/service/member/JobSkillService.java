package vn.mangtuyendung.service.member;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.persistence.domain.member.JobSkillDomain;
import vn.mangtuyendung.persistence.repositories.member.JobSkillRepository;

public class JobSkillService {

    private final Logger logger = LoggerFactory.getLogger(JobSkillService.class);
    private JobSkillRepository repository;

    public JobSkillRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(JobSkillRepository repository) {
        this.repository = repository;
    }

    public JobSkillDomain findById(long id) {
        try {
            return this.repository.findById(id);
        } catch (Exception ex) {
            this.logger.error("Get job skill " + id + " from database error.", ex);
        }
        return null;
    }

    public List<JobSkillDomain> findByProfile(long profileId) {
        try {
            return this.repository.findByProfileId(profileId);
        } catch (Exception ex) {
            this.logger.error("Find skill by profile id " + profileId, ex);
        }
        return new ArrayList();
    }

    public boolean save(JobSkillDomain domain) {
        try {
            this.repository.save(domain);
            return true;
        } catch (Exception ex) {
            this.logger.error("Save job skill to database error.", ex);
        }
        return false;
    }

    public boolean edit(JobSkillDomain domain) {
        try {
            this.repository.update(domain);
            return true;
        } catch (Exception ex) {
            this.logger.error("Edit job skill to database error.", ex);
        }
        return false;
    }

    public boolean delete(long id, long profileId) {
        try {
            this.repository.delete(id, profileId);
            return true;
        } catch (Exception ex) {
            this.logger.error("Delete job skill from database error.", ex);
        }
        return false;
    }
}