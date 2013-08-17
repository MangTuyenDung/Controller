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

import com.hadoopvietnam.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.mangtuyendung.controller.SiteAbstractController;
import vn.mangtuyendung.service.member.ProfileService;

/*
 * ActivateAccountController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Activate account controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */

@RequestMapping({"/thanh-vien/kich-hoat-tai-khoan/{key}/{signature}"})
@Controller
public class ActivateAccountController extends SiteAbstractController {

    private final Logger logger = LoggerFactory.getLogger(ActivateAccountController.class);
    @Autowired
    AccountService accountService;
    @Autowired
    ProfileService profileService;

    @RequestMapping(method = RequestMethod.GET)
    public String viewActive(@PathVariable("key") String key, @PathVariable("signature") String signature, Model model) {
        
        return "";
    }
}