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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hadoopvietnam.service.AbstractCachedService;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.mangtuyendung.common.JobCacheKeys;
import vn.mangtuyendung.persistence.domain.JobCategoryDomain;
import vn.mangtuyendung.persistence.repositories.JobCategoryRepository;

/**
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class JobCategoryService extends AbstractCachedService<JobCategoryDomain> {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(JobCategoryService.class);
    /**
     * Job category repository.
     */
    private JobCategoryRepository repository;

    public JobCategoryRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(JobCategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Find all category.
     *
     * @param orderBy
     * @return
     */
    public Collection<JobCategoryDomain> findAll() {
        Collection<JobCategoryDomain> categorys = null;
        try {
            logger.debug("Find all category from cache.");
            categorys = cacheToList(JobCacheKeys.CATEGORY_ALL);
            if (categorys == null) {
                logger.debug("Find all category from db.");
                categorys = repository.findAll();
            }
        } catch (Exception ex) {
            logger.error("Cannot find all category.", ex);
        }
        if (categorys != null && !categorys.isEmpty()) {
            logger.debug("Total get " + categorys.size() + " categorys.");
            addToList(categorys, JobCacheKeys.CATEGORY_ALL);
            return categorys;
        }
        return new ArrayList<JobCategoryDomain>();
    }

    public JobCategoryDomain findByUrl(String url) {
        JobCategoryDomain domain = null;
        try {
            logger.debug("Find category " + url + " from cache.");
            domain = cacheToObject(JobCacheKeys.CATEGORY_ID + url);
            if (domain == null) {
                logger.debug("Find category url " + url + " from db.");
                domain = repository.findByUrl(url);
                if (domain != null) {
                    addToObject(domain, JobCacheKeys.CATEGORY_ID + url);
                }
            }
        } catch (Exception ex) {
            logger.error("Cannot find category " + url, ex);
        }
        return domain;
    }

    @Override
    public Collection<JobCategoryDomain> cacheToList(String key) {
        try {
            Object obj = getCache().get(key);
            if (obj != null) {
                Type list = new TypeToken<Collection<JobCategoryDomain>>() {
                }.getType();
                Gson gson = new Gson();
                Collection<JobCategoryDomain> value = gson.fromJson(obj.toString(), list);
                return value;
            }
        } catch (Exception ex) {
            logger.error("CacheToList: Cannot find key " + key + " in cache.", ex);
        }
        return null;
    }

    @Override
    public JobCategoryDomain cacheToObject(String key) {
        try {
            Object obj = getCache().get(key);
            if (obj != null) {
                Type object = new TypeToken<JobCategoryDomain>() {
                }.getType();
                Gson gson = new Gson();
                JobCategoryDomain value = gson.fromJson(obj.toString(), object);
                return value;
            }
        } catch (Exception ex) {
            logger.error("CacheToObject: Cannot find key " + key + " in cache.", ex);
        }
        return null;
    }
}
