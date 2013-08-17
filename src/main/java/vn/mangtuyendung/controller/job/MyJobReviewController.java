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
package vn.mangtuyendung.controller.job;

import com.hadoopvietnam.controller.MyResourceMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.mangtuyendung.controller.SiteAbstractController;
import vn.mangtuyendung.persistence.domain.CrawlDomain;
import vn.mangtuyendung.persistence.domain.JobDomain;
import vn.mangtuyendung.service.CrawlService;

/*
 * MyJobController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * My job create controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  12-Aug-2013     tuanta      Create first time
 */
@RequestMapping("/thanh-vien/tin-tuyen-dung/id-{id}")
@Controller
public class MyJobReviewController extends SiteAbstractController {

    private Logger logger = LoggerFactory.getLogger(MyJobReviewController.class);
    private final String DEFAULT_DOMAIN = "mangtuyendung.vn";
    @Autowired
    private CrawlService crawlService;
    @Autowired
    MyResourceMessage resourceMessage;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public String indexJob(@PathVariable("id") long id, Model model) {
        try {
            CrawlDomain crawlDomain = crawlService.findById(id);
            JobDomain job = convert(crawlDomain);
            model.addAttribute("job", job);
            return "member/job";
        } catch (Exception ex) {
            logger.error("Cannot get job id : " + id, ex);
        }
        return "home/maintenance";
    }

    private JobDomain convert(CrawlDomain source) {
        JobDomain clone = new JobDomain();
        clone.setId(Long.valueOf(source.getId()));
        clone.setBoost(Float.valueOf(source.getBoost()));
        clone.setDomain(source.getDomain());
        clone.setUrl(source.getUrl());
        clone.setExpired(source.getExpired());
        clone.setJobContactEmail(source.getJobContactEmail());

        clone.setTitle(source.getTitle());
        clone.setContent(source.getContent());

        clone.setCompanyName(source.getCompanyName());
        clone.setCompanyOverview(source.getCompanyOverview());
        clone.setCompanyAddress(source.getCompanyAddress());
        clone.setCompanyRange(source.getCompanyRange());

//        clone.setSourceCategory(source.getJobCategory());
        clone.setJobOverview(source.getJobOverview());
        String[] cats = source.getJobCategory().split(",");
        List<String> categories = new ArrayList<String>();
        categories.addAll(Arrays.asList(cats));
        clone.setJobCategory(categories);
//        clone.setJobLocation();
//        clone.setJobMemberLevel(map(source.getJobMemberLevel());
//        clone.setJobTimeWork(source.getJobTimeWork());

//        clone.setJobSalary(source.getJobSalary());
//        clone.setJobAge(source.getJobAge());
//        clone.setJobSex(source.getJobSex());
//        clone.setJobOverview(source.getJobOverview());

//        clone.setJobEducationLevel(source.getJobEducationLevel());
//        clone.setJobExperienceLevel(source.getJobExperienceLevel());
        clone.setJobRequirement(source.getJobRequirement());

//        clone.setJobLanguage(source.getJobLanguage());
        clone.setJobContactDetail(source.getJobContactDetail());
        clone.setJobContactName(source.getJobContactName());
        clone.setJobContactAddress(source.getJobContactAddress());
        clone.setJobContactPerson(source.getJobContactPerson());
        clone.setJobContactPhone(source.getJobContactPhone());
        clone.setJobExpired(source.getJobExpired());
        return clone;
    }
}