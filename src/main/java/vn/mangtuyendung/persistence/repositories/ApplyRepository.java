package vn.mangtuyendung.persistence.repositories;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.mangtuyendung.persistence.domain.ApplyDomain;

public interface ApplyRepository {

    public ApplyDomain findById(long id);

    public List<ApplyDomain> findByUsername(@Param("username") String username, @Param("start") int start, @Param("end") int end);

    public List<ApplyDomain> findBy(@Param("start") int start, @Param("end") int end);

    public long count();
    
    public long countByUsername(@Param("username") String username);

    public void save(ApplyDomain domain);
}