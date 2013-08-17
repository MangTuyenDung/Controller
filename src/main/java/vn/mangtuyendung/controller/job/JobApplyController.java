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

import com.hadoopvietnam.common.ErrorMessage;
import com.hadoopvietnam.common.ValidationResponse;
import com.hadoopvietnam.controller.MyResourceMessage;
import com.hadoopvietnam.persistence.domain.AccountDomain;
import com.hadoopvietnam.service.AccountService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.mangtuyendung.controller.SiteAbstractController;
import vn.mangtuyendung.form.ApplyForm;
import vn.mangtuyendung.form.validator.ApplyValidator;
import vn.mangtuyendung.persistence.domain.ApplyDomain;
import vn.mangtuyendung.persistence.domain.JobDomain;
import vn.mangtuyendung.service.ApplyService;

/*
 * JobApplyController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Job apply controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */
@RequestMapping("/thanh-vien/nop-ho-so-tuyen-dung")
@Controller
public class JobApplyController extends SiteAbstractController {

    private Logger logger = LoggerFactory.getLogger(JobApplyController.class);
    @Autowired
    private ApplyService applyService;
    @Autowired
    private AccountService accountService;
    @Autowired
    MyResourceMessage resourceMessage;
    @Autowired
    private ApplyValidator validator;

    @RequestMapping(value = {"/{jobId}"}, method = RequestMethod.GET)
    public String index(@PathVariable("jobId") String jobId, Model model) {
        ApplyForm form = new ApplyForm();
        form.setJobId(jobId);
        try {
            QueryResponse response = this.solrService.search("signature:" + jobId, 0, 10);
            SolrDocumentList list = response.getResults();
            if ((list != null) && (!list.isEmpty())) {
                JobDomain job = this.solrService.convertDocument((SolrDocument) list.get(0));
                model.addAttribute("job", job);
                model.addAttribute("username", getUsername());
                model.addAttribute("title", "Nộp hồ sơ ứng tuyển vào vị trí " + job.getTitle());
                model.addAttribute("description", "Nộp hồ sơ ứng tuyển vào vị trí " + job.getTitle() + " trên Mạng Tuyển Dụng.");
                model.addAttribute("applyForm", form);
            }
            return "member/apply";
        } catch (SolrServerException ex) {
            this.logger.error("Cannot query data from Solr Server." + ex.toString());
        } catch (Exception ex) {
            this.logger.error("Cannot get data." + ex.toString());
        }
        return "job/apply";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/json"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public ValidationResponse updateApplyAjaxJson(Model model, @ModelAttribute("applyForm") ApplyForm form, HttpServletRequest request, BindingResult bindingResult) {
        ValidationResponse res = new ValidationResponse();
        this.validator.validate(form, bindingResult);
        if (!bindingResult.hasErrors()) {
            res.setStatus("SUCCESS");
        } else {
            res.setStatus("FAIL");
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
            for (FieldError objectError : allErrors) {
                errorMesages.add(new ErrorMessage(objectError.getField(), this.resourceMessage.getMessage(objectError.getCode(), request)));
            }
            res.setErrorMessageList(errorMesages);
        }
        return res;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String apply(@ModelAttribute("applyForm") ApplyForm form, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) {
        try {
            AccountDomain account = this.accountService.findByUsername(getUsername());
            if (account != null) {
                ApplyDomain domain = new ApplyDomain();
                domain.setJobId(form.getJobId());
                domain.setTitle(form.getTitle());
                domain.setDescription(form.getDescription());
                domain.setUsername(getUsername());
                if (this.applyService.save(domain)) {
                    return "job/successapply";
                }
                model.addAttribute("applyForm", form);
                return "member/apply";
            }
            return "redirect:/";
        } catch (Exception ex) {
            this.logger.error("Update apply resume error.", ex);
        }
        return "home/maintenance";
    }
}