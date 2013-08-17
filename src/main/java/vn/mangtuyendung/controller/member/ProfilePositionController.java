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
import vn.mangtuyendung.form.profile.PersonPositionForm;
import vn.mangtuyendung.form.validator.profile.PersonPositionValidator;
import vn.mangtuyendung.persistence.domain.member.JobPositionDomain;
import vn.mangtuyendung.service.member.JobPositionService;
import vn.mangtuyendung.service.member.ProfileService;

/*
 * ProfilePositionController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Profile position controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */

@RequestMapping("/thanh-vien/kinh-nghiem")
@Controller
public class ProfilePositionController extends SiteAbstractController {

    private final Logger logger = LoggerFactory.getLogger(ProfilePositionController.class);
    @Autowired
    AccountService accountService;
    @Autowired
    JobPositionService positionService;
    @Autowired
    ProfileService profileService;
    @Autowired
    PersonPositionValidator validator;
    @Autowired
    MyResourceMessage resourceMessage;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public String viewPosition(PersonPositionForm form, Model model) {
        model.addAttribute("positionForm", new PersonPositionForm());
        return "profile/position";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/json"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public ValidationResponse updatePositionAjaxJson(Model model, @ModelAttribute("positionForm") PersonPositionForm form, HttpServletRequest request, BindingResult bindingResult) {
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
    public String updatePosition(@ModelAttribute("positionForm") PersonPositionForm form, HttpServletRequest request, BindingResult bindingResult, Model model) {
        try {
            this.validator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("positionForm", form);
                return "profile/position";
            }
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if (accountDomain != null) {
                JobPositionDomain domain = new JobPositionDomain();
                domain.setId(form.getId());
                domain.setProfileId(accountDomain.getMainProfile());
                domain.setTitle(form.getTitle());
                domain.setCompanyName(form.getCompanyName());
                domain.setAddress(form.getAddress());
                domain.setStartDateMonth(form.getStartDateMonth());
                domain.setEndDateMonth(form.getEndDateMonth());
                domain.setStartDateYear(Integer.valueOf(form.getStartDateYear()).intValue());
                domain.setEndDateYear(Integer.valueOf(form.getEndDateYear()).intValue());
                domain.setCurrentHere(form.isCurrentHere());
                domain.setDescription(form.getDescription());
                domain.setCreated(new Date());
                if ((domain.getId() == 0L)
                        && (this.positionService.save(domain))) {
                    if (form.isCurrentHere()) {
                        this.profileService.update(accountDomain.getMainProfile(), form.getTitle(), form.getCompanyName());
                    }
                    model.addAttribute("positionForm", new PersonPositionForm());
                    return "profile/insertposition";
                }

                domain.setProfileId(accountDomain.getMainProfile());
                domain.setUpdated(new Date());
                this.positionService.edit(domain);
            }
            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update position resume error.", ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public String editPosition(@PathVariable("id") long id, Model model) {
        try {
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if (accountDomain != null) {
                JobPositionDomain positionDomain = this.positionService.findById(id);
                if (positionDomain.getProfileId() == accountDomain.getMainProfile()) {
                    PersonPositionForm form = new PersonPositionForm();
                    form.setCompanyName(positionDomain.getCompanyName());
                    form.setAddress(positionDomain.getAddress());
                    form.setCurrentHere(positionDomain.isCurrentHere());
                    form.setDescription(positionDomain.getDescription());
                    form.setEndDateMonth(positionDomain.getEndDateMonth());
                    form.setEndDateYear(positionDomain.getEndDateYear() + "");
                    form.setId(positionDomain.getId());
                    form.setProfileId(positionDomain.getProfileId());
                    form.setStartDateMonth(positionDomain.getStartDateMonth());
                    form.setStartDateYear(positionDomain.getStartDateYear() + "");
                    form.setTitle(positionDomain.getTitle());
                    model.addAttribute("positionForm", form);
                    return "profile/position";
                }
            }
            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update position resume error.", ex);
        }
        return "home/maintenance";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/do/{id}"}, method = RequestMethod.GET)
    public String deletePosition(@PathVariable("id") long id, Model model) {
        try {
            AccountDomain accountDomain = this.accountService.findByUsername(getUsername());
            if ((accountDomain != null)
                    && (this.positionService.delete(id, accountDomain.getMainProfile()))) {
                return "profile/successposition";
            }

            return "redirect:/thanh-vien.html";
        } catch (Exception ex) {
            this.logger.error("Update position resume error.", ex);
        }
        return "home/maintenance";
    }
}