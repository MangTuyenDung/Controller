package vn.mangtuyendung.persistence.repositories.member;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.mangtuyendung.persistence.domain.member.JobPositionDomain;

public interface JobPositionRepository {

    public JobPositionDomain findById(@Param("id") long paramLong);

    public List<JobPositionDomain> findByProfileId(@Param("profileId") long paramLong);

    public void save(JobPositionDomain paramJobPositionDomain);

    public void update(JobPositionDomain paramJobPositionDomain);

    public void delete(@Param("id") long paramLong1, @Param("profileId") long paramLong2);
}