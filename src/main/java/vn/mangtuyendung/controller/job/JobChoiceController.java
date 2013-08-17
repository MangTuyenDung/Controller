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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.mangtuyendung.controller.SiteAbstractController;
import vn.mangtuyendung.form.job.JobCategoryForm;
import vn.mangtuyendung.form.job.JobCompanyForm;
import vn.mangtuyendung.form.job.JobContactForm;
import vn.mangtuyendung.form.job.JobCreateForm;
import vn.mangtuyendung.form.job.JobDetailForm;
import vn.mangtuyendung.form.job.JobLocationForm;
import vn.mangtuyendung.form.validator.JobCategoryValidator;
import vn.mangtuyendung.form.validator.JobCompanyValidator;
import vn.mangtuyendung.form.validator.JobContactValidator;
import vn.mangtuyendung.form.validator.JobCreateValidator;
import vn.mangtuyendung.form.validator.JobDetailValidator;
import vn.mangtuyendung.form.validator.JobLocationValidator;
import vn.mangtuyendung.persistence.domain.CrawlDomain;
import vn.mangtuyendung.service.CrawlService;
import vn.mangtuyendung.service.JobService;

/*
 * JobChoiseController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Choise type create of job
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */
@RequestMapping("/dang-tin-tuyen-dung")
@Controller
public class JobChoiceController extends SiteAbstractController {

