/*
 * Copyright 2012 Hadoop Vietnam <admin@hadoopvietnam.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vn.mangtuyendung.persistence.repositories;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.mangtuyendung.persistence.domain.CategoryMapDomain;

/**
 * Job category repository.
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public interface CategoryMapRepository {

    List<CategoryMapDomain> findAll(@Param(value = "orderBy") String orderBy, @Param(value = "start") int start, @Param(value = "end") int end);

    List<CategoryMapDomain> findByDomain(@Param(value = "domain") String domain, @Param(value = "orderBy") String orderBy, @Param(value = "start") int start, @Param(value = "end") int end);

    CategoryMapDomain findByDomainAndCategory(@Param(value = "domain") String domain, @Param(value = "cat") String cat);

    CategoryMapDomain findById(@Param(value = "id") long id);

    long countBy(CategoryMapDomain domain);

    void save(CategoryMapDomain domain);
    
    void update(CategoryMapDomain domain);

    void setParent(long id, long parentId);

    void setEnable(long id, boolean enabled);

    void setOrder(long id, int orderId);
}