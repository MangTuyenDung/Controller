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
package vn.mangtuyendung.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.persistence.domain.CategoryMapDomain;
import vn.mangtuyendung.persistence.repositories.CategoryMapRepository;

/**
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class CategoryMapService {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(CategoryMapService.class);
    private CategoryMapRepository repository;

    public CategoryMapRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(CategoryMapRepository repository) {
        this.repository = repository;
    }

    /**
     * Find map by category and domain.
     *
     * @param cat
     * @param domain
     * @return
     */
    public CategoryMapDomain findByCategoryAndDomain(String cat, String domain) {
        try {
            return repository.findByDomainAndCategory(domain, cat);
        } catch (Exception ex) {
            logger.error("Find by category and domain error:" + ex);
        }
        return null;
    }
}
