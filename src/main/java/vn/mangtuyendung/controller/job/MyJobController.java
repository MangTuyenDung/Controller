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
import java.util.Collection;
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
import vn.mangtuyendung.form.AdminJobForm;
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
@RequestMapping("/thanh-vien/tin-tuyen-dung")
@Controller
public class MyJobController extends SiteAbstractController {

    private Logger logger = LoggerFactory.getLogger(MyJobController.class);
    private final String DEFAULT_DOMAIN = "mangtuyendung.vn";
    @Autowired
    private CrawlService crawlService;
    @Autowired
    MyResourceMessage resourceMessage;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        try {
            int sizeNo = 10;
            List<AdminJobForm> myJobs = new ArrayList<AdminJobForm>();
            Collection<CrawlDomain> jobs = crawlService.findByUsername(DEFAULT_DOMAIN, getUsername(), 0, 10);
            for (CrawlDomain crawlDomain : jobs) {
                AdminJobForm adminJobForm = new AdminJobForm();
                adminJobForm.setJobId(crawlDomain.getId());
                adminJobForm.setJobName(crawlDomain.getTitle());
                adminJobForm.setJobCompany(crawlDomain.getCompanyName());
                adminJobForm.setStatus(crawlDomain.getStatus());
                adminJobForm.setCreater(crawlDomain.getCreater());
                adminJobForm.setCreated(crawlDomain.getCreated());
                myJobs.add(adminJobForm);
            }
            model.addAttribute("jobs", myJobs);
            long total = crawlService.countByUsername(DEFAULT_DOMAIN, getUsername());
            float nrOfPages = (float) total / sizeNo;

            return "member/myjobs";
        } catch (Exception ex) {
            logger.error("Cannot get jobs: " + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/p{page}-{size}"}, method = RequestMethod.GET)
    public String indexPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size, Model model) {
        try {
            int sizeNo;
            Collection<CrawlDomain> jobs;
            sizeNo = size == null ? 10 : size.intValue();
            page = Integer.valueOf(page == null ? 0 : page.intValue() - 1);
            int start = page.intValue() * sizeNo;
            jobs = crawlService.findByUsername(DEFAULT_DOMAIN, getUsername(), start, start + sizeNo);
            List<AdminJobForm> myJobs = new ArrayList<AdminJobForm>();
            for (CrawlDomain crawlDomain : jobs) {
                AdminJobForm adminJobForm = new AdminJobForm();
                adminJobForm.setJobId(crawlDomain.getId());
                adminJobForm.setJobName(crawlDomain.getTitle());
                adminJobForm.setJobCompany(crawlDomain.getCompanyName());
                adminJobForm.setStatus(crawlDomain.getStatus());
                adminJobForm.setCreater(crawlDomain.getCreater());
                adminJobForm.setCreated(crawlDomain.getCreated());
                myJobs.add(adminJobForm);
            }
            long total = crawlService.countByUsername(DEFAULT_DOMAIN, getUsername());
            float nrOfPages = (float) total / sizeNo;
            model.addAttribute("maxPages", nrOfPages == 0 ? nrOfPages + 1 : nrOfPages);

            model.addAttribute("jobs", myJobs);
            model.addAttribute("pageNo", Integer.valueOf(page.intValue() + 1));
            model.addAttribute("sizeNo", size);
            return "member/myjobs";
        } catch (Exception ex) {
            logger.error("Cannot get jobs: " + ex.toString(), ex);
        }
        return "home/maintenance";
    }
}