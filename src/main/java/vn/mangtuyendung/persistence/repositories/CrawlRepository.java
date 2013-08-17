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
import vn.mangtuyendung.persistence.domain.CrawlDomain;

/**
 * Crawl repository.
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public interface CrawlRepository {

    List<CrawlDomain> findBy(@Param(value = "domain") String domain, @Param(value = "start") int start, @Param(value = "end") int end);

    List<CrawlDomain> findByUsername(@Param(value = "domain") String domain, @Param(value = "username") String username, @Param(value = "start") int start, @Param(value = "end") int end);

    CrawlDomain findById(@Param(value = "id") long id);

    long countBy(@Param(value = "domain") String domain);

    long countByUsername(@Param(value = "domain") String domain, @Param(value = "username") String username);

    void save(CrawlDomain crawl);

    void update(CrawlDomain crawl);

    void updateCategory(CrawlDomain crawl);
    
    void updateLocation(CrawlDomain crawl);
    
    void updateDetail(CrawlDomain crawl);

    void updateCompany(CrawlDomain crawl);

    void updateJob(CrawlDomain crawl);

    void updateContact(CrawlDomain crawl);

    void publish(CrawlDomain crawl);

    void vip(CrawlDomain crawl);
}