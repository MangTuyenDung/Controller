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
package vn.mangtuyendung.persistence.repositories.company;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.mangtuyendung.persistence.domain.company.CompanyProfileDomain;

/*
 * CompanyProfileRepositoryTest.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Company profile repository testcase
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-mybatis.xml"})
public class CompanyProfileRepositoryTest {

    @Autowired
    CompanyProfileRepository repository;
    long id;
    CompanyProfileDomain domain;

    @Before
    public void init() {
        id = System.currentTimeMillis();
    }

    @Test
    public void testConfigure() {
        Assert.assertNotNull(repository);
    }

    @Test
    public void testFindAll() {
        List<CompanyProfileDomain> domains = repository.findAll();
        Assert.assertNotNull(domains);
    }

    @Test
    public void testFindById() {
        domain = repository.findById(1357830965178l);
        Assert.assertNotNull(domain);
    }

    @Test
    public void testSave() {
        if (domain == null) {
            domain = repository.findById(1357830965178l);
        }
        domain.setId(id);
        domain.setCompanyTaxCode(id + "");
        repository.save(domain);
    }

    @Test
    public void testUpdate() {
        if (domain == null) {
            domain = repository.findById(1357830965178l);
        }
        domain.setCompanyURL("http:/mangtuyendung.vn");
        repository.update(domain);
    }
}
