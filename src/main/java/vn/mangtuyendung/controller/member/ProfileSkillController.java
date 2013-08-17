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
import vn.mangtuyendung.form.profile.PersonSkillForm;
import vn.mangtuyendung.form.validator.profile.PersonSkillValidator;
import vn.mangtuyendung.persistence.domain.member.JobSkillDomain;
import vn.mangtuyendung.service.member.JobSkillService;

/*
 * ProfileSkillController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Profile skill controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */
@RequestMapping({"/thanh-vien/ky-nang-chuyen-mon"})
@Controller
public class ProfileSkillController extends SiteAbstractController {

    private final Logger logger = LoggerFactory.getLogger(ProfileSkillController.class);
    @Autowired
    AccountService accountService;
    @Autowired
    JobSkillService skillService;
    @Autowired
    PersonSkillValidator validator;
    @Autowired
    MyResourceMessage resourceMessage;

    /**
     * View skill page.
     *
     * @param form Form input skill
     * @return MVC name
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public String viewSkill(PersonSkillForm form, Model model) {
        model.addAttribute("skillForm", new PersonSkillForm());
        return "profile/skill";
    }

    /**
     * Post skill data json via AJAX
     *
     * @param form Form input skill
     * @param bindingResult {@link BindingResult}
     * @return {@link ValidationResponse}
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/json"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public ValidationResponse updateSkillAjaxJson(Model model, @ModelAttribute("skillForm") PersonSkillForm form, HttpServletRequest request, BindingResult bindingResult) {
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

    /**
     * Update skill data
     *
     * @param bindingResult {@link BindingResult}
     * @return MVCname
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String updateSkill(@ModelAttribute("skillForm") PersonSkillForm form, HttpServletRequest request, BindingResult bindingResult, Model model) {
        try {
            this.validator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("skillForm", form);
                return "profile/skill";
            }
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if (accountDomain != null) {
                JobSkillDomain domain = new JobSkillDomain();
                domain.setSkillName(form.getSkillName());
                domain.setCreated(new Date());
                domain.setProfileId(accountDomain.getMainProfile());
                if ((form.getId() == 0L)
                        && (this.skillService.save(domain))) {
                    model.addAttribute("skillForm", new PersonSkillForm());
                    return "profile/insertskill";
                }

                domain.setId(form.getId());
                domain.setProfileId(accountDomain.getMainProfile());
                domain.setUpdated(new Date());
                this.skillService.edit(domain);
            }
            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update skill resume error.", ex);
        }
        return "home/maintenance";
    }

    /**
     * Edit skill
     *
     * @param id Skill id
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public String editSkill(@PathVariable("") long id, Model model) {
        try {
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if (accountDomain != null) {
                JobSkillDomain domain = this.skillService.findById(id);
                if (accountDomain.getMainProfile() == domain.getProfileId()) {
                    PersonSkillForm form = new PersonSkillForm();
                    form.setId(domain.getId());
                    form.setSkillName(domain.getSkillName());
                    model.addAttribute("skillForm", form);
                    return "profile/skill";
                }
            }
            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update skill resume error.", ex);
        }
        return "home/maintenance";
    }

    /**
     * Delete skill
     *
     * @param id Skill id
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/do/{id}"}, method = RequestMethod.GET)
    public String deleteSkill(@PathVariable("id") long id, Model model) {
        try {
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if ((accountDomain != null)
                    && (this.skillService.delete(id, accountDomain.getMainProfile()))) {
                return "profile/successskill";
            }

            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update skill resume error.", ex);
        }
        return "home/maintenance";
    }
}