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
package vn.mangtuyendung.controller;

import com.hadoopvietnam.common.ErrorMessage;
import com.hadoopvietnam.common.ValidationResponse;
import com.hadoopvietnam.controller.MyResourceMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.mangtuyendung.common.JobUtils;
import vn.mangtuyendung.form.SearchForm;
import vn.mangtuyendung.form.SearchQuickForm;
import vn.mangtuyendung.form.validator.SearchValidator;
import vn.mangtuyendung.persistence.domain.JobCategoryDomain;
import vn.mangtuyendung.persistence.domain.JobDomain;
import vn.mangtuyendung.persistence.domain.JobLocationDomain;
import vn.mangtuyendung.persistence.domain.JobSearchDomain;
import vn.mangtuyendung.service.JobSearchService;

/*
 * HomeController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Home controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 *  25-jan-2013     tuanta      Refactor code
 */
@RequestMapping({"/"})
@Controller
public class HomeController extends SiteAbstractController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private JobSearchService searchService;
    @Autowired
    MyResourceMessage resourceMessage;
    @Autowired
    private SearchValidator searchValidator;
    private boolean isLogAction = true;

    @ModelAttribute("searchQuickForm")
    public SearchQuickForm getSearchQuickForm() {
        return new SearchQuickForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        try {
            String query = "*:*";
            query = query + " AND expired:[NOW TO *]";
            query = query + " AND jobUpdated:[NOW-30DAY TO *]";
            QueryResponse response = solrService.search(query, 0, 10);
            model.addAttribute("title", "Mạng xã hội tuyển dụng và việc làm");
            model.addAttribute("description", "Mạng xã hội tuyển dụng và việc làm đầu tiên tại Việt Nam. Hàng ngàn việc làm, hàng triệu ứng viên mới nhất cập nhật liên tục.");
            model.addAttribute("jobs", solrService.convertDocument(response.getResults()));
            setEducation(response, model);
            setExperience(response, model);
            setTopLocation(response, model);
            setLocation(response, model);
            setCategory(response, model);
            model.addAttribute("action_url", "/tim-viec-lam-nhanh");
            model.addAttribute("domains", response.getFacetField("domain").getValues());
            return "home/index";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/viec-lam-tai-{location}"}, method = RequestMethod.GET)
    public String location(@ModelAttribute("searchQuickForm") SearchQuickForm form, @PathVariable("location") String location, HttpServletRequest request, Model model) {
        try {
            JobLocationDomain domain = locationService.findByUrl(location);
            if (domain != null) {
                int sizeNo = 10;
                String query = "jobLocation:\"" + domain.getName() + "\" AND";
                
                if (form.getFlocations() != null && form.getFlocations().length > 0) {
                    query = query + " (";
                    for (String location_ : form.getFlocations()) {
                        query = query + " jobLocation:\"" + location_ + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFeducations() != null && form.getFeducations().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFeducations()) {
                        query = query + " jobEducationLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFexperiences() != null && form.getFexperiences().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFexperiences()) {
                        query = query + " jobExperienceLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                
                query = query + " expired:[NOW TO *]";
                query = query + " AND jobUpdated:[NOW-30DAY TO *]";

                QueryResponse response = solrService.search(query, 0, sizeNo);
                long total = response.getResults().getNumFound();
                float nrOfPages = (float) total / sizeNo;
                model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

                model.addAttribute("pageNo", Integer.valueOf(form.getPageNumber()));
                model.addAttribute("sizeNo", Integer.valueOf(sizeNo));
                model.addAttribute("action_url", "/viec-lam-tai-" + location);
                
                model.addAttribute("ratingValue", Float.valueOf(JobUtils.ratingValue()));
                model.addAttribute("ratingCount", Long.valueOf(total));
                model.addAttribute("location", domain);
                model.addAttribute("jobs", solrService.convertDocument(response.getResults()));
                setLocation(response, model);
                setCategory(response, model);
                setEducation(response, model);
                setExperience(response, model);
                setTopLocation(response, model);
                model.addAttribute("domains", response.getFacetField("domain").getValues());
                String title = "Tìm việc làm tại " + domain.getName();
                model.addAttribute("title", title);
                model.addAttribute("description", "Chúng tôi cung cấp thông tin việc làm tại " + domain.getName() + " miễn phí. Hàng ngàn việc làm tại " + domain.getName() + " cập nhật liên tục đang chờ đón bạn.");
            }
            return "location/view";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }
    
    @RequestMapping(value = {"/viec-lam-tai-{location}"}, method = RequestMethod.POST)
    public String locationFilter(@ModelAttribute("searchQuickForm") SearchQuickForm form, @PathVariable("location") String location, HttpServletRequest request, Model model) {
        try {
            JobLocationDomain domain = locationService.findByUrl(location);
            if (domain != null) {
                int sizeNo = 10;
                String query = "jobLocation:\"" + domain.getName() + "\" AND";
                
                if (form.getFlocations() != null && form.getFlocations().length > 0) {
                    query = query + " (";
                    for (String location_ : form.getFlocations()) {
                        query = query + " jobLocation:\"" + location_ + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFeducations() != null && form.getFeducations().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFeducations()) {
                        query = query + " jobEducationLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFexperiences() != null && form.getFexperiences().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFexperiences()) {
                        query = query + " jobExperienceLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                
                query = query + " expired:[NOW TO *]";
                query = query + " AND jobUpdated:[NOW-30DAY TO *]";

                QueryResponse response = solrService.search(query, 0, sizeNo);
                long total = response.getResults().getNumFound();
                float nrOfPages = (float) total / sizeNo;
                model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

                model.addAttribute("pageNo", Integer.valueOf(form.getPageNumber()));
                model.addAttribute("sizeNo", Integer.valueOf(sizeNo));
                model.addAttribute("action_url", "/viec-lam-tai-" + location);
                
                model.addAttribute("ratingValue", Float.valueOf(JobUtils.ratingValue()));
                model.addAttribute("ratingCount", Long.valueOf(total));
                model.addAttribute("location", domain);
                model.addAttribute("jobs", solrService.convertDocument(response.getResults()));
                setLocation(response, model);
                setCategory(response, model);
                setEducation(response, model);
                setExperience(response, model);
                setTopLocation(response, model);
                model.addAttribute("domains", response.getFacetField("domain").getValues());
                String title = "Tìm việc làm tại " + domain.getName();
                model.addAttribute("title", title);
                model.addAttribute("description", "Chúng tôi cung cấp thông tin việc làm tại " + domain.getName() + " miễn phí. Hàng ngàn việc làm tại " + domain.getName() + " cập nhật liên tục đang chờ đón bạn.");
            }
            return "location/view";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/viec-lam-tai-{location}/p{page}-{size}"}, method = RequestMethod.GET)
    public String locationPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @PathVariable("location") String location, HttpServletRequest request, Model model) {
        try {
            JobLocationDomain domain = locationService.findByUrl(location);
            if (domain != null) {
                String query = "jobLocation:\"" + domain.getName() + "\"";
                query = query + " AND expired:[NOW TO *]";
                query = query + " AND jobUpdated:[NOW-30DAY TO *]";
                QueryResponse response;
                int sizeNo;
                if ((page != null) || (size != null)) {
                    sizeNo = size == null ? 10 : size.intValue();
                    page = Integer.valueOf(page == null ? 0 : page.intValue() - 1);
                    response = solrService.search(query, page.intValue() * sizeNo, sizeNo);
                } else {
                    sizeNo = 10;
                    response = solrService.search(query, 0, sizeNo);
                }
                long total = response.getResults().getNumFound();
                float nrOfPages = (float) total / sizeNo;
                model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

                model.addAttribute("ratingValue", Float.valueOf(JobUtils.ratingValue()));
                model.addAttribute("ratingCount", Long.valueOf(total));
                model.addAttribute("location", domain);
                model.addAttribute("jobs", solrService.convertDocument(response.getResults()));
                setLocation(response, model);
                setCategory(response, model);
                setEducation(response, model);
                setExperience(response, model);
                setTopLocation(response, model);
                model.addAttribute("domains", response.getFacetField("domain").getValues());
                String title = "Tìm việc làm tại " + domain.getName();
                if (page != null) {
                    title = title + " - Trang " + (page.intValue() + 1) + " " + size;
                }
                model.addAttribute("title", title);
                model.addAttribute("pageNo", Integer.valueOf(page.intValue() + 1));
                model.addAttribute("sizeNo", size);
                model.addAttribute("description", "Chúng tôi cung cấp thông tin việc làm tại " + domain.getName() + " miễn phí. Hàng ngàn việc làm tại " + domain.getName() + " cập nhật liên tục đang chờ đón bạn.");
            }
            return "location/view";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/tim-viec-lam-{category}"}, method = RequestMethod.GET)
    public String category(@ModelAttribute("searchQuickForm") SearchQuickForm form, @PathVariable("category") String category, HttpServletRequest request, Model model) {
        try {
            JobCategoryDomain domain = categoryService.findByUrl(category);
            if (domain != null) {
                int sizeNo = 10;
                String query = "jobCategory:\"" + domain.getName() + "\"";

                if (form.getFlocations() != null && form.getFlocations().length > 0) {
                    query = query + " (";
                    for (String location : form.getFlocations()) {
                        query = query + " jobLocation:\"" + location + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFeducations() != null && form.getFeducations().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFeducations()) {
                        query = query + " jobEducationLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFexperiences() != null && form.getFexperiences().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFexperiences()) {
                        query = query + " jobExperienceLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }

                query = query + " AND expired:[NOW TO *]";
                query = query + " AND jobUpdated:[NOW-30DAY TO *]";

                QueryResponse response = solrService.search(query, 0, sizeNo);
                long total = response.getResults().getNumFound();
                float nrOfPages = (float) total / sizeNo;
                model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

                model.addAttribute("ratingValue", Float.valueOf(JobUtils.ratingValue()));
                model.addAttribute("ratingCount", Long.valueOf(total));
                model.addAttribute("category", domain);
                model.addAttribute("action_url", "/tim-viec-lam-" + category);
                model.addAttribute("jobs", solrService.convertDocument(response.getResults()));
                setLocation(response, model);
                setCategory(response, model);
                setEducation(response, model);
                setExperience(response, model);
                setTopLocation(response, model);
                model.addAttribute("domains", response.getFacetField("domain").getValues());
                String title = "Tìm việc làm " + domain.getName();
                model.addAttribute("title", title);
                model.addAttribute("description", "Chúng tôi cung cấp thông tin việc làm " + domain.getName() + " miễn phí. Hàng ngàn việc làm " + domain.getName() + " cập nhật liên tục đang chờ đón bạn.");
            }
            return "category/view";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/tim-viec-lam-{category}"}, method = RequestMethod.POST)
    public String categoryFilter(@ModelAttribute("searchQuickForm") SearchQuickForm form, @PathVariable("category") String category, HttpServletRequest request, Model model) {
        try {
            JobCategoryDomain domain = categoryService.findByUrl(category);
            if (domain != null) {
                int sizeNo = 10;
                String query = "jobCategory:\"" + domain.getName() + "\" AND";

                if (form.getFlocations() != null && form.getFlocations().length > 0) {
                    query = query + " (";
                    for (String location : form.getFlocations()) {
                        query = query + " jobLocation:\"" + location + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFeducations() != null && form.getFeducations().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFeducations()) {
                        query = query + " jobEducationLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFexperiences() != null && form.getFexperiences().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFexperiences()) {
                        query = query + " jobExperienceLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }

                query = query + " expired:[NOW TO *]";
                query = query + " AND jobUpdated:[NOW-30DAY TO *]";

                QueryResponse response = solrService.search(query, form.getPageNumber() == 0 ? 0 : form.getPageNumber() - 1, sizeNo);
                long total = response.getResults().getNumFound();
                float nrOfPages = (float) total / sizeNo;
                model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));
                model.addAttribute("pageNo", Integer.valueOf(form.getPageNumber()));
                model.addAttribute("sizeNo", Integer.valueOf(sizeNo));
                model.addAttribute("ratingValue", Float.valueOf(JobUtils.ratingValue()));
                model.addAttribute("ratingCount", Long.valueOf(total));
                model.addAttribute("category", domain);
                model.addAttribute("action_url", "/tim-viec-lam-" + category);
                model.addAttribute("searchQuickForm", form);
                model.addAttribute("jobs", solrService.convertDocument(response.getResults()));
                setLocation(response, model);
                setCategory(response, model);
                setEducation(response, model);
                setExperience(response, model);
                setTopLocation(response, model);
                model.addAttribute("domains", response.getFacetField("domain").getValues());
                String title = "Tìm việc làm " + domain.getName();
                model.addAttribute("title", title);
                model.addAttribute("description", "Chúng tôi cung cấp thông tin việc làm " + domain.getName() + " miễn phí. Hàng ngàn việc làm " + domain.getName() + " cập nhật liên tục đang chờ đón bạn.");
            }
            return "category/view";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/tim-viec-lam-{category}/p{page}-{size}"}, method = RequestMethod.GET)
    public String categoryPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @PathVariable("category") String category, HttpServletRequest request, Model model) {
        try {
            JobCategoryDomain domain = categoryService.findByUrl(category);
            if (domain != null) {
                String query = "jobCategory:\"" + domain.getName() + "\"";
                query = query + " AND expired:[NOW TO *]";
                query = query + " AND jobUpdated:[NOW-30DAY TO *]";
                QueryResponse response;
                int sizeNo;
                if ((page != null) || (size != null)) {
                    sizeNo = size == null ? 10 : size.intValue();
                    page = Integer.valueOf(page == null ? 0 : page.intValue() - 1);
                    response = solrService.search(query, page.intValue() * sizeNo, sizeNo);
                } else {
                    sizeNo = 10;
                    response = solrService.search(query, 0, sizeNo);
                }
                long total = response.getResults().getNumFound();
                float nrOfPages = (float) total / sizeNo;
                model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

                model.addAttribute("ratingValue", Float.valueOf(JobUtils.ratingValue()));
                model.addAttribute("ratingCount", Long.valueOf(total));
                model.addAttribute("category", domain);
                model.addAttribute("jobs", solrService.convertDocument(response.getResults()));
                setLocation(response, model);
                setCategory(response, model);
                setEducation(response, model);
                setExperience(response, model);
                setTopLocation(response, model);
                model.addAttribute("domains", response.getFacetField("domain").getValues());
                String title = "Tìm việc làm " + domain.getName();
                if (page != null) {
                    title = title + " - Trang " + (page.intValue() + 1) + " " + size;
                }
                model.addAttribute("title", title);
                model.addAttribute("pageNo", Integer.valueOf(page.intValue() + 1));
                model.addAttribute("sizeNo", size);
                model.addAttribute("description", "Chúng tôi cung cấp thông tin việc làm " + domain.getName() + " miễn phí. Hàng ngàn việc làm " + domain.getName() + " cập nhật liên tục đang chờ đón bạn.");
            }
            return "category/view";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/tim-viec-lam-{category}/tai-{location}"}, method = RequestMethod.GET)
    public String locationCategory(@ModelAttribute("searchQuickForm") SearchQuickForm form, @PathVariable("category") String category, @PathVariable("location") String location, HttpServletRequest request, Model model) {
        try {
            JobLocationDomain _location = locationService.findByUrl(location);
            JobCategoryDomain _category = categoryService.findByUrl(category);
            if ((_location != null) && (_category != null)) {
                int sizeNo = 10;
                String query = "jobLocation:\"" + _location.getName() + "\" AND jobCategory:\"" + _category.getName() + "\" AND";

                if (form.getFlocations() != null && form.getFlocations().length > 0) {
                    query = query + " (";
                    for (String location_ : form.getFlocations()) {
                        query = query + " jobLocation:\"" + location_ + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFeducations() != null && form.getFeducations().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFeducations()) {
                        query = query + " jobEducationLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFexperiences() != null && form.getFexperiences().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFexperiences()) {
                        query = query + " jobExperienceLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }

                query = query + " expired:[NOW TO *]";
                query = query + " AND jobUpdated:[NOW-30DAY TO *]";
                QueryResponse response = solrService.search(query, 0, sizeNo);
                long total = response.getResults().getNumFound();
                float nrOfPages = (float) total / sizeNo;
                model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

                model.addAttribute("pageNo", Integer.valueOf(form.getPageNumber()));
                model.addAttribute("sizeNo", Integer.valueOf(sizeNo));
                model.addAttribute("action_url", "/tim-viec-lam-" + category + "/tai-" + location);
                model.addAttribute("searchQuickForm", form);
                model.addAttribute("ratingValue", Float.valueOf(JobUtils.ratingValue()));
                model.addAttribute("ratingCount", Long.valueOf(total));
                model.addAttribute("location", _location);
                model.addAttribute("category", _category);
                model.addAttribute("jobs", solrService.convertDocument(response.getResults()));
                setLocation(response, model);
                setCategory(response, model);
                setEducation(response, model);
                setExperience(response, model);
                setTopLocation(response, model);
                model.addAttribute("domains", response.getFacetField("domain").getValues());
                String title = "Tìm việc làm " + _category.getName() + " tại " + _location.getName();
                model.addAttribute("title", title);
                model.addAttribute("description", "Chúng tôi cung cấp thông tin việc làm " + _category.getName() + " tại " + _location.getName() + " miễn phí. Hàng ngàn việc làm cập nhật liên tục đang chờ đón bạn.");
            }
            return "category-location/index";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/tim-viec-lam-{category}/tai-{location}"}, method = RequestMethod.POST)
    public String locationCategoryFilter(@ModelAttribute("searchQuickForm") SearchQuickForm form, @PathVariable("category") String category, @PathVariable("location") String location, HttpServletRequest request, Model model) {
        try {
            JobLocationDomain _location = locationService.findByUrl(location);
            JobCategoryDomain _category = categoryService.findByUrl(category);
            if ((_location != null) && (_category != null)) {
                int sizeNo = 10;
                String query = "jobLocation:\"" + _location.getName() + "\" AND jobCategory:\"" + _category.getName() + "\" AND";

                if (form.getFlocations() != null && form.getFlocations().length > 0) {
                    query = query + " (";
                    for (String location_ : form.getFlocations()) {
                        query = query + " jobLocation:\"" + location_ + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFeducations() != null && form.getFeducations().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFeducations()) {
                        query = query + " jobEducationLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }
                if (form.getFexperiences() != null && form.getFexperiences().length > 0) {
                    query = query + " (";
                    for (String edu : form.getFexperiences()) {
                        query = query + " jobExperienceLevel:\"" + edu + "\" AND";
                    }
                    query = query + ")";
                    query = query.replaceAll("AND\\)", ")");
                    query = query + " AND ";
                }

                query = query + " expired:[NOW TO *]";
                query = query + " AND jobUpdated:[NOW-30DAY TO *]";
                QueryResponse response = solrService.search(query, 0, sizeNo);
                long total = response.getResults().getNumFound();
                float nrOfPages = (float) total / sizeNo;
                model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

                model.addAttribute("pageNo", Integer.valueOf(form.getPageNumber()));
                model.addAttribute("sizeNo", Integer.valueOf(sizeNo));
                model.addAttribute("action_url", "/tim-viec-lam-" + category + "/tai-" + location);
                model.addAttribute("searchQuickForm", form);
                model.addAttribute("ratingValue", Float.valueOf(JobUtils.ratingValue()));
                model.addAttribute("ratingCount", Long.valueOf(total));
                model.addAttribute("location", _location);
                model.addAttribute("category", _category);
                model.addAttribute("jobs", solrService.convertDocument(response.getResults()));
                setLocation(response, model);
                setCategory(response, model);
                setEducation(response, model);
                setExperience(response, model);
                setTopLocation(response, model);
                model.addAttribute("domains", response.getFacetField("domain").getValues());
                String title = "Tìm việc làm " + _category.getName() + " tại " + _location.getName();
                model.addAttribute("title", title);
                model.addAttribute("description", "Chúng tôi cung cấp thông tin việc làm " + _category.getName() + " tại " + _location.getName() + " miễn phí. Hàng ngàn việc làm cập nhật liên tục đang chờ đón bạn.");
            }
            return "category-location/index";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/tim-viec-lam-{category}/tai-{location}/p{page}-{size}"}, method = RequestMethod.GET)
    public String locationCategoryPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @PathVariable("category") String category, @PathVariable("location") String location, HttpServletRequest request, Model model) {
        try {
            JobLocationDomain _location = locationService.findByUrl(location);
            JobCategoryDomain _category = categoryService.findByUrl(category);
            if ((_location != null) && (_category != null)) {
                String query = "jobLocation:\"" + _location.getName() + "\" AND jobCategory:\"" + _category.getName() + "\"";
                query = query + " AND expired:[NOW TO *]";
                query = query + " AND jobUpdated:[NOW-30DAY TO *]";
                int sizeNo;
                QueryResponse response;
                if ((page != null) || (size != null)) {
                    sizeNo = size == null ? 10 : size.intValue();
                    page = Integer.valueOf(page == null ? 0 : page.intValue() - 1);
                    response = solrService.search(query, page.intValue() * sizeNo, sizeNo);
                } else {
                    sizeNo = 10;
                    response = solrService.search(query, 0, sizeNo);
                }
                long total = response.getResults().getNumFound();
                float nrOfPages = (float) total / sizeNo;
                model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

                model.addAttribute("ratingValue", Float.valueOf(JobUtils.ratingValue()));
                model.addAttribute("ratingCount", Long.valueOf(total));
                model.addAttribute("location", _location);
                model.addAttribute("category", _category);
                model.addAttribute("jobs", solrService.convertDocument(response.getResults()));
                setLocation(response, model);
                setCategory(response, model);
                setEducation(response, model);
                setExperience(response, model);
                setTopLocation(response, model);
                model.addAttribute("domains", response.getFacetField("domain").getValues());
                String title = "Tìm việc làm " + _category.getName() + " tại " + _location.getName();
                if (page != null) {
                    title = title + " - Trang " + (page.intValue() + 1) + " " + size;
                }
                model.addAttribute("title", title);
                model.addAttribute("pageNo", Integer.valueOf(page.intValue() + 1));
                model.addAttribute("sizeNo", size);
                model.addAttribute("description", "Chúng tôi cung cấp thông tin việc làm " + _category.getName() + " tại " + _location.getName() + " miễn phí. Hàng ngàn việc làm cập nhật liên tục đang chờ đón bạn.");
            }
            return "category-location/index";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/*/id-{id}"}, method = RequestMethod.GET)
    public String view(@PathVariable("id") String id, HttpServletRequest request, Model model) {
        try {
            QueryResponse response = solrService.search("*:*", 0, 10);
            setLocation(response, model);
            setCategory(response, model);
            logger.debug("Remote from ip " + request.getRemoteAddr() + " view url " + request.getRequestURI());
            model.addAttribute("domains", response.getFacetField("domain").getValues());
            response = solrService.search("signature:" + id, 0, 10);
            SolrDocumentList list = response.getResults();
            if ((list != null) && (!list.isEmpty())) {
                JobDomain job = solrService.convertDocument((SolrDocument) list.get(0));
                if (job != null) {
                    if (job.getExpired() != null && job.getExpired().after(new Date())) { //If job not expired
                        model.addAttribute("job", job);
                        model.addAttribute("title", "Cần tuyển " + job.getTitle() + " tại " + (String) job.getJobLocation().get(0));
                        model.addAttribute("description", job.getContent());
                        return "job/view";
                    } else {
                        String query = "(title:(\"" + job.getTitle() + "\")^2 OR title:(" + job.getTitle() + ")^1.5)";
                        query = query + " AND expired:[NOW TO *]";
                        query = query + " AND jobUpdated:[NOW-30DAY TO *]";
                        int sizeNo = 10;
                        response = solrService.search(query, 0, sizeNo);
                        setLocation(response, model);
                        setCategory(response, model);
                        model.addAttribute("domains", response.getFacetField("domain").getValues());
                        Map highlights = response.getHighlighting();
                        List<JobDomain> jobs = solrService.convertDocument(response.getResults());
                        for (JobDomain jobDomain : jobs) {
                            try {
                                jobDomain.setHigthligthTitle((String) ((List) ((Map) highlights.get(jobDomain.getSignature())).get("title")).get(0));
                            } catch (Exception ex) {
                            }
                        }
                        long total = response.getResults().getNumFound();
                        float nrOfPages = (float) total / sizeNo;

                        SearchForm form = new SearchForm();
                        form.setSlocation("all");
                        form.setStext(job.getTitle());

                        model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));
                        model.addAttribute("pageNo", Integer.valueOf(1));
                        model.addAttribute("sizeNo", Integer.valueOf(sizeNo));
                        model.addAttribute("jobs", jobs);
                        model.addAttribute("text", job.getTitle());
                        model.addAttribute("location", "all");
                        model.addAttribute("sform", form);
                        String title = "Cần tuyển " + form.getStext();
                        model.addAttribute("title", title);
                        model.addAttribute("description", "Cơ hội việc làm " + form.getStext() + " trên Mạng tuyển dụng.");
                        return "search/view";
                    }
                } else {
                    return "redirect:/";
                }
            }
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/tim-viec-lam/json"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public ValidationResponse searchAjaxJson(Model model, @ModelAttribute("sform") SearchForm form, HttpServletRequest request, BindingResult bindingResult) {
        ValidationResponse res = new ValidationResponse();
        searchValidator.validate(form, bindingResult);
        if (!bindingResult.hasErrors()) {
            res.setStatus("SUCCESS");
        } else {
            res.setStatus("FAIL");
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
            for (FieldError objectError : allErrors) {
                errorMesages.add(new ErrorMessage(objectError.getField(), resourceMessage.getMessage(objectError.getCode(), request)));
            }
            res.setErrorMessageList(errorMesages);
        }
        return res;
    }

    @RequestMapping(value = "/tim-viec-lam-nhanh", method = RequestMethod.POST)
    public String searchQuick(Model model, @ModelAttribute("searchQuickForm") SearchQuickForm form, HttpServletRequest request, BindingResult bindingResult) {
        try {
            String query = "";
            if (!StringUtils.isEmpty(form.getFtext())) {
                query = query + "(title:(\"" + form.getFtext() + "\")^2 OR title:(" + form.getFtext() + ")^1.5)";
                query = query + " AND ";
            }
            if (!"all".equals(form.getFlocation()) && !"".equals(form.getFlocation())) {
                query = query + " jobLocation:\"" + form.getFlocation() + "\"";
                query = query + " AND ";
            }
            if (form.getFlocations() != null && form.getFlocations().length > 0) {
                query = query + " (";
                for (String location : form.getFlocations()) {
                    query = query + " jobLocation:\"" + location + "\" AND";
                }
                query = query + ")";
                query = query.replaceAll("AND\\)", ")");
                query = query + " AND ";
            }
            if (form.getFeducations() != null && form.getFeducations().length > 0) {
                query = query + " (";
                for (String edu : form.getFeducations()) {
                    query = query + " jobEducationLevel:\"" + edu + "\" AND";
                }
                query = query + ")";
                query = query.replaceAll("AND\\)", ")");
                query = query + " AND ";
            }
            if (form.getFexperiences() != null && form.getFexperiences().length > 0) {
                query = query + " (";
                for (String edu : form.getFexperiences()) {
                    query = query + " jobExperienceLevel:\"" + edu + "\" AND";
                }
                query = query + ")";
                query = query.replaceAll("AND\\)", ")");
                query = query + " AND ";
            }
            query = query + " expired:[NOW TO *]";
            query = query + " AND jobUpdated:[NOW-30DAY TO *]";
            int sizeNo = 10;

            QueryResponse response = solrService.search(query, form.getPageNumber() == 0 ? 0 : form.getPageNumber() - 1, sizeNo);
            setLocation(response, model);
            setCategory(response, model);
            setEducation(response, model);
            setExperience(response, model);
            setTopLocation(response, model);
            model.addAttribute("domains", response.getFacetField("domain").getValues());
            Map highlights = response.getHighlighting();
            List<JobDomain> jobs = solrService.convertDocument(response.getResults());
            for (JobDomain jobDomain : jobs) {
                try {
                    jobDomain.setHigthligthTitle((String) ((List) ((Map) highlights.get(jobDomain.getSignature())).get("title")).get(0));
                } catch (Exception ex) {
                }
            }
            long total = response.getResults().getNumFound();
            float nrOfPages = (float) total / sizeNo;
            model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

            model.addAttribute("pageNo", Integer.valueOf(form.getPageNumber()));
            model.addAttribute("sizeNo", Integer.valueOf(sizeNo));
            model.addAttribute("jobs", jobs);
            model.addAttribute("action_url", "/tim-viec-lam-nhanh");
            model.addAttribute("text", form.getFtext());
            model.addAttribute("location", form.getFlocation());
            SearchForm searchForm = new SearchForm();
            searchForm.setSlocation(form.getFlocation());
            searchForm.setStext(form.getFtext());
            model.addAttribute("sform", searchForm);
            model.addAttribute("searchQuickForm", form);
            String title = "Tìm việc làm " + form.getFtext();
            if (!"all".equals(form.getFlocation())) {
                title = title + " tại " + form.getFlocation();
            }
            model.addAttribute("title", title);
            model.addAttribute("title", "Tìm việc làm " + form.getFtext() + " tại " + form.getFlocation());
            model.addAttribute("description", "Cơ hội việc làm " + form.getFtext() + " tại " + form.getFlocation() + " trên Mạng tuyển dụng.");
            return "search/filter";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/tim-viec-lam"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String search(@ModelAttribute("sform") SearchForm form, BindingResult bindingResult, Model model, HttpServletRequest request) {
        searchValidator.validate(form, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        try {
            String query = "(title:(\"" + form.getStext() + "\")^2 OR title:(" + form.getStext() + ")^1.5)";
            if (!"all".equals(form.getSlocation())) {
                query = query + " AND jobLocation:\"" + form.getSlocation() + "\"";
            }
            query = query + " AND expired:[NOW TO *]";
            query = query + " AND jobUpdated:[NOW-30DAY TO *]";
            int sizeNo = 10;
            QueryResponse response = solrService.search(query, 0, sizeNo);
            setLocation(response, model);
            setCategory(response, model);
            setEducation(response, model);
            setExperience(response, model);
            setTopLocation(response, model);
            model.addAttribute("domains", response.getFacetField("domain").getValues());
            Map highlights = response.getHighlighting();
            List<JobDomain> jobs = solrService.convertDocument(response.getResults());
            for (JobDomain jobDomain : jobs) {
                try {
                    jobDomain.setHigthligthTitle((String) ((List) ((Map) highlights.get(jobDomain.getSignature())).get("title")).get(0));
                } catch (Exception ex) {
                }
            }
            long total = response.getResults().getNumFound();
            float nrOfPages = (float) total / sizeNo;
            model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

            model.addAttribute("pageNo", Integer.valueOf(1));
            model.addAttribute("sizeNo", Integer.valueOf(sizeNo));
            model.addAttribute("jobs", jobs);
            model.addAttribute("text", form.getStext());
            model.addAttribute("location", form.getSlocation());
            model.addAttribute("sform", form);
            SearchQuickForm searchQuickForm = new SearchQuickForm();
            searchQuickForm.setFlocation(form.getSlocation());
            searchQuickForm.setFtext(form.getStext());
            model.addAttribute("searchQuickForm", searchQuickForm);
            model.addAttribute("sform", form);
            String title = "Tìm việc làm " + form.getStext();
            if (!"all".equals(form.getSlocation())) {
                title = title + " tại " + form.getSlocation();
            }
            model.addAttribute("title", title);
            model.addAttribute("title", "Tìm việc làm " + form.getStext() + " tại " + form.getSlocation());
            model.addAttribute("description", "Cơ hội việc làm " + form.getStext() + " tại " + form.getSlocation() + " trên Mạng tuyển dụng.");
            try {
                if (isLogAction) {
                    JobSearchDomain domain = new JobSearchDomain();
                    domain.setIp(request.getRemoteAddr());
                    domain.setText(form.getStext());
                    domain.setLocation(form.getSlocation());
                    domain.setSessionId(request.getSession().getId());
                    domain.setUrl(request.getRequestURI());
                    domain.setUserAgent(request.getHeader("User-Agent"));
                    searchService.save(domain);
                }
            } catch (Exception ex) {
                logger.warn("Log search param error.", ex);
            }
            return "search/view";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/tim-viec-lam/{text}"}, method = RequestMethod.GET)
    public String searchText(@PathVariable("text") String text, HttpServletRequest request, Model model) {
        try {
            text = text.replaceAll("-", " ");
            String query = "(title:(\"" + text + "\")^2 OR title:(" + text + ")^1.5 OR content:(\"" + text + "\")^1.1 OR content:" + text + ")";
            query = query + " AND expired:[NOW TO *]";
            query = query + " AND jobUpdated:[NOW-30DAY TO *]";

            int sizeNo = 10;
            QueryResponse response = solrService.search(query, 0, sizeNo);
            setLocation(response, model);
            setCategory(response, model);
            setEducation(response, model);
            setExperience(response, model);
            setTopLocation(response, model);
            model.addAttribute("domains", response.getFacetField("domain").getValues());
            Map highlights = response.getHighlighting();
            List<JobDomain> jobs = solrService.convertDocument(response.getResults());
            for (JobDomain jobDomain : jobs) {
                try {
                    jobDomain.setHigthligthTitle((String) ((List) ((Map) highlights.get(jobDomain.getSignature())).get("title")).get(0));
                } catch (Exception ex) {
                }
            }
            long total = response.getResults().getNumFound();
            float nrOfPages = (float) total / sizeNo;
            model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

            model.addAttribute("pageNo", 1);
            model.addAttribute("sizeNo", Integer.valueOf(sizeNo));
            model.addAttribute("jobs", jobs);
            model.addAttribute("text", text);
            model.addAttribute("location", "all");
            SearchForm form = new SearchForm();
            form.setSlocation("all");
            form.setStext(text);
            model.addAttribute("sform", form);
            String title = "Tìm việc làm " + form.getStext();
            if (!"all".equals(form.getSlocation())) {
                title = title + " tại " + form.getSlocation();
            }
            model.addAttribute("title", title);
            model.addAttribute("description", "Cơ hội việc làm " + form.getStext() + " tại " + form.getSlocation() + " trên Mạng tuyển dụng.");
            return "search/view";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/tim-viec-lam/cong-viec-{text}/{location}/p{page}-{size}"}, method = RequestMethod.GET)
    public String searchPage(@PathVariable("text") String text, @PathVariable("location") String location, @PathVariable("page") Integer page, @PathVariable("size") Integer size, HttpServletRequest request, Model model) {
        try {
            text = text.replaceAll("\\+", " ");
            location = location.replaceAll("\\+", " ");
            String query = "(title:(\"" + text + "\")^2 OR title:(" + text + ")^1.5 OR content:(\"" + text + "\")^1.1 OR content:" + text + ")";
            if (!"all".equals(location)) {
                query = query + " AND jobLocation:\"" + location + "\"";
            }
            query = query + " AND expired:[NOW TO *]";
            query = query + " AND jobUpdated:[NOW-30DAY TO *]";

            int sizeNo = 10;
            QueryResponse response = solrService.search(query, (page.intValue() - 1) * sizeNo, sizeNo);
            setLocation(response, model);
            setCategory(response, model);
            setEducation(response, model);
            setExperience(response, model);
            setTopLocation(response, model);
            model.addAttribute("domains", response.getFacetField("domain").getValues());
            Map highlights = response.getHighlighting();
            List<JobDomain> jobs = solrService.convertDocument(response.getResults());
            for (JobDomain jobDomain : jobs) {
                try {
                    jobDomain.setHigthligthTitle((String) ((List) ((Map) highlights.get(jobDomain.getSignature())).get("title")).get(0));
                } catch (Exception ex) {
                }
            }
            long total = response.getResults().getNumFound();
            float nrOfPages = (float) total / sizeNo;
            model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

            model.addAttribute("pageNo", page);
            model.addAttribute("sizeNo", Integer.valueOf(sizeNo));
            model.addAttribute("jobs", jobs);
            model.addAttribute("text", text);
            model.addAttribute("location", location);
            SearchForm form = new SearchForm();
            form.setSlocation(location);
            form.setStext(text);
            model.addAttribute("sform", form);
            String title = "Tìm việc làm " + form.getStext();
            if (!"all".equals(form.getSlocation())) {
                title = title + " tại " + form.getSlocation();
            }
            if (page != null) {
                title = title + " - Trang " + (page.intValue() + 1) + " " + size;
            }
            model.addAttribute("title", title);
            model.addAttribute("description", "Cơ hội việc làm " + form.getStext() + " tại " + form.getSlocation() + " trên Mạng tuyển dụng.");
            return "search/view";
        } catch (SolrServerException ex) {
            logger.error("Cannot query data from Solr Server." + ex.toString(), ex);
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString(), ex);
        }
        return "home/maintenance";
    }

    @RequestMapping(value = {"/quy-dinh-thanh-vien"}, method = RequestMethod.GET)
    public String policy(Model model) {
        model.addAttribute("sform", new SearchForm());
        model.addAttribute("title", "Quy định thành viên tham gia Mạng Tuyển Dụng");
        model.addAttribute("description", "Các quyền lợi và trách nhiêm thành viên khi tham gia Mạng Tuyển Dụng.");
        return "home/policy";
    }
}