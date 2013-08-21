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

import com.hadoopvietnam.controller.AbstractController;
import com.hadoopvietnam.form.admin.ChangePasswordForm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.mangtuyendung.form.SearchForm;
import vn.mangtuyendung.persistence.domain.JobCategoryDomain;
import vn.mangtuyendung.persistence.domain.JobQuickDomain;
import vn.mangtuyendung.persistence.domain.JobLocationDomain;
import vn.mangtuyendung.service.JobCategoryService;
import vn.mangtuyendung.service.JobLocationService;
import vn.mangtuyendung.solr.SolrService;

/*
 * SiteAbstractController.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Commons for controller
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 *  25-jan-2013     tuanta      Refactor code
 */
public abstract class SiteAbstractController extends AbstractController {

    @Autowired
    protected SolrService solrService;
    @Autowired
    protected JobCategoryService categoryService;
    @Autowired
    protected JobLocationService locationService;
    private QueryResponse response_;

    protected QueryResponse getQueryResponse() {
        if (response_ == null) {
            String query = "*:*";
            query = query + " AND expired:[NOW TO *]";
            query = query + " AND jobUpdated:[NOW-30DAY TO *]";
            try {
                return this.solrService.search(query, 0, 10);
            } catch (SolrServerException ex) {
            }
        }
        return response_;
    }

    @ModelAttribute(value = "totaljobs")
    public long getTotaljobs() {
        if (response_ == null) {
            response_ = getQueryResponse();
        }
        try {
            return Long.valueOf(response_.getResults().getNumFound());
        } catch (Exception ex) {
        }
        return 0;

    }

    @ModelAttribute("changePasswordForm")
    public ChangePasswordForm getChangePasswordForm() {
        return new ChangePasswordForm();
    }

    @ModelAttribute("sform")
    public SearchForm getSearchForm() {
        return new SearchForm();
    }
    
    public void setLocation(QueryResponse response, Model model) {
        Collection<JobLocationDomain> locations = locationService.findAll();
        List<FacetField.Count> counts = response.getFacetField("jobLocation").getValues();
        for (JobLocationDomain jobLocationDomain : locations) {
            jobLocationDomain.setEnabled(false);
            for (FacetField.Count count : counts) {
                if (jobLocationDomain.getName().equals(count.getName())) {
                    long num = count.getCount();
                    if (num > 0) {
                        jobLocationDomain.setCount(count.getCount());
                        jobLocationDomain.setEnabled(true);
                    }
                    break;
                }
            }
        }
        model.addAttribute("locations", locations);
    }

    public void setCategory(QueryResponse response, Model model) {
        Collection<JobCategoryDomain> categories = categoryService.findAll();
        List<FacetField.Count> counts = response.getFacetField("jobCategory").getValues();
        for (JobCategoryDomain jobCategoryDomain : categories) {
            jobCategoryDomain.setEnabled(false);
            for (FacetField.Count count : counts) {
                if (jobCategoryDomain.getName().equals(count.getName())) {
                    long num = count.getCount();
                    if (num > 0) {
                        jobCategoryDomain.setCount(count.getCount());
                        jobCategoryDomain.setEnabled(true);
                    }
                    break;
                }
            }
        }
        model.addAttribute("categories", categories);
    }

    public void setEducation(QueryResponse response, Model model) {
        String[] eNames = {"Không yêu cầu", "Lao động phổ thông", "Trung cấp", "Cao đẳng", "Đại học", "Trên đại học"};
        Collection<JobQuickDomain> educations = new ArrayList<JobQuickDomain>();
        for (int i = 0; i < eNames.length; i++) {
            JobQuickDomain domain = new JobQuickDomain();
            domain.setName(eNames[i]);
            domain.setEnable(false);
            educations.add(domain);
        }
        List<FacetField.Count> counts = response.getFacetField("jobEducationLevel").getValues();
        for (JobQuickDomain eDomain : educations) {
            for (FacetField.Count count : counts) {
                if (eDomain.getName().equals(count.getName())) {
                    long num = count.getCount();
                    if (num > 0) {
                        eDomain.setCount(count.getCount());
                        eDomain.setEnable(true);
                    }
                    break;
                }
            }
        }

        model.addAttribute("educations", educations);
    }

    public void setExperience(QueryResponse response, Model model) {
        String[] eNames = {"Chưa có kinh nghiệm", "Dưới 1 năm", "1 năm", "2 năm", "3 năm", "4 năm", "5 năm", "Hơn 5 năm", "Hơn 10 năm"};
        Collection<JobQuickDomain> educations = new ArrayList<JobQuickDomain>();
        for (int i = 0; i < eNames.length; i++) {
            JobQuickDomain domain = new JobQuickDomain();
            domain.setName(eNames[i]);
            domain.setEnable(false);
            educations.add(domain);
        }
        List<FacetField.Count> counts = response.getFacetField("jobExperienceLevel").getValues();
        for (JobQuickDomain eDomain : educations) {
            for (FacetField.Count count : counts) {
                if (eDomain.getName().equals(count.getName())) {
                    long num = count.getCount();
                    if (num > 0) {
                        eDomain.setCount(count.getCount());
                        eDomain.setEnable(true);
                    }
                    break;
                }
            }
        }

        model.addAttribute("experiences", educations);
    }

    public void setTopLocation(QueryResponse response, Model model) {
        int i = 0;
        List<JobQuickDomain> toplocations = new ArrayList<JobQuickDomain>();
        List<FacetField.Count> counts = response.getFacetField("jobLocation").getValues();
        for (FacetField.Count count : counts) {
            if (i == 10) {
                break;
            }
            JobQuickDomain domain = new JobQuickDomain();
            domain.setName(count.getName());
            domain.setCount(count.getCount());
            toplocations.add(domain);
            i++;
        }

        model.addAttribute("topLocations", toplocations);
    }
}