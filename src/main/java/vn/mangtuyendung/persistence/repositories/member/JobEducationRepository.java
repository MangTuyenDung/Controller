package vn.mangtuyendung.persistence.repositories.member;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.mangtuyendung.persistence.domain.member.JobEducationDomain;

public interface JobEducationRepository {

    public JobEducationDomain findById(@Param("id") long paramLong);

    public List<JobEducationDomain> findByProfileId(@Param("profileId") long paramLong);

    public void save(JobEducationDomain paramJobEducationDomain);

    public void update(JobEducationDomain paramJobEducationDomain);

    public void delete(@Param("id") long paramLong1, @Param("profileId") long paramLong2);
}
