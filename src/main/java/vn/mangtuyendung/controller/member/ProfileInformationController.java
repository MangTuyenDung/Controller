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
import vn.mangtuyendung.form.profile.PersonInformationForm;
import vn.mangtuyendung.form.validator.profile.PersonInformationValidator;
import vn.mangtuyendung.persistence.domain.member.ProfileDomain;
import vn.mangtuyendung.service.member.ProfileService;

/*
 * ProfileInformationController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Profile information controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */

@RequestMapping({"/thanh-vien/thong-tin-thanh-vien"})
@Controller
public class ProfileInformationController extends SiteAbstractController {

    private final Logger logger = LoggerFactory.getLogger(ProfileInformationController.class);
    @Autowired
    AccountService accountService;
    @Autowired
    ProfileService profileService;
    @Autowired
    PersonInformationValidator validator;
    @Autowired
    MyResourceMessage resourceMessage;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public String viewInformation(PersonInformationForm form, Model model) {
        model.addAttribute("informationForm", new PersonInformationForm());
        return "profile/information";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/json"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public ValidationResponse updateInformationAjaxJson(Model model, @ModelAttribute("informationForm") PersonInformationForm form, HttpServletRequest request, BindingResult bindingResult) {
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
    public String updateInformation(@ModelAttribute("informationForm") PersonInformationForm form, HttpServletRequest request, BindingResult bindingResult, Model model) {
        try {
            this.validator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("informationForm", form);
                return "profile/information";
            }
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if (accountDomain != null) {
                ProfileDomain profileDomain = new ProfileDomain();
                profileDomain.setId(form.getId());
                profileDomain.setUserId(accountDomain.getId());
                profileDomain.setPersonName(form.getPersonName());
                profileDomain.setPersonSex(form.getPersonSex());
                profileDomain.setPersonMarried(form.getPersonMarried());
                profileDomain.setPersonMobile(form.getPersonMobile());
                profileDomain.setResumeOverview(form.getResumeOverview());
                if (profileDomain.getId() == 0L) {
                    profileDomain.setCreated(new Date());
                    if (this.profileService.save(profileDomain)) {
                        if (this.accountService.mainProfile(profileDomain.getId(), getUsername())) {
                            model.addAttribute("educationForm", new PersonEducationForm());
                            return "profile/successinformation";
                        }
                    }
                }
                profileDomain.setUserId(accountDomain.getId());
                profileDomain.setUpdated(new Date());
                this.profileService.edit(profileDomain);
            }
            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update information resume error.", ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public String editInformation(@PathVariable("id") long id, Model model) {
        try {
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if (accountDomain != null) {
                ProfileDomain profileDomain = this.profileService.findById(id);
                if (profileDomain.getId() == accountDomain.getMainProfile()) {
                    PersonInformationForm form = new PersonInformationForm();
                    form.setId(profileDomain.getId());
                    form.setPersonName(profileDomain.getPersonName());
                    form.setPersonMobile(profileDomain.getPersonMobile());
                    form.setPersonSex(profileDomain.getPersonSex());
                    form.setPersonMarried(profileDomain.getPersonMarried());
                    form.setResumeOverview(profileDomain.getResumeOverview());
                    model.addAttribute("informationForm", form);
                    return "profile/information";
                }
            }
            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update information resume error.", ex);
        }
        return "home/maintenance";
    }
}