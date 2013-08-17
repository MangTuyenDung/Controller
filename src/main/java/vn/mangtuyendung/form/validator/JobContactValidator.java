package vn.mangtuyendung.form.validator;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.job.JobContactForm;

@Service
public class JobContactValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return JobContactValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JobContactForm form = (JobContactForm) target;
        if (MyValidator.validateNullOrEmpty(form.getJobContactName())) {
            errors.rejectValue("jobContactName", "job.create.contactnamenotnull");
        }
        if (MyValidator.validateNullOrEmpty(form.getJobContactEmail())) {
            errors.rejectValue("jobContactEmail", "job.create.contactemailnotnull");
        } else if (!MyValidator.validateEmail(form.getJobContactEmail())) {
            errors.rejectValue("jobContactEmail", "job.create.emailnotmatch");
        }
        if (MyValidator.validateNullOrEmpty(form.getJobContactPhone())) {
            errors.rejectValue("jobContactPhone", "job.create.contactphonenotnull");
        } else if (!MyValidator.validateMobile(form.getJobContactPhone())) {
            errors.rejectValue("jobContactPhone", "job.create.mobileformat");
        }
    }
}
