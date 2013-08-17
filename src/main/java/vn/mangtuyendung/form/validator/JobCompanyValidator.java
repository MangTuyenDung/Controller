package vn.mangtuyendung.form.validator;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.job.JobCompanyForm;

@Service
public class JobCompanyValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return JobCompanyValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JobCompanyForm form = (JobCompanyForm) target;
        if (MyValidator.validateNullOrEmpty(form.getCompanyName())) {
            errors.rejectValue("companyName", "job.create.companynamenotnull");
        }
        if (MyValidator.validateNullOrEmpty(form.getCompanyAddress())) {
            errors.rejectValue("companyAddress", "job.create.companyaddressnotnull");
        }
        if (MyValidator.validateNullOrEmpty(form.getCompanyOverview())) {
            errors.rejectValue("companyOverview", "job.create.companyoverviewnotnull");
        }
    }
}
