package vn.mangtuyendung.controller.crawl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.mangtuyendung.persistence.domain.CategoryMapDomain;
import vn.mangtuyendung.persistence.domain.CrawlAccountDomain;
import vn.mangtuyendung.persistence.domain.CrawlMemberDomain;
import vn.mangtuyendung.persistence.domain.JobMemberDomain;
import vn.mangtuyendung.persistence.domain.member.JobEducationDomain;
import vn.mangtuyendung.persistence.domain.member.JobPositionDomain;
import vn.mangtuyendung.service.CategoryMapService;
import vn.mangtuyendung.service.CrawlAccountService;
import vn.mangtuyendung.service.CrawlMemberService;
import vn.mangtuyendung.service.member.JobEducationService;
import vn.mangtuyendung.service.member.JobPositionService;
import vn.mangtuyendung.solr.Education;
import vn.mangtuyendung.solr.Experience;
import vn.mangtuyendung.solr.MemberDocument;

@RequestMapping({"/crawlmember"})
@Controller
public class CrawlMemberController {

    private static Logger logger = LoggerFactory.getLogger(CrawlMemberController.class);
    @Autowired
    CrawlMemberService service;
    @Autowired
    CategoryMapService mapService;
    @Autowired
    ShaPasswordEncoder passwordEncoder;
    @Autowired
    CrawlAccountService accountService;
    @Autowired
    JobEducationService educationService;
    @Autowired
    JobPositionService positionService;

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public String post(CrawlMemberDomain member) {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        if ((member.getId() != 0L) && (member.getUrl() != null) && (member.getDomain() != null) && (member.getBoost() != 0.0F) && (member.getJobCategory() != null) && (member.getJobLocation() != null)) {
            try {
                logger.debug(new StringBuilder().append("Receiver post data from crawler ").append(new Timestamp(System.currentTimeMillis())).toString());
                member.setCreated(new Timestamp(System.currentTimeMillis()));
                member.setUpdated(new Timestamp(System.currentTimeMillis()));

                this.service.save(member);

                JobMemberDomain clone = clone(member);
                clone.setCreated(new Timestamp(System.currentTimeMillis()));

                long preferenceId = System.currentTimeMillis();

                CrawlAccountDomain account = new CrawlAccountDomain();
                account.setPreferenceId(preferenceId);
                account.setDomain(clone.getDomain());
                account.setEmail(clone.getPersonEmail());
                account.setPreferenceMobile(clone.getPersonMobile());

                this.accountService.save(account);

                clone.setProfileId(account.getId());

                List<JobEducationDomain> edus = clone.getEducations();
                for (JobEducationDomain jobEducationDomain : edus) {
                    jobEducationDomain.setProfileId(preferenceId);
                    this.educationService.save(jobEducationDomain);
                }

                List<JobPositionDomain> positions = clone.getPositions();
                for (JobPositionDomain jobPositionDomain : positions) {
                    jobPositionDomain.setProfileId(preferenceId);
                    this.positionService.save(jobPositionDomain);
                }

                MemberDocument doc = new MemberDocument();
                doc.convertCrawlDomain(clone);

                postDocument(doc);

                logger.debug(new StringBuilder().append("Store successful data crawled ").append(new Timestamp(System.currentTimeMillis())).toString());
                return "OK";
            } catch (SolrServerException ex) {
                sb.append(ExceptionUtils.getStackTrace(ex));
                logger.error(ex.toString());
            } catch (IOException ex) {
                sb.append(ExceptionUtils.getStackTrace(ex));
                logger.error(ex.toString());
            } catch (Exception ex) {
                sb.append(ExceptionUtils.getStackTrace(ex));
                logger.error(ex.toString());
            }
        }
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append(gson.toJson(member));
        return sb.toString();
    }

    private void postDocument(MemberDocument doc)
            throws SolrServerException, IOException {
        logger.trace("Post document to Solr Server.");

        SolrServer source = new HttpSolrServer("http://localhost:8982/solr");
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.add(doc);
        updateRequest.process(source);
        source.commit();
    }

    private JobMemberDomain clone(CrawlMemberDomain source) {
        source = filter(source);

        JobMemberDomain clone = new JobMemberDomain();
        clone.setId(source.getId());
        clone.setBoost(Float.valueOf(source.getBoost()));
        clone.setDomain(source.getDomain());
        clone.setUrl(source.getUrl());
        clone.setTitle(convertBase64(source.getJobTitle()));
        clone.setContent(convertBase64(source.getContent()));

        clone.setPersonAddress(convertBase64(source.getPersonAddress()));
        clone.setPersonBirthDay(convertBase64(source.getPersonBirthDay()));
        clone.setPersonEmail(convertBase64(source.getPersonEmail()));
        clone.setPersonMarried(convertBase64(source.getPersonMarried()));
        clone.setPersonMobile(convertBase64(source.getPersonMobile()));
        clone.setPersonName(convertBase64(source.getPersonName()));
        clone.setPersonSex(convertBase64(source.getPersonSex()));

        clone.setSourceCategory(source.getJobCategory());
        clone.setJobCategory(map(source.getJobCategory(), source.getDomain()));
        clone.setJobLocation(map(source.getJobLocation()));
        clone.setJobMemberLevel(source.getJobMemberLevel());
        clone.setJobSalaryDesired(convertBase64(source.getJobSalaryDesired()));
        clone.setJobSex(convertBase64(source.getJobSex()));
        clone.setJobSkills(convertBase64(source.getJobSkills()));
        clone.setJobWorkForm(convertBase64(source.getJobWorkForm()));

        clone.setEducations(mapEducation(source.getJobEducation()));
        clone.setPositions(mapPosition(source.getJobExperience()));

        clone.setSkillComunication(convertBase64(source.getSkillComunication()));
        clone.setSkillEducation(convertBase64(source.getSkillEducation()));
        clone.setSkillEnglish(convertBase64(source.getSkillEnglish()));
        clone.setSkillIT(convertBase64(source.getSkillIT()));
        return clone;
    }

