package vn.mangtuyendung.controller.crawl;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.mangtuyendung.persistence.domain.CategoryMapDomain;
import vn.mangtuyendung.persistence.domain.CrawlDomain;
import vn.mangtuyendung.persistence.domain.JobDomain;
import vn.mangtuyendung.service.CategoryMapService;
import vn.mangtuyendung.service.CrawlService;
import vn.mangtuyendung.service.JobService;
import vn.mangtuyendung.solr.JobDocument;

@RequestMapping({"/crawl"})
@Controller
public class CrawlController {

    private static Logger logger = LoggerFactory.getLogger(CrawlController.class);
    @Autowired
    CrawlService service;
    @Autowired
    JobService jobService;
    @Autowired
    CategoryMapService mapService;

    private Timestamp convertTime(String time) {
        if ((time == null) || ((time != null) && (time.isEmpty()))) {
            return null;
        }
        time = time.replaceAll("[^0-9]+", "-");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return new Timestamp(fromUser.parse(time.trim()).getTime());
        } catch (Exception ex) {
        }
        return null;
    }

    private String convertBase64(String data) {
        if (data != null) {
            data = new String(Base64.decodeBase64(data));
            return StringEscapeUtils.unescapeHtml(data);
        }
        return null;
    }

    private JobDomain clone(CrawlDomain source) {
        source = filter(source);

        JobDomain clone = new JobDomain();
        clone.setId(Long.valueOf(source.getId()));
        clone.setBoost(Float.valueOf(source.getBoost()));
        clone.setDomain(source.getDomain());
        clone.setUrl(source.getUrl());
        clone.setExpired(source.getExpired());
        clone.setJobContactEmail(source.getJobContactEmail());

        clone.setTitle(convertBase64(source.getTitle()));
        clone.setContent(convertBase64(source.getContent()));

        clone.setCompanyName(convertBase64(source.getCompanyName()));
        clone.setCompanyOverview(convertBase64(source.getCompanyOverview()));
        clone.setCompanyAddress(convertBase64(source.getCompanyAddress()));
        clone.setCompanyRange(convertBase64(source.getCompanyRange()));

        clone.setSourceCategory(source.getJobCategory());
        clone.setJobCategory(map(source.getJobCategory(), source.getDomain()));
        clone.setJobLocation(map(source.getJobLocation()));
        clone.setJobMemberLevel(map(source.getJobMemberLevel()));
        clone.setJobTimeWork(source.getJobTimeWork());

        clone.setJobSalary(convertBase64(source.getJobSalary()));
        clone.setJobAge(convertBase64(source.getJobAge()));
        clone.setJobSex(convertBase64(source.getJobSex()));
        clone.setJobOverview(convertBase64(source.getJobOverview()));

        clone.setJobEducationLevel(source.getJobEducationLevel());
        clone.setJobExperienceLevel(convertBase64(source.getJobExperienceLevel()));
        clone.setJobRequirement(convertBase64(source.getJobRequirement()));

        clone.setJobLanguage(convertBase64(source.getJobLanguage()));
        clone.setJobContactDetail(convertBase64(source.getJobContactDetail()));
        clone.setJobContactName(convertBase64(source.getJobContactName()));
        clone.setJobContactAddress(convertBase64(source.getJobContactAddress()));
        clone.setJobContactPerson(convertBase64(source.getJobContactPerson()));
        clone.setJobContactPhone(convertBase64(source.getJobContactPhone()));
        clone.setJobExpired(source.getJobExpired());
        return clone;
    }

    private List<String> map(String _cat, String domain) {
        List values = new ArrayList();
        String[] tmp = _cat.split(",");
        for (String v : tmp) {
            String map = v.trim();
            System.out.println("Map:" + map + "-Domain:" + domain);
            CategoryMapDomain _map = this.mapService.findByCategoryAndDomain(map, domain);
            if (_map != null) {
                map = _map.getMap();
                String[] c = map.split("/");
                for (String cat : c) {
                    cat = cat.trim();
                    System.out.println("Category map:" + cat);
                    if (!values.contains(cat)) {
                        values.add(cat);
                    }
                }
            } else {
                System.out.println("Cannot find map:" + map);
            }
        }
        return values;
    }

    private List<String> map(String _cat) {
        List values = new ArrayList();
        String[] tmp = _cat.split(",");
        for (String v : tmp) {
            v = v.trim();
            if (!values.contains(v)) {
                values.add(v);
            }
        }
        return values;
    }

    public CrawlDomain filter(CrawlDomain clone) {
        CrawlDomain source = clone;
        logger.debug("Filter properties data crawled.");
        clone.setJobCategory(convertBase64(source.getJobCategory()));
        clone.setJobLocation(convertBase64(source.getJobLocation()));
        clone.setJobMemberLevel(convertBase64(source.getJobMemberLevel()));
        clone.setJobEducationLevel(convertBase64(source.getJobEducationLevel()));
        clone.setJobTimeWork(convertBase64(source.getJobTimeWork()));

        String tmp = clone.getJobLocation();
        if (tmp != null) {
            System.out.println("JobLocation:" + tmp);
            tmp = tmp.trim();
            tmp = tmp.replaceAll("TP\\. HCM", "Hồ Chí Minh");
            tmp = tmp.replaceAll("Lắc", "Lắk");
            tmp = tmp.replaceAll("Đắk", "Đắc");
            tmp = tmp.replaceAll("Đăk", "Đắc");
            tmp = tmp.replaceAll("Thừa Thiên-Huế", "Thừa Thiên Huế");
            tmp = tmp.replaceAll("Huế", "Thừa Thiên Huế");
            tmp = tmp.replaceAll("Thừa Thiên Thừa Thiên Huế", "Thừa Thiên Huế");
            tmp = tmp.replaceAll("Thừa Thiên - Thừa Thiên Huế", "Thừa Thiên Huế");
            tmp = tmp.replaceAll("Bà Rịa-Vũng Tàu", "Vũng Tàu");
            tmp = tmp.replaceAll("Bà Rịa - Vũng Tàu", "Vũng Tàu");
            tmp = tmp.replaceAll("Nước Ngoài", "Nước ngoài");
            tmp = tmp.replaceAll("Nước ngoài", "Quốc tế");
            tmp = tmp.trim();
            clone.setJobLocation(tmp);
        }

        tmp = clone.getJobEducationLevel();
        if (tmp != null) {
            System.out.println("JobEducation:" + tmp);
            tmp = tmp.replaceAll("Không khai báo", "Không yêu cầu");
            tmp = tmp.replaceAll("Khác", "Không yêu cầu");
            tmp = tmp.replaceAll("PTCS", "Trung học");
            tmp = tmp.replaceAll("Kỹ sư", "Đại học");
            tmp = tmp.replaceAll("Cử nhân", "Đại học");
            tmp = tmp.replaceAll("Thạc sĩ", "Trên đại học");
            tmp = tmp.replaceAll("Cao học", "Trên đại học");
            tmp = tmp.replaceAll("Trên đại học QTKD", "Trên đại học");

            clone.setJobEducationLevel(tmp);
        }

        tmp = clone.getJobExperienceLevel();
        if (tmp != null) {
            tmp = tmp.trim();
            System.out.println("JobExperienceLevel:" + tmp);
            if ("1 năm".equals(tmp) || "Chưa có kinh nghiệm".equals(tmp) || "Dưới 1 năm".equals(tmp)) {
                tmp = "0-1 năm kinh nghiệm";
            } else if ("2 năm".equals(tmp)) {
                tmp = "1-2 năm kinh nghiệm";
            } else if ("3 năm".equals(tmp) || "4 năm".equals(tmp) || "5 năm".equals(tmp)) {
                tmp = "2-5 năm kinh nghiệm";
            } else if ("Hơn 5 năm".equals(tmp) || "Trên 5 năm".equals(tmp) || "Hơn 10 năm kinh nghiệm".equals(tmp)) {
                tmp = "5-10 năm kinh nghiệm";
            }
            clone.setJobExperienceLevel(tmp);
        }

        if (clone.getJobTimeWork() != null) {
            tmp = clone.getJobTimeWork().replaceAll("- ", "").trim();
            clone.setJobTimeWork(tmp);
        }

        tmp = clone.getJobCategory();
        if (clone.getJobCategory() != null) {
            System.out.println("JobCategory:" + tmp);
            tmp = tmp.replaceAll("/ ", "/");
            tmp = tmp.replaceAll(" /", "/");
            tmp = tmp.replaceAll("- ", "-");
            tmp = tmp.replaceAll(" -", "-");

            tmp = tmp.replaceAll("Xuất, nhập khẩu", "Xuất nhập khẩu");
            clone.setJobCategory(tmp);
        }
        return clone;
    }

    private void postDocument(JobDocument doc)
            throws SolrServerException, IOException {
        logger.trace("Post document to Solr Server.");

        SolrServer source = new HttpSolrServer("http://mangtuyendung.vn:8983/solr");
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.add(doc);
        updateRequest.process(source);
        source.commit();
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public String post(CrawlDomain crawl) {
        if ((crawl.getId() != 0L) && (crawl.getTitle() != null) && (crawl.getUrl() != null) && (crawl.getDomain() != null) && (crawl.getCompanyName() != null) && (crawl.getJobCategory() != null) && (crawl.getJobLocation() != null) && (crawl.getJobOverview() != null) && (crawl.getJobRequirement() != null)) {
            try {
                logger.debug("Receiver post data from crawler " + new Timestamp(System.currentTimeMillis()));
                crawl.setExpired(convertTime(crawl.getJobExpired()));

                //Save data to database
                //this.service.save(crawl);

                JobDomain clone = clone(crawl);
                clone.setCreated(new Timestamp(System.currentTimeMillis()));

                JobDocument doc = new JobDocument();
                doc.convertCrawlDomain(clone);

                postDocument(doc);

                logger.debug("Store successful data crawled " + new Timestamp(System.currentTimeMillis()));
                return "OK";
            } catch (SolrServerException ex) {
                logger.error(ex.toString());
            } catch (IOException ex) {
                logger.error(ex.toString());
            } catch (Exception ex) {
                logger.error(ex.toString());
            }
        }
        Gson gson = new Gson();
        return gson.toJson(crawl);
    }

    @RequestMapping(value = {"/{id}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String index(@PathVariable("id") Long id, Model uiModel) {
        CrawlDomain item = this.service.findById(id.longValue());
        uiModel.addAttribute("crawl", item);
        return "crawl/index";
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String list(@RequestParam(value = "domain", required = false) String domain, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Collection<CrawlDomain> items;
        int sizeNo;
        if ((page != null) || (size != null)) {
            sizeNo = size == null ? 10 : size.intValue();
            items = this.service.findBy(domain, page == null ? 0 : page.intValue() - 1, sizeNo);
        } else {
            sizeNo = 30;
            items = this.service.findBy(domain, 0, sizeNo);
        }
        uiModel.addAttribute("requests", items);
        float nrOfPages = (float) this.service.count(domain) / sizeNo;
        uiModel.addAttribute("maxPages", (int) Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages));
        
        return "crawl/list";
    }
}