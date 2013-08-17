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

package vn.mangtuyendung.controller.member;

import com.hadoopvietnam.common.ErrorMessage;
import com.hadoopvietnam.common.ValidationResponse;
import com.hadoopvietnam.controller.MyResourceMessage;
import com.hadoopvietnam.persistence.domain.AccountDomain;
import com.hadoopvietnam.service.AccountService;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.mangtuyendung.controller.SiteAbstractController;
import vn.mangtuyendung.form.profile.PersonEducationForm;
import vn.mangtuyendung.form.validator.profile.PersonEducationValidator;
import vn.mangtuyendung.persistence.domain.member.JobEducationDomain;
import vn.mangtuyendung.service.member.JobEducationService;

/*
 * ProfileEducationController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Profile education controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */

@RequestMapping({"/thanh-vien/bang-cap-chung-chi"})
@Controller
public class ProfileEducationController extends SiteAbstractController {

    private final Logger logger = LoggerFactory.getLogger(ProfileEducationController.class);
    @Autowired
    AccountService accountService;
    @Autowired
    JobEducationService educationService;
    @Autowired
    PersonEducationValidator validator;
    @Autowired
    MyResourceMessage resourceMessage;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public String viewEducation(PersonEducationForm form, Model model) {
        model.addAttribute("educationForm", new PersonEducationForm());
        return "profile/education";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/json"}, method = {RequestMethod.POST})
    @ResponseBody
    public ValidationResponse updateEducationAjaxJson(Model model, @ModelAttribute("educationForm") PersonEducationForm form, HttpServletRequest request, BindingResult bindingResult) {
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
    @RequestMapping(method = {RequestMethod.POST})
    public String updateEducation(@ModelAttribute("educationForm") PersonEducationForm form, HttpServletRequest request, BindingResult bindingResult, Model model) {
        try {
            this.validator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("educationForm", form);
                return "profile/education";
            }
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if (accountDomain != null) {
                JobEducationDomain domain = new JobEducationDomain();
                domain.setId(form.getId());
                domain.setProfileId(accountDomain.getMainProfile());
                domain.setSchoolName(form.getSchoolName());
                domain.setSchoolFieldOfStudy(form.getSchoolFieldOfStudy());
                domain.setStartYear(form.getStartYear());
                domain.setEndYear(form.getEndYear());
                domain.setDescription(form.getDescription());
                domain.setCreated(new Date());
                if ((domain.getId() == 0L)
                        && (this.educationService.save(domain))) {
                    return "profile/inserteducation";
                }

                domain.setProfileId(accountDomain.getMainProfile());
                domain.setUpdated(new Date());
                this.educationService.edit(domain);
            }
            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update education resume error.", ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public String editEducation(@PathVariable("id") long id, Model model) {
        try {
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if (accountDomain != null) {
                JobEducationDomain domain = this.educationService.findById(id);
                if (domain.getProfileId() == accountDomain.getMainProfile()) {
                    PersonEducationForm form = new PersonEducationForm();
                    form.setId(domain.getId());
                    form.setSchoolName(domain.getSchoolName());
                    form.setSchoolFieldOfStudy(domain.getSchoolFieldOfStudy());
                    form.setStartYear(domain.getStartYear());
                    form.setEndYear(domain.getEndYear());
                    form.setDescription(domain.getDescription());
                    model.addAttribute("educationForm", form);
                    return "profile/education";
                }
            }
            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update education resume error.", ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/do/{id}"}, method = RequestMethod.GET)
    public String deleteEducation(@PathVariable("id") long id, Model model) {
        try {
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if ((accountDomain != null)
                    && (this.educationService.delete(id, accountDomain.getMainProfile()))) {
                return "profile/successeducation";
            }

            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update education resume error.", ex);
        }
        return "home/maintenance";
    }
}