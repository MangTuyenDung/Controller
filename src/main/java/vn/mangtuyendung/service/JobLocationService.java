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
import vn.mangtuyendung.persistence.domain.JobLocationDomain;
import vn.mangtuyendung.persistence.repositories.JobLocationRepository;

/**
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class JobLocationService extends AbstractCachedService<JobLocationDomain> {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(JobLocationService.class);
    /**
     * Job location repository.
     */
    private JobLocationRepository repository;

    public JobLocationRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(JobLocationRepository repository) {
        this.repository = repository;
    }

    /**
     * Find all location.
     *
     * @param orderBy
     * @return
     */
    public Collection<JobLocationDomain> findAll() {
        Collection<JobLocationDomain> locations = null;
        try {
            logger.debug("Find all location from cache.");
            locations = cacheToList(JobCacheKeys.LOCATION_ALL);
            if (locations == null) {
                logger.debug("Find all location from db.");
                locations = repository.findAll();
            }
        } catch (Exception ex) {
            logger.error("Cannot find all location.", ex);
        }
        if (locations != null && !locations.isEmpty()) {
            logger.debug("Total get " + locations.size() + " locations.");
            addToList(locations, JobCacheKeys.LOCATION_ALL);
            return locations;
        }
        return new ArrayList<JobLocationDomain>();
    }

    public JobLocationDomain findByUrl(String url) {
        JobLocationDomain domain = null;
        try {
            logger.debug("Find location " + url + " from cache.");
            domain = cacheToObject(JobCacheKeys.LOCATION_ID + url);
            if (domain == null) {
                logger.debug("Find location url " + url + " from db.");
                domain = repository.findByUrl(url);
                if (domain != null) {
                    addToObject(domain, JobCacheKeys.LOCATION_ID + url);
                }
            }
        } catch (Exception ex) {
            logger.error("Cannot find location.", ex);
        }
        return domain;
    }

    @Override
    public Collection<JobLocationDomain> cacheToList(String key) {
        try {
            Object obj = getCache().get(key);
            if (obj != null) {
                Type list = new TypeToken<Collection<JobLocationDomain>>() {
                }.getType();
                Gson gson = new Gson();
                Collection<JobLocationDomain> value = gson.fromJson(obj.toString(), list);
                return value;
            }
        } catch (Exception ex) {
            logger.error("CacheToList: Cannot find key " + key + " in cache.", ex);
        }
        return null;
    }

    @Override
    public JobLocationDomain cacheToObject(String key) {
        try {
            Object obj = getCache().get(key);
            if (obj != null) {
                Type object = new TypeToken<JobLocationDomain>() {
                }.getType();
                Gson gson = new Gson();
                JobLocationDomain value = gson.fromJson(obj.toString(), object);
                return value;
            }
        } catch (Exception ex) {
            logger.error("CacheToObject: Cannot find key " + key + " in cache.", ex);
        }
        return null;
    }
}