    public CrawlMemberDomain filter(CrawlMemberDomain clone) {
        CrawlMemberDomain source = clone;
        logger.debug("Filter properties data crawled.");
        clone.setJobCategory(convertBase64(source.getJobCategory()));
        clone.setJobLocation(convertBase64(source.getJobLocation()));
        clone.setJobMemberLevel(convertBase64(source.getJobMemberLevel()));

        String tmp = clone.getJobLocation();
        if (tmp != null) {
            logger.debug(new StringBuilder().append("JobLocation:").append(tmp).toString());
            tmp = filter(tmp);
            tmp = tmp.replaceAll("TP\\. HCM", "Hồ Chí Minh");
            tmp = tmp.replaceAll("Lắc", "Lắk");
            tmp = tmp.replaceAll("Đắk", "Đắc");
            tmp = tmp.replaceAll("Đăk", "Đắc");
            tmp = tmp.replaceAll("Thừa Thiên-Huế", "Thừa Thiên Huế");
            tmp = tmp.replaceAll("Huế", "Thừa Thiên Huế");
            tmp = tmp.replaceAll("Thừa Thiên Thừa Thiên Huế", "Thừa Thiên Huế");
            tmp = tmp.replaceAll("Bà Rịa-Vũng Tàu", "Vũng Tàu");
            tmp = tmp.replaceAll("Bà Rịa - Vũng Tàu", "Vũng Tàu");
            tmp = tmp.replaceAll("Nước Ngoài", "Nước ngoài");
            tmp = tmp.replaceAll("Nước ngoài", "Quốc tế");
            tmp = filter(tmp);
            clone.setJobLocation(tmp);
        }

        tmp = clone.getJobCategory();
        if (clone.getJobCategory() != null) {
            logger.debug(new StringBuilder().append("JobCategory:").append(tmp).toString());
            tmp = tmp.replaceAll("/ ", "/");
            tmp = tmp.replaceAll(" /", "/");
            tmp = tmp.replaceAll("- ", "-");
            tmp = tmp.replaceAll(" -", "-");

            tmp = tmp.replaceAll("Xuất, nhập khẩu", "Xuất nhập khẩu");
            clone.setJobCategory(tmp);
        }
        return clone;
    }

    private String filter(String value) {
        value = value.replaceAll("\t", "");
        if (value.startsWith("-")) {
            value = value.replaceAll("-", "");
        }
        value = value.trim();
        return value;
    }

    private String convertBase64(String data) {
        if (data != null) {
            data = new String(Base64.decodeBase64(data));
            data = filter(data);
            return StringEscapeUtils.unescapeHtml(data);
        }
        return null;
    }

    private List<String> map(String _cat, String domain) {
        List values = new ArrayList();
        String[] tmp = _cat.split(",");
        for (String v : tmp) {
            String map = v.trim();
            logger.debug(new StringBuilder().append("Map:").append(map).append("-Domain:").append(domain).toString());
            CategoryMapDomain _map = this.mapService.findByCategoryAndDomain(map, domain);
            if (_map != null) {
                map = _map.getMap();
                String[] c = map.split("/");
                for (String cat : c) {
                    cat = cat.trim();
                    logger.debug(new StringBuilder().append("Category map:").append(cat).toString());
                    if (!values.contains(cat)) {
                        values.add(cat);
                    }
                }
            } else {
                logger.debug(new StringBuilder().append("Cannot find map:").append(map).toString());
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

    private List<JobEducationDomain> mapEducation(String value) {
        List<JobEducationDomain> values = new ArrayList();
        if (value != null) {
            Gson gson = new Gson();
            Type list = new TypeToken() {
            }.getType();

            Collection<Education> _value = (Collection) gson.fromJson(convertBase64(value), list);
            if (_value != null) {
                for (Education e : _value) {
                    JobEducationDomain domain = new JobEducationDomain();
                    String desc = e.getDescription();
                    if (desc != null) {
                        desc = desc.replaceAll("\t", "");
                    }
                    domain.setDescription(desc);
                    domain.setSchoolFieldOfStudy(e.getName());
                    domain.setSchoolName(e.getIndustry());
                    domain.setCreated(new Date());
                    domain.setTime(e.getTime());
                    values.add(domain);
                }
            }
        }
        return values;
    }

    private List<JobPositionDomain> mapPosition(String value) {
        List<JobPositionDomain> values = new ArrayList();
        if (value != null) {
            Gson gson = new Gson();
            Type list = new TypeToken() {
            }.getType();

            Collection<Experience> _value = (Collection) gson.fromJson(convertBase64(value), list);
            if (_value != null) {
                for (Experience e : _value) {
                    JobPositionDomain domain = new JobPositionDomain();
                    domain.setCompanyName(e.getCompany());
                    String desc = e.getDescription();
                    if (desc != null) {
                        desc = desc.replaceAll("\t", "");
                    }
                    domain.setDescription(desc);
                    domain.setTitle(e.getTitle());
                    domain.setTime(e.getTime());
                    values.add(domain);
                }
            }
        }
        return values;
    }
}