    private final Logger logger = LoggerFactory.getLogger(JobChoiceController.class);
    @Autowired
    JobCreateValidator validator;
    @Autowired
    JobCategoryValidator categoryValidator;
    @Autowired
    JobLocationValidator locationValidator;
    @Autowired
    JobCompanyValidator companyValidator;
    @Autowired
    JobDetailValidator detailValidator;
    @Autowired
    JobContactValidator contactValidator;
    @Autowired
    MyResourceMessage resourceMessage;
    @Autowired
    JobService jobService;
    @Autowired
    CrawlService crawlService;
    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("jobCreateForm", new JobCreateForm());
        return "job/choice";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/buoc-1"}, method = RequestMethod.GET)
    public String createJobView(Model model) {
        model.addAttribute("jobCreateForm", new JobCreateForm());
        return "job/step1";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/buoc-1"}, method = RequestMethod.POST)
    public String createJob(@ModelAttribute("jobCreateForm") JobCreateForm form, HttpServletRequest request, BindingResult bindingResult, Model model) {
        try {
            this.validator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("jobCreateForm", form);
                return "job/step1";
            }
            AccountDomain account = this.accountService.findByUsername(getUsername());
            if (account != null) {
                long jid = System.currentTimeMillis();
                CrawlDomain domain = new CrawlDomain();
                domain.setId(jid);
                domain.setJobOverview(form.getJobOverview());
                domain.setJobRequirement(form.getJobRequirement());
                domain.setTitle(form.getJobTitle());
                domain.setJobExperienceLevel(form.getJobExperienceLevel());
                domain.setJobEducationLevel(form.getJobEducationLevel());
                domain.setJobSalary(form.getJobSalary());
                domain.setJobMemberLevel(form.getJobMemberLevel());
                domain.setJobTimeWork(form.getJobTimeWork());
                domain.setCreater(account.getAccountName());
                if (crawlService.save(domain)) {
                    JobCategoryForm categoryForm = new JobCategoryForm();
                    categoryForm.setId(jid);
                    model.addAttribute("jobCategoryForm", categoryForm);
                    setCategory(getQueryResponse(), model);
                    return "job/step2";
                } else {
                    model.addAttribute("jobCreateForm", form);
                    return "job/step1";
                }
            }
        } catch (Exception ex) {
            this.logger.error("Insert new job error.", ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/buoc-1/json"}, method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse createJobAjaxJson(Model model, @ModelAttribute("jobCreateForm") JobCreateForm form, HttpServletRequest request, BindingResult bindingResult) {
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
    @RequestMapping(value = {"/buoc-2"}, method = RequestMethod.POST)
    public String choiceCategory(@ModelAttribute("jobCategoryForm") JobCategoryForm form, HttpServletRequest request, BindingResult bindingResult, Model model) {
        try {
            categoryValidator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                setCategory(getQueryResponse(), model);
                model.addAttribute("jobCategoryForm", form);
                return "job/step2";
            }
            AccountDomain account = this.accountService.findByUsername(getUsername());
            if (account != null) {
                CrawlDomain domain = new CrawlDomain();
                domain.setId(form.getId());
                String cat = Arrays.toString(form.getJobCategories());
                cat = cat.replaceAll("\\[", "");
                cat = cat.replaceAll("]", "");
                cat = cat.replaceAll(", ", ",");
                cat = cat.replaceAll(" ,", ",");
                domain.setJobCategory(cat);
                domain.setUpdated(new Timestamp(System.currentTimeMillis()));
                domain.setUpdater(account.getAccountName());
                if (crawlService.updateCategory(domain)) {
                    JobLocationForm locationForm = new JobLocationForm();
                    locationForm.setJobId(form.getId());
                    model.addAttribute("jobLocationForm", locationForm);
                    setLocation(getQueryResponse(), model);
                    return "job/step2-1";
                } else {
                    model.addAttribute("jobCategoryForm", form);
                    return "job/step2";
                }
            }
        } catch (Exception ex) {
            logger.error("Update category job error.", ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/buoc-2/json"}, method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse choiceCategoryAjaxJson(Model model, @ModelAttribute("jobCategoryForm") JobCategoryForm form, HttpServletRequest request, BindingResult bindingResult) {
        ValidationResponse res = new ValidationResponse();
        categoryValidator.validate(form, bindingResult);
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
    @RequestMapping(value = {"/buoc-2-1"}, method = RequestMethod.POST)
    public String choiceLocation(@ModelAttribute("jobLocationForm") JobLocationForm form, HttpServletRequest request, BindingResult bindingResult, Model model) {
        try {
            locationValidator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                setLocation(getQueryResponse(), model);
                model.addAttribute("jobLocationForm", form);
                return "job/step2-1";
            }
            AccountDomain account = this.accountService.findByUsername(getUsername());
            if (account != null) {
                CrawlDomain domain = new CrawlDomain();
                domain.setId(form.getJobId());
                String location = Arrays.toString(form.getJobLocations());
                location = location.replaceAll("\\[", "");
                location = location.replaceAll("]", "");
                location = location.replaceAll(", ", ",");
                location = location.replaceAll(" ,", ",");
                domain.setJobLocation(location);
                domain.setUpdated(new Timestamp(System.currentTimeMillis()));
                domain.setUpdater(account.getAccountName());
                if (crawlService.updateLocation(domain)) {
                    JobCompanyForm companyForm = new JobCompanyForm();
                    companyForm.setJobId(form.getJobId());
                    model.addAttribute("jobCompanyForm", companyForm);
                    return "job/step3";
                } else {
                    setLocation(getQueryResponse(), model);
                    model.addAttribute("jobLocationForm", form);
                    return "job/step2-1";
                }
            }
        } catch (Exception ex) {
            logger.error("Update location job error.", ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/buoc-2-1/json"}, method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse choiceLocationAjaxJson(Model model, @ModelAttribute("jobLocationForm") JobLocationForm form, HttpServletRequest request, BindingResult bindingResult) {
        ValidationResponse res = new ValidationResponse();
        locationValidator.validate(form, bindingResult);
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
    @RequestMapping(value = {"/buoc-3"}, method = RequestMethod.POST)
    public String companyInformation(@ModelAttribute("jobCompanyForm") JobCompanyForm form, HttpServletRequest request, BindingResult bindingResult, Model model) {
        try {
            companyValidator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("jobCompanyForm", form);
                return "job/step3";
            }
            AccountDomain account = this.accountService.findByUsername(getUsername());
            if (account != null) {
                CrawlDomain domain = new CrawlDomain();
                domain.setId(form.getJobId());
                domain.setCompanyName(form.getCompanyName());
                domain.setCompanyAddress(form.getCompanyAddress());
                domain.setCompanyOverview(form.getCompanyOverview());
                domain.setCompanyRange(form.getCompanyRange());
                domain.setUpdated(new Timestamp(System.currentTimeMillis()));
                domain.setUpdater(account.getAccountName());
                if (crawlService.updateCompany(domain)) {
                    JobContactForm contactForm = new JobContactForm();
                    contactForm.setJobId(form.getJobId());
                    model.addAttribute("jobContactForm", contactForm);
                    return "job/step4";
                } else {
                    model.addAttribute("jobCompanyForm", form);
                    return "job/step3";
                }
            }
        } catch (Exception ex) {
            this.logger.error("Update company job error.", ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/buoc-3/json"}, method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse companyInformationAjaxJson(Model model, @ModelAttribute("jobCompanyForm") JobCompanyForm form, HttpServletRequest request, BindingResult bindingResult) {
        ValidationResponse res = new ValidationResponse();
        companyValidator.validate(form, bindingResult);
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
    @RequestMapping(value = {"/buoc-4"}, method = RequestMethod.POST)
    public String contactInformation(@ModelAttribute("jobContactForm") JobContactForm form, HttpServletRequest request, BindingResult bindingResult, Model model) {
        try {
            contactValidator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("jobCategoryForm", form);
                return "job/step4";
            }
            AccountDomain account = this.accountService.findByUsername(getUsername());
            if (account != null) {
                CrawlDomain domain = new CrawlDomain();
                domain.setId(form.getJobId());
                domain.setJobContactAddress(form.getJobContactAddress());
                domain.setJobContactName(form.getJobContactName());
                domain.setJobContactPerson(form.getJobContactPerson());
                domain.setJobContactPhone(form.getJobContactPhone());
                domain.setJobContactEmail(form.getJobContactEmail());
                domain.setUpdated(new Timestamp(System.currentTimeMillis()));
                domain.setUpdater(account.getAccountName());
                if (crawlService.updateContact(domain)) {
                    return "job/finish";
                } else {
                    model.addAttribute("jobContactForm", form);
                    return "job/step4";
                }
            }
        } catch (Exception ex) {
            this.logger.error("Update contact job error.", ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/buoc-4/json"}, method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse contactInformationAjaxJson(Model model, @ModelAttribute("jobContactForm") JobContactForm form, HttpServletRequest request, BindingResult bindingResult) {
        ValidationResponse res = new ValidationResponse();
        contactValidator.validate(form, bindingResult);
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
}
