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
import java.util.List;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
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
import vn.mangtuyendung.form.MyApplyForm;
import vn.mangtuyendung.persistence.domain.ApplyDomain;
import vn.mangtuyendung.persistence.domain.JobDomain;
import vn.mangtuyendung.service.ApplyService;

/*
 * MyApplyController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * My job applied controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 *  25-jan-2013     tuanta      Refactor code
 */
@RequestMapping("/thanh-vien/ho-so-ung-tuyen")
@Controller
public class MyApplyController extends SiteAbstractController {

    private Logger logger = LoggerFactory.getLogger(MyApplyController.class);
    @Autowired
    private ApplyService applyService;
    @Autowired
    MyResourceMessage resourceMessage;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        try {
            List<ApplyDomain> applies = applyService.findByUsername(getUsername(), 0, 10);
            List<MyApplyForm> myApplies = new ArrayList<MyApplyForm>();
            for (ApplyDomain applyDomain : applies) {
                QueryResponse response = solrService.search("signature:" + applyDomain.getJobId(), 0, 10);
                SolrDocumentList list = response.getResults();
                if ((list != null) && (!list.isEmpty())) {
                    JobDomain job = solrService.convertDocument((SolrDocument) list.get(0));
                    MyApplyForm myApply = new MyApplyForm();
                    myApply.setId(applyDomain.getId());
                    myApply.setJobId(applyDomain.getJobId());
                    myApply.setApplyCreated(applyDomain.getCreated());
                    myApply.setJobName(job.getTitle());
                    myApply.setJobCompany(job.getCompanyName());
                    myApply.setJobExpired(job.getExpired());
                    myApplies.add(myApply);
                }
            }
            model.addAttribute("myApplies", myApplies);
            return "member/myapplies";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString());
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString());
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/p{page}-{size}"}, method = RequestMethod.GET)
    public String indexPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size, Model model) {
        try {
            int sizeNo;
            List<ApplyDomain> applies;
            sizeNo = size == null ? 10 : size.intValue();
            page = Integer.valueOf(page == null ? 0 : page.intValue() - 1);
            int start = page.intValue() * sizeNo;
            applies = applyService.findByUsername(getUsername(), start, sizeNo);

            List<MyApplyForm> myApplies = new ArrayList<MyApplyForm>();
            for (ApplyDomain applyDomain : applies) {
                QueryResponse response = solrService.search("signature:" + applyDomain.getJobId(), 0, 10);
                SolrDocumentList list = response.getResults();
                if ((list != null) && (!list.isEmpty())) {
                    JobDomain job = solrService.convertDocument((SolrDocument) list.get(0));
                    MyApplyForm myApply = new MyApplyForm();
                    myApply.setId(applyDomain.getId());
                    myApply.setJobId(applyDomain.getJobId());
                    myApply.setApplyCreated(applyDomain.getCreated());
                    myApply.setJobName(job.getTitle());
                    myApply.setJobCompany(job.getCompanyName());
                    myApply.setJobExpired(job.getExpired());
                    myApplies.add(myApply);
                }
            }
            long total = applyService.countByUsername(getUsername());
            float nrOfPages = (float) total / sizeNo;
            model.addAttribute("maxPages", nrOfPages == 0 ? nrOfPages + 1 : nrOfPages);

            model.addAttribute("myApplies", myApplies);
            model.addAttribute("pageNo", Integer.valueOf(page.intValue() + 1));
            model.addAttribute("sizeNo", size);
            return "member/myapplies";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString());
        } catch (Exception ex) {
            logger.error("Cannot get applied: " + ex.toString(), ex);
        }
        return "home/maintenance";
    }
}