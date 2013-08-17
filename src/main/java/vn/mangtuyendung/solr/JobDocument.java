package vn.mangtuyendung.solr;

import java.util.Date;
import org.apache.solr.common.SolrInputDocument;
import vn.mangtuyendung.persistence.domain.JobDomain;

public class JobDocument extends SolrInputDocument {

    public void convertCrawlDomain(JobDomain job) {
        addDocument("id", job.getId());
        addDocument("title", job.getTitle());
        addDocument("url", job.getUrl());
        addDocument("domain", job.getDomain());
        addDocument("boost", job.getBoost());
        addDocument("content", job.getContent());

        addDocument("companyName", job.getCompanyName());
        addDocument("companyAddress", job.getCompanyAddress());
        addDocument("companyRange", job.getCompanyRange());
        addDocument("companyOverview", job.getCompanyOverview());

        addDocument("sourceCategory", job.getSourceCategory());
        addDocument("jobCategory", job.getJobCategory());
        addDocument("jobLocation", job.getJobLocation());
        addDocument("jobMemberLevel", job.getJobMemberLevel());

        addDocument("jobTimeWork", job.getJobTimeWork());
        addDocument("jobSalary", job.getJobSalary());
        addDocument("jobAge", job.getJobAge());
        addDocument("jobSex", job.getJobSex());
        addDocument("jobOverview", job.getJobOverview());

        addDocument("jobLanguage", job.getJobLanguage());
        addDocument("jobEducationLevel", job.getJobEducationLevel());
        addDocument("jobExperienceLevel", job.getJobExperienceLevel());
        addDocument("jobRequirement", job.getJobRequirement());

        addDocument("jobContactDetail", job.getJobContactDetail());
        addDocument("jobContactName", job.getJobContactName());
        addDocument("jobContactAddress", job.getJobContactAddress());
        addDocument("jobContactPerson", job.getJobContactPerson());
        addDocument("jobContactPhone", job.getJobContactPhone());
        addDocument("jobContactEmail", job.getJobContactEmail());

        addDocument("jobCreated", new Date());
        addDocument("jobExpired", job.getJobExpired());
        addDocument("expired", job.getExpired());
    }

    private void addDocument(String key, Object value) {
        if (value != null) {
            addField(key, value);
        }
    }
}