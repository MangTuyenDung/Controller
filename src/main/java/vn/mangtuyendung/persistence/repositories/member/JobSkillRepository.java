package vn.mangtuyendung.persistence.repositories.member;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.mangtuyendung.persistence.domain.member.JobSkillDomain;

public interface JobSkillRepository {

    public JobSkillDomain findById(@Param("id") long paramLong);

    public List<JobSkillDomain> findByProfileId(@Param("profileId") long paramLong);

    public void save(JobSkillDomain paramJobSkillDomain);

    public void update(JobSkillDomain paramJobSkillDomain);

    public void delete(@Param("id") long paramLong1, @Param("profileId") long paramLong2);
}