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

import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.mangtuyendung.persistence.domain.company.CompanyMemberDomain;
import vn.mangtuyendung.persistence.domain.company.CompanyProfileDomain;

/*
 * CompanyMemberRepository.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Company member repository testcase
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-mybatis.xml"})
public class CompanyMemberRepositoryTest {

    @Autowired
    CompanyMemberRepository repository;
    @Autowired
    CompanyProfileRepository companyProfileRepository;
    long id;
    CompanyMemberDomain domain;
    
    @Test
    public void testSetup(){
        Assert.assertNotNull(repository);
    }
    
    @Test
    public void testFindAll() {
        List<CompanyMemberDomain> domains = repository.findAll();
        Assert.assertNotNull(domains);
    }
    
    @Test
    public void testFindById() {
        domain = repository.findById(0l);
        Assert.assertNull(domain);
    }
    
    @Test
    public void testFindByMemberId() {
        List<CompanyMemberDomain> domains = repository.findByMemberId(0l);
        Assert.assertTrue(domains.isEmpty());
    }
    
    @Test
    public void testSave() {
        if (domain == null) {
            domain = new CompanyMemberDomain();
            CompanyProfileDomain companyProfileDomain = companyProfileRepository.findById(1357830965178l);
            if (companyProfileDomain != null){
                domain.setId(System.currentTimeMillis());
                domain.setCompanyAddress(companyProfileDomain.getCompanyAddress());
                domain.setCompanyId(companyProfileDomain.getId());
                domain.setCompanyName(companyProfileDomain.getCompanyName());
                domain.setCompanyNameEnglish(companyProfileDomain.getCompanyNameEnglish());
                domain.setCreater(companyProfileDomain.getCreater());
                domain.setMemberId(System.currentTimeMillis());
                domain.setVerify(false);
                domain.setCreated(new Date());
            }
        }
        repository.save(domain);
    }
    
    @Test
    public void testUpdate() {
        domain = repository.findById(1359564030587l);
        if (domain != null) {
            domain.setUpdater(System.currentTimeMillis());
            domain.setUpdated(new Date());
            domain.setVerify(true);
            repository.update(domain);
        }
    }
}
