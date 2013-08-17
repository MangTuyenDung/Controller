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
package vn.mangtuyendung.controller.crawl;

import com.google.gson.Gson;
import java.sql.Timestamp;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.mangtuyendung.persistence.domain.CrawlCompanyDomain;
import vn.mangtuyendung.service.CrawlCompanyService;

@RequestMapping({"/crawlcompany"})
@Controller
public class CrawlCompanyController {

    private static Logger logger = LoggerFactory.getLogger(CrawlCompanyController.class);
    @Autowired
    CrawlCompanyService service;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String post(CrawlCompanyDomain crawl) {
        if ((crawl.getId() != 0L) && (crawl.getCompanyName() != null) && (crawl.getCompanyEmail() != null)) {
            try {
                logger.debug("Receiver post data from crawler " + new Timestamp(System.currentTimeMillis()));
                crawl.setCompanyAddress(convertBase64(crawl.getCompanyAddress()));
                crawl.setCompanyBusinessScope(convertBase64(crawl.getCompanyBusinessScope()));
                crawl.setCompanyName(convertBase64(crawl.getCompanyName()));
                crawl.setCompanyNameEnglish(convertBase64(crawl.getCompanyNameEnglish()));
                crawl.setCompanyOverview(convertBase64(crawl.getCompanyOverview()));
                crawl.setCompanyPhone(convertBase64(crawl.getCompanyPhone()));
                crawl.setCompanyRange(convertBase64(crawl.getCompanyRange()));
                this.service.save(crawl);
                logger.debug("Store successful data crawled " + new Timestamp(System.currentTimeMillis()));
                return "OK";
            } catch (Exception ex) {
                logger.error(ex.toString());
            }
        }
        Gson gson = new Gson();
        return gson.toJson(crawl);
    }
    
    private String convertBase64(String data) {
        if (data != null) {
            data = new String(Base64.decodeBase64(data));
            return StringEscapeUtils.unescapeHtml(data);
        }
        return null;
    }
}
