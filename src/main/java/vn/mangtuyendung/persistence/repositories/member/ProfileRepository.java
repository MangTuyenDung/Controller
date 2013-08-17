package vn.mangtuyendung.persistence.repositories.member;

import org.apache.ibatis.annotations.Param;
import vn.mangtuyendung.persistence.domain.member.ProfileDomain;

public interface ProfileRepository {

    public ProfileDomain findById(long paramLong);

    public ProfileDomain findByUserId(@Param("userId") long paramLong);

    public void save(ProfileDomain paramProfileDomain);

    public void update(ProfileDomain paramProfileDomain);

    public void currentPosition(@Param("id") long paramLong, @Param("currentTitle") String paramString1, @Param("currentCompany") String paramString2);
}