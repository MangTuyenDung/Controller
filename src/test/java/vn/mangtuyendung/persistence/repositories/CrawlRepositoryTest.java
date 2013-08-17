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

import java.sql.Timestamp;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.mangtuyendung.persistence.domain.CrawlDomain;

/**
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-mybatis.xml"})
public class CrawlRepositoryTest {

    @Autowired
    CrawlRepository repository;

    @Test
    public void testConfigure() {
        Assert.assertNotNull(repository);
    }

    @Test
    public void testFindBy() {
        repository.findBy("tuyendungnhanh.vn", 0, 10);
    }

    @Test
    public void testCountBy() {
        repository.countBy("tuyendungnhanh.vn");
    }

    @Test
    public void testSave() {
        CrawlDomain crawl = new CrawlDomain();
        crawl.setId(System.currentTimeMillis());
        crawl.setTitle("Tuyen nhan vien lap trinh Java");
        crawl.setUrl("http://mangtuyendung.vn");
        crawl.setBoost(1.0f);
        crawl.setDomain("mangtuyendung.vn");
        crawl.setCompanyName("Mang tuyen dung");
        crawl.setJobCategory("IT");
        crawl.setJobLocation("Ha noi");
        crawl.setJobOverview("Tuyen nhan vien lap trinh Java");
        crawl.setJobRequirement("Yeu cau phai biet Spring framework.");
        crawl.setExpired(new Timestamp(System.currentTimeMillis()));
//        repository.save(crawl);
    }
}
