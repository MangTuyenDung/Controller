package vn.mangtuyendung.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import vn.mangtuyendung.persistence.domain.ApplyDomain;
import vn.mangtuyendung.persistence.repositories.ApplyRepository;

/*
 * ApplyService.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Apply service
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create fist time
 */
public class ApplyService {

    private final Logger logger = LoggerFactory.getLogger(ApplyService.class);
    private ApplyRepository repository;

    public ApplyRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(ApplyRepository repository) {
        this.repository = repository;
    }

    /**
     * Save apply domain
     *
     * @param apply Apply domain
     * @return True or False
     */
    public boolean save(ApplyDomain apply) {
        try {
            Assert.notNull(apply, "The job you have applied must not null");
            Assert.isTrue(!StringUtils.isBlank(apply.getUsername()), "The job you have applied then username must not blank");
            Assert.isTrue(!StringUtils.isBlank(apply.getJobId()), "The job you have applied then job id must not blank");
            Assert.isTrue(!StringUtils.isBlank(apply.getTitle()), "The job you have applied then title must not blank");
            logger.debug("Save apply cv member " + apply.getUsername() + " to database");
            repository.save(apply);
            return true;
        } catch (Exception ex) {
            logger.error("Cannot save apply cv to database.", ex);
        }
        return false;
    }

    /**
     * Find the jobs you have applied by username.
     *
     * @param username User ID
     * @return The jobs you have applied.
     */
    public List<ApplyDomain> findByUsername(String username, int start, int limit) {
        try {
            Assert.isTrue(!StringUtils.isBlank(username), "The job you have applied then username must not blank");
            logger.debug("Find the jobs you have applied by username " + username);
            List<ApplyDomain> domains = repository.findByUsername(username, start, limit);
            if (domains == null || domains.isEmpty()) {
                logger.debug("Cant find any job you have applied by username " + username);
                return new ArrayList<ApplyDomain>();
            } else {
                logger.debug("Have " + domains.size() + " jobs you have applied by username " + username);
                return domains;
            }
        } catch (Exception ex) {
            logger.error("Error find the jobs you have applied by username " + username, ex);
        }
        return new ArrayList<ApplyDomain>();
    }

    /**
     * Find limited the jobs you have applied
     *
     * @param start Start row
     * @param limit Limit row
     * @return The jobs you have applied
     */
    public List<ApplyDomain> findBy(int start, int limit) {
        try {
            Assert.isTrue(!(start < 0), "Start must great or equal 0");
            Assert.isTrue(!(limit < 0), "Limit must great than 0");
            if (logger.isDebugEnabled()) {
                logger.debug("Find all the jobs you have applied by limit " + start + " to " + (start + limit));
            }
            return repository.findBy(start, limit);
        } catch (Exception ex) {
            logger.error("Error find the jobs you have applied by limit " + start + " to " + (start + limit), ex);
        }
        return new ArrayList();
    }

    /**
     * Count the jobs you have applied
     *
     * @return Number of the jobs have applied
     */
    public long count() {
        try {
            logger.debug("Count all the jobs you have applied.");
            return repository.count();
        } catch (Exception ex) {
            logger.error("Error count the jobs you have applied.", ex);
        }
        return 0L;
    }

    /**
     * Count the jobs you have applied
     *
     * @return Number of the jobs have applied
     */
    public long countByUsername(String username) {
        try {
            logger.debug("Count all the jobs you have applied.");
            return repository.countByUsername(username);
        } catch (Exception ex) {
            logger.error("Error count the jobs you have applied.", ex);
        }
        return 0L;
    }

    /**
     * Find the jobs you have applied by id
     *
     * @param id ID job
     * @return The apply job
     */
    public ApplyDomain findById(long id) {
        try {
            Assert.isTrue(!(id > 0), "Id the job you have applied must great than 0");
            logger.debug("Find the jobs you have applied by id: " + id);
            return repository.findById(id);
        } catch (Exception ex) {
            logger.error("Error find the jobs you have applied by id: " + id, ex);
        }
        return null;
    }
}