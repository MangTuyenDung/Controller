package vn.mangtuyendung.controller.admin;

import java.util.ArrayList;
import java.util.List;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.mangtuyendung.controller.SiteAbstractController;
import vn.mangtuyendung.form.AdminApplyForm;
import vn.mangtuyendung.persistence.domain.ApplyDomain;
import vn.mangtuyendung.persistence.domain.JobDomain;
import vn.mangtuyendung.service.ApplyService;

@RequestMapping({"/admin/apply"})
@Controller
public class AdminApplyController extends SiteAbstractController {

    private Logger logger = LoggerFactory.getLogger(AdminApplyController.class);
    @Autowired
    private ApplyService applyService;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String index(Model model) {
        try {
            int sizeNo = 10;
            List myApplies = new ArrayList();
            List<ApplyDomain> applies = applyService.findBy(0, 10);
            for (ApplyDomain applyDomain : applies) {
                QueryResponse response = solrService.search("signature:" + applyDomain.getJobId(), 0, 1);
                SolrDocumentList list = response.getResults();
                if ((list != null) && (!list.isEmpty())) {
                    JobDomain job = solrService.convertDocument((SolrDocument) list.get(0));
                    AdminApplyForm myApply = new AdminApplyForm();
                    myApply.setId(applyDomain.getId());
                    myApply.setJobId(applyDomain.getJobId());
                    myApply.setApplyCreated(applyDomain.getCreated());
                    myApply.setJobName(job.getTitle());
                    myApply.setJobCompany(job.getCompanyName());
                    myApply.setJobExpired(job.getExpired());
                    myApply.setUsername(applyDomain.getUsername());

                    myApply.setTitle(applyDomain.getTitle());
                    myApply.setDescription(applyDomain.getDescription());

                    myApplies.add(myApply);
                }
            }
            model.addAttribute("applies", myApplies);
            long total = applyService.count();
            float nrOfPages = (float) total / sizeNo;
            model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

            return "admin/applies";
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString());
        }
        return "home/maintenance";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @RequestMapping(value = {"/p{page}-{size}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String indexPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size, Model model) {
        try {
            int sizeNo;
            List<ApplyDomain> applies;
            sizeNo = size == null ? 10 : size.intValue();
            page = Integer.valueOf(page == null ? 0 : page.intValue() - 1);
            int start = page.intValue() * sizeNo;
            applies = applyService.findBy(start, sizeNo);

            List<AdminApplyForm> myApplies = new ArrayList();
            for (ApplyDomain applyDomain : applies) {
                QueryResponse response = solrService.search("signature:" + applyDomain.getJobId(), 0, 1);
                SolrDocumentList list = response.getResults();
                if ((list != null) && (!list.isEmpty())) {
                    JobDomain job = solrService.convertDocument((SolrDocument) list.get(0));
                    AdminApplyForm myApply = new AdminApplyForm();
                    myApply.setId(applyDomain.getId());
                    myApply.setJobId(applyDomain.getJobId());
                    myApply.setApplyCreated(applyDomain.getCreated());
                    myApply.setJobName(job.getTitle());
                    myApply.setJobCompany(job.getCompanyName());
                    myApply.setJobExpired(job.getExpired());
                    myApply.setUsername(applyDomain.getUsername());

                    myApply.setTitle(applyDomain.getTitle());
                    myApply.setDescription(applyDomain.getDescription());

                    myApplies.add(myApply);
                }
            }
            long total = applyService.count();
            float nrOfPages = (float) total / sizeNo;
            model.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));

            model.addAttribute("applies", myApplies);
            model.addAttribute("pageNo", Integer.valueOf(page.intValue() + 1));
            model.addAttribute("sizeNo", size);
            return "admin/applies";
        } catch (Exception ex) {
            logger.error("Cannot get data." + ex.toString());
        }
        return "home/maintenance";
    }
}