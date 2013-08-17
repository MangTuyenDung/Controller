package vn.mangtuyendung.persistence.repositories;

import vn.mangtuyendung.persistence.domain.JobSearchDomain;

public interface JobSearchRepository {

    public void save(JobSearchDomain paramJobSearchDomain);
}