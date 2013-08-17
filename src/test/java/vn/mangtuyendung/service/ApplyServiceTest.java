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

import java.sql.SQLException;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import vn.mangtuyendung.persistence.domain.ApplyDomain;
import vn.mangtuyendung.persistence.repositories.ApplyRepository;

/*
 * ApplyServiceTest.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Test case for apply service
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create fist time
 */
public class ApplyServiceTest {

    @Mock
    ApplyRepository repository;
    ApplyDomain domain;
    ApplyService service;

    @Before
    public void init() {
        domain = Mockito.mock(ApplyDomain.class);
        Mockito.when(domain.getId()).thenReturn(1l);
        Mockito.when(domain.getTitle()).thenReturn("Title 1");
        Mockito.when(domain.getJobId()).thenReturn("Job 1");
        Mockito.when(domain.getUsername()).thenReturn("Username 1");

        repository = Mockito.mock(ApplyRepository.class);
        service = new ApplyService();
        service.setRepository(repository);
    }

    @Test
    public void testFindById() {
        Mockito.when(repository.findById(1)).thenReturn(domain);
        ApplyDomain domain_ = service.findById(1);
        Mockito.verify(repository).findById(1);
        Assert.assertEquals(domain_.getId(), 1);
        Assert.assertEquals(domain_.getTitle(), "Title 1");
        Assert.assertEquals(domain_.getJobId(), "Job 1");
        Assert.assertEquals(domain_.getUsername(), "Username 1");
    }

    @Test
    public void testFindByWrongId() {
        ApplyDomain domain_ = service.findById(-1);
        Assert.assertNull(domain_);
    }

    @Test
    public void testFindByIdNotFound() {
        Mockito.when(repository.findById(10)).thenReturn(null);
        ApplyDomain domain_ = service.findById(10);
        Mockito.verify(repository).findById(10);
        Assert.assertNull(domain_);
    }

    @Test
    public void testFindByUsernameNotFound() {
        Mockito.when(repository.findByUsername("tuanta", 0, 10)).thenReturn(null);
        List<ApplyDomain> domains = service.findByUsername("tuanta", 0, 10);
        Mockito.verify(repository).findByUsername("tuanta", 0, 10);
        Assert.assertTrue(domains.isEmpty());
    }

    @Test
    public void testSaveSuccess() {
        Mockito.doNothing().when(repository).save(domain);//.thenReturn(null);
        boolean action = service.save(domain);
        Mockito.verify(repository).save(domain);
        Assert.assertTrue(action);
    }

    @Test
    public void testSaveWrongParam() {
        Mockito.doThrow(SQLException.class).when(repository).save(domain);//.thenReturn(null);
        boolean action = service.save(domain);
        Mockito.verify(repository).save(domain);
        Assert.assertFalse(action);
    }

    @Test
    public void testSaveNullDomain() {
        boolean action = service.save(null);
        Assert.assertFalse(action);
    }

    @Test
    public void testSaveDomainWithoutParam() {
        ApplyDomain domain_ = new ApplyDomain();
        boolean action = service.save(domain_);
        Assert.assertFalse(action);
    }
}