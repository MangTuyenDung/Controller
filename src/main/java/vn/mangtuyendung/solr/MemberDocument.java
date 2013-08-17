package vn.mangtuyendung.solr;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.apache.solr.common.SolrInputDocument;
import vn.mangtuyendung.persistence.domain.JobMemberDomain;
import vn.mangtuyendung.persistence.domain.member.JobEducationDomain;
import vn.mangtuyendung.persistence.domain.member.JobPositionDomain;

public class MemberDocument extends SolrInputDocument {

    public void convertCrawlDomain(JobMemberDomain member) {
        addDocument("id", Long.valueOf(member.getId()));
        addDocument("title", member.getTitle());
        addDocument("url", member.getUrl());
        addDocument("domain", member.getDomain());
        addDocument("boost", member.getBoost());
        addDocument("content", member.getContent());
        addDocument("profileId", Long.valueOf(member.getProfileId()));

        addDocument("personAddress", member.getPersonAddress());
        addDocument("personBirthDay", member.getPersonBirthDay());
        addDocument("personEmail", member.getPersonEmail());
        addDocument("personMarried", member.getPersonMarried());
        addDocument("personMobile", member.getPersonMobile());
        addDocument("personName", member.getPersonName());
        addDocument("personSex", member.getPersonSex());

        addDocument("sourceCategory", member.getSourceCategory());
        addDocument("jobCategory", member.getJobCategory());
        addDocument("jobLocation", member.getJobLocation());
        addDocument("jobMemberLevel", member.getJobMemberLevel());
        addDocument("jobTitle", member.getJobTitle());
        addDocument("jobDesired", member.getJobDesired());
        addDocument("jobSalaryCurrent", member.getJobSalaryCurrent());
        addDocument("jobSalaryDesired", member.getJobSalaryDesired());
        addDocument("jobSkills", member.getJobSkills());
        addDocument("jobWorkForm", member.getJobWorkForm());

        addEducation("jobEducation", member.getEducations());
        addPosition("jobExperience", member.getPositions());

        addDocument("skillComunication", member.getSkillComunication());
        addDocument("skillEducation", member.getSkillEducation());
        addDocument("skillEnglish", member.getSkillEnglish());
        addDocument("skillIT", member.getSkillIT());
    }

    private void addDocument(String key, Object value) {
        if (value != null) {
            addField(key, value);
        }
    }

    private void addEducation(String key, List<JobEducationDomain> domains) {
        if (domains != null) {
            List values = new ArrayList();
            for (JobEducationDomain jobEducationDomain : domains) {
                Gson gson = new Gson();
                values.add(gson.toJson(jobEducationDomain));
            }
            addField(key, values);
        }
    }

    private void addPosition(String key, List<JobPositionDomain> domains) {
        if (domains != null) {
            List values = new ArrayList();
            for (JobPositionDomain jobPositionDomain : domains) {
                Gson gson = new Gson();
                values.add(gson.toJson(jobPositionDomain));
            }
            addField(key, values);
        }
    }
}