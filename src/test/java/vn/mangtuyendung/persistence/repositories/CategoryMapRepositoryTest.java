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
import vn.mangtuyendung.persistence.domain.CategoryMapDomain;

/**
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-mybatis.xml"})
public class CategoryMapRepositoryTest {

    @Autowired
    CategoryMapRepository repository;

    @Test
    public void testConfigure() {
        Assert.assertNotNull(repository);
    }

    @Test
    public void testFindBy() {
        Assert.assertNotNull(repository.findByDomain("vietnamworks.com", "CAT", 0, 10));
    }

    @Test
    public void testFindAll() {
        long count = repository.countBy(null);
        int pageSize = 50;
        int loop = (int) count / pageSize;

        for (int i = 0; i < loop; i++) {
            List<CategoryMapDomain> cats = repository.findAll("CAT", i * pageSize, (i + 1) * pageSize);
            for (CategoryMapDomain categoryMapDomain : cats) {
                String tmp = categoryMapDomain.getCat();
                tmp = tmp.replaceAll("- ", "-");
                categoryMapDomain.setCat(tmp);
                repository.update(categoryMapDomain);
            }
        }
    }

    @Test
    public void testCountBy() {
        CategoryMapDomain domain = new CategoryMapDomain();
        domain.setDomain("vietnamworks.com");
        System.out.println("Count by vietnamworks.com: " + repository.countBy(domain));
    }

    @Test
    public void testCountByNull() {
        CategoryMapDomain domain = new CategoryMapDomain();
        System.out.println("Count all: " + repository.countBy(domain));
    }
//    @Test
//    public void testSetParent() {
//        Assert.assertNotNull(repository);
//    }
//
//    @Test
//    public void testSetOrder() {
//        Assert.assertNotNull(repository);
//    }
}
