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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.mangtuyendung.persistence.domain.JobCategoryDomain;

/**
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-mybatis.xml"})
public class CategoryRepositoryTest {

    @Autowired
    JobCategoryRepository repository;

    @Test
    public void testConfigure() {
        Assert.assertNotNull(repository);
    }

    @Test
    public void testUpdateUrl() {
        List<JobCategoryDomain> domains = repository.findAll();
        for (JobCategoryDomain category : domains) {
//            category.setUrl(URLConvert.convert(category.getName()));
//            repository.update(category);
            System.out.println(category.getName());
        }
    }
//    @Test
//    public void testEnable() {
//        List<JobCategoryDomain> domains = repository.findAll("NAME");
//        for (JobCategoryDomain category : domains) {
//            if (category.getId() == 27
//                    || category.getId() == 36
//                    || category.getId() == 37
//                    || category.getId() == 38
//                    || category.getId() == 51
//                    || category.getId() == 59
//                    || category.getId() == 67
//                    || category.getId() == 43) {
//                category.setEnabled(false);
//                repository.update(category);
//            }
//        }
//    }
}
