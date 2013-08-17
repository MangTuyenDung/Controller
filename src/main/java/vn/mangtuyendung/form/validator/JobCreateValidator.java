package vn.mangtuyendung.form.validator;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.job.JobCreateForm;

@Service
public class JobCreateValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return JobCreateValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JobCreateForm form = (JobCreateForm) target;
        if (MyValidator.validateNullOrEmpty(form.getJobTitle())) {
            errors.rejectValue("jobTitle", "job.create.titlenotnull");
        }
        if (MyValidator.validateNullOrEmpty(form.getJobOverview())) {
            errors.rejectValue("jobOverview", "job.create.overviewnotnull");
        }
        if (MyValidator.validateNullOrEmpty(form.getJobRequirement())) {
            errors.rejectValue("jobRequirement", "job.create.requirementnotnull");
        }
    }
}
