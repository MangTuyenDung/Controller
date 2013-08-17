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

import com.hadoopvietnam.persistence.domain.AccountDomain;
import com.hadoopvietnam.service.AccountService;
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
import vn.mangtuyendung.persistence.domain.member.ProfileDomain;
import vn.mangtuyendung.service.member.JobEducationService;
import vn.mangtuyendung.service.member.JobPositionService;
import vn.mangtuyendung.service.member.JobSkillService;
import vn.mangtuyendung.service.member.ProfileService;

/*
 * MemberController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Member controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */

@RequestMapping({"/thanh-vien/{username}"})
@Controller
public class MemberController extends SiteAbstractController {

    private final Logger logger = LoggerFactory.getLogger(MemberController.class);
    @Autowired
    AccountService accountService;
    @Autowired
    ProfileService profileService;
    @Autowired
    JobPositionService positionService;
    @Autowired
    JobEducationService educationService;
    @Autowired
    JobSkillService skillService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public String viewMember(@PathVariable("username") String username, Model model) {
        try {
            this.logger.debug("View my profile " + username);
            AccountDomain accountDomain = this.accountService.findByUsername(username);
            ProfileDomain profileDomain = this.profileService.findByUserId(accountDomain.getId());
            if (profileDomain == null) {
                return "redirect:/thanh-vien.html";
            }
            model.addAttribute("account", accountDomain);
            model.addAttribute("profile", profileDomain);
            model.addAttribute("positions", this.positionService.findByProfile(accountDomain.getMainProfile()));
            model.addAttribute("educations", this.educationService.findByProfile(accountDomain.getMainProfile()));
            model.addAttribute("skills", this.skillService.findByProfile(accountDomain.getMainProfile()));
            return "member/index";
        } catch (Exception ex) {
            this.logger.error("View member error", ex);
        }
        return "home/maintenance";
    }
}