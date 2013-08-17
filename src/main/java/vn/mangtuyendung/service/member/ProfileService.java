package vn.mangtuyendung.service.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.persistence.domain.member.ProfileDomain;
import vn.mangtuyendung.persistence.repositories.member.ProfileRepository;

public class ProfileService {

    private final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    private ProfileRepository repository;

    public ProfileRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(ProfileRepository repository) {
        this.repository = repository;
    }

    public ProfileDomain findById(long userId) {
        ProfileDomain domain = null;
        try {
            domain = this.repository.findById(userId);
        } catch (Exception ex) {
            this.logger.error("Find profile by user id " + userId + " error", ex);
        }
        return domain;
    }

    public ProfileDomain findByUserId(long userId) {
        ProfileDomain domain = null;
        try {
            domain = this.repository.findByUserId(userId);
        } catch (Exception ex) {
            this.logger.error("Find profile by user id " + userId + " error", ex);
        }
        return domain;
    }

    public boolean save(ProfileDomain domain) {
        try {
            this.repository.save(domain);
            return true;
        } catch (Exception ex) {
            this.logger.error("Save profile information error", ex);
        }
        return false;
    }

    public boolean edit(ProfileDomain profileDomain) {
        try {
            this.repository.update(profileDomain);
            return true;
        } catch (Exception ex) {
            this.logger.error("Update profile information error", ex);
        }
        return false;
    }

    public boolean update(long id, String currentTitle, String currentCompany) {
        try {
            this.repository.currentPosition(id, currentTitle, currentCompany);
            return true;
        } catch (Exception ex) {
            this.logger.error("Update current position error", ex);
        }
        return false;
    }
}