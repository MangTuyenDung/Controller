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
package vn.mangtuyendung.controller.admin;

import com.hadoopvietnam.common.MyCommon;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.mangtuyendung.controller.SiteAbstractController;
import vn.mangtuyendung.form.job.JobPublishForm;
import vn.mangtuyendung.persistence.domain.CrawlDomain;
import vn.mangtuyendung.persistence.domain.JobDomain;
import vn.mangtuyendung.service.CrawlService;
import vn.mangtuyendung.solr.JobDocument;

/**
 *
 * @author Letfy Team <letfy@hadoopvietnam.com>
 */
@RequestMapping("/admin/jobs/publish")
@Controller
public class AdminJobPublishController extends SiteAbstractController {

    private final Logger logger = LoggerFactory.getLogger(AdminJobPublishController.class);
    @Autowired
    CrawlService service;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public String indexPage(@ModelAttribute("jobPublishForm") JobPublishForm form, HttpServletRequest request, BindingResult bindingResult, Model model) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Publish job id " + form.getId());
            }
            CrawlDomain domain = new CrawlDomain();
            long time = System.currentTimeMillis();
            Timestamp t = new Timestamp(time);
            domain.setId(form.getId());
            domain.setPublished(t);
            domain.setUpdated(t);
            domain.setExpired(new Timestamp(time + 15 * 24 * 3600));
            domain.setUpdater(getUsername());
            service.publish(domain);
            domain = service.findById(form.getId());
            domain.setJobExpired(MyCommon.formatDate("dd-MM-yyyy", domain.getExpired()));
            JobDomain jobClone = clone(domain);
            JobDocument doc = new JobDocument();
            doc.convertCrawlDomain(jobClone);
            postDocument(doc);
            return "admin/jobpublish";
        } catch (Exception ex) {
            logger.error("Cannot get jobs: " + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    private void postDocument(JobDocument doc)
            throws SolrServerException, IOException {
        logger.trace("Post document to Solr Server.");
        SolrServer source = new HttpSolrServer("http://localhost:8983/solr");
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.add(doc);
        updateRequest.process(source);
        source.commit();
    }

    private JobDomain clone(CrawlDomain source) {
        JobDomain clone = new JobDomain();
        clone.setId(Long.valueOf(source.getId()));
        clone.setBoost(Float.valueOf(source.getBoost()));
        clone.setDomain(source.getDomain());
        clone.setUrl(source.getUrl());
        clone.setExpired(source.getExpired());
        clone.setJobContactEmail(source.getJobContactEmail());

        clone.setTitle((source.getTitle()));
        clone.setContent((source.getContent()));

        clone.setCompanyName((source.getCompanyName()));
        clone.setCompanyOverview((source.getCompanyOverview()));
        clone.setCompanyAddress((source.getCompanyAddress()));
        clone.setCompanyRange((source.getCompanyRange()));

        clone.setSourceCategory(source.getJobCategory());
        List<String> categories = new ArrayList<String>();
        String[] cats = source.getJobCategory().split(",");
        for (String cat : cats) {
            categories.add(cat.trim());
        }
        clone.setJobCategory(categories);
        List<String> locations = new ArrayList<String>();
        String[] locals = source.getJobLocation().split(",");
        for (String local : locals) {
            locations.add(local.trim());
        }

        List<String> members = new ArrayList<String>();
        members.add(source.getJobMemberLevel());
        clone.setJobLocation(locations);
        clone.setJobMemberLevel(members);
        clone.setJobTimeWork(source.getJobTimeWork());
        clone.setJobSalary((source.getJobSalary()));
        clone.setJobAge((source.getJobAge()));
        clone.setJobSex((source.getJobSex()));
        clone.setJobOverview((source.getJobOverview()));

        clone.setJobEducationLevel(source.getJobEducationLevel());
        clone.setJobExperienceLevel((source.getJobExperienceLevel()));
        clone.setJobRequirement((source.getJobRequirement()));

        clone.setJobLanguage((source.getJobLanguage()));
        clone.setJobContactDetail((source.getJobContactDetail()));
        clone.setJobContactName((source.getJobContactName()));
        clone.setJobContactAddress((source.getJobContactAddress()));
        clone.setJobContactPerson((source.getJobContactPerson()));
        clone.setJobContactPhone((source.getJobContactPhone()));
        clone.setJobExpired(source.getJobExpired());
        return clone;
    }
}
