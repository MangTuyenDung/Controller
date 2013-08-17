package vn.mangtuyendung.solr;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.mangtuyendung.persistence.domain.JobDomain;

public class SolrService {

    private Logger logger = LoggerFactory.getLogger(SolrService.class);
    private SolrServer source = new HttpSolrServer("http://mangtuyendung.vn:8983/solr");

    public QueryResponse search(String query, int start, int pageSize) throws SolrServerException {
        try {
            this.logger.info("Query:" + query + " documents from " + start + " to " + pageSize);
            SolrQuery solrQuery = new SolrQuery(query);
            solrQuery.setStart(Integer.valueOf(start));
            solrQuery.setRows(Integer.valueOf(pageSize));
            solrQuery.addSortField("score", SolrQuery.ORDER.desc);
            solrQuery.addSortField("id", SolrQuery.ORDER.desc);
            return this.source.query(solrQuery);
        } catch (SolrServerException e) {
            this.logger.error("Cannot query to source Server.");
            throw e;
        }
    }

    public void setSource(SolrServer source) {
        this.source = source;
    }

    public List<FacetField.Count> getCategory(QueryResponse response, String category) {
        FacetField field = response.getFacetField(category);
        return field.getValues();
    }

    public List<JobDomain> convertDocument(SolrDocumentList solrDocs) {
        List<JobDomain> jobs = new ArrayList();
        for (SolrDocument doc : solrDocs) {
            JobDomain domain = convertDocument(doc);
            jobs.add(domain);
        }
        return jobs;
    }

    public JobDomain convertDocument(SolrDocument doc) {
        JobDomain domain = new JobDomain();
        Object value = doc.getFieldValue("id");
        if (value != null) {
            domain.setId(Long.valueOf((String) doc.getFieldValue("id")));
        }
        value = doc.getFieldValue("title");
        if (value != null) {
            domain.setTitle((String) value);
        }

        value = doc.getFieldValue("companyAddress");
        if (value != null) {
            domain.setCompanyAddress((String) value);
        }
        value = doc.getFieldValue("companyName");
        if (value != null) {
            domain.setCompanyName((String) value);
        }
        value = doc.getFieldValue("companyOverview");
        if (value != null) {
            domain.setCompanyOverview((String) value);
        }
        value = doc.getFieldValue("companyRange");
        if (value != null) {
            domain.setCompanyRange((String) value);
        }

        value = doc.getFieldValue("url");
        if (value != null) {
            domain.setUrl((String) value);
        }
        value = doc.getFieldValue("content");
        if (value != null) {
            domain.setContent((String) value);
        }
        value = doc.getFieldValue("domain");
        if (value != null) {
            domain.setDomain((String) value);
        }
        value = doc.getFieldValue("signature");
        if (value != null) {
            domain.setSignature((String) value);
        }
        value = doc.getFieldValue("jobAge");
        if (value != null) {
            domain.setJobAge((String) value);
        }
        value = doc.getFieldValue("jobCategory");
        if (value != null) {
            domain.setJobCategory((List) value);
        }
        value = doc.getFieldValue("jobContactAddress");
        if (value != null) {
            domain.setJobContactAddress((String) value);
        }
        value = doc.getFieldValue("jobContactDetail");
        if (value != null) {
            domain.setJobContactDetail((String) value);
        }
        value = doc.getFieldValue("jobContactEmail");
        if (value != null) {
            domain.setJobContactEmail((String) value);
        }
        value = doc.getFieldValue("jobContactName");
        if (value != null) {
            domain.setJobContactName((String) value);
        }
        value = doc.getFieldValue("jobContactPerson");
        if (value != null) {
            domain.setJobContactPerson((String) value);
        }
        value = doc.getFieldValue("jobContactPhone");
        if (value != null) {
            domain.setJobContactPhone((String) value);
        }
        value = doc.getFieldValue("jobEducationLevel");
        if (value != null) {
            domain.setJobEducationLevel((String) value);
        }
        value = doc.getFieldValue("jobExperienceLevel");
        if (value != null) {
            domain.setJobExperienceLevel((String) value);
        }
        value = doc.getFieldValue("jobLanguage");
        if (value != null) {
            domain.setJobLanguage((String) value);
        }
        value = doc.getFieldValue("jobExpired");
        if (value != null) {
            domain.setJobExpired((String) value);
        }
        value = doc.getFieldValue("jobLocation");
        if (value != null) {
            domain.setJobLocation((List) value);
        }
        value = doc.getFieldValue("jobMemberLevel");
        if (value != null) {
            domain.setJobMemberLevel((List) value);
        }
        value = doc.getFieldValue("jobOverview");
        if (value != null) {
            domain.setJobOverview((String) value);
        }
        value = doc.getFieldValue("jobRequirement");
        if (value != null) {
            domain.setJobRequirement((String) value);
        }
        value = doc.getFieldValue("jobSalary");
        if (value != null) {
            domain.setJobSalary((String) value);
        }
        value = doc.getFieldValue("jobSex");
        if (value != null) {
            domain.setJobSex((String) value);
        }
        value = doc.getFieldValue("jobTimeWork");
        if (value != null) {
            domain.setJobTimeWork((String) value);
        }
        value = doc.getFieldValue("jobCreated");
        if (value != null) {
            domain.setCreated(convertTime(value.toString()));
        }
        value = doc.getFieldValue("jobUpdated");
        if (value != null) {
            domain.setUpdated(convertTime(value.toString()));
        }
        value = doc.getFieldValue("expired");
        if (value != null) {
            domain.setExpired(convertTime(value.toString()));
        }
        return domain;
    }

    private Timestamp convertTime(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            Date parsedDate = dateFormat.parse(time);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException ex) {
        }
        return null;
    }
}