package vn.mangtuyendung.form.validator;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.job.JobLocationForm;

@Service
public class JobLocationValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return JobLocationValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JobLocationForm form = (JobLocationForm) target;
        if (MyValidator.validateNullOrEmpty(form.getJobLocations())) {
            errors.rejectValue("jobLocations", "job.create.locationnotnull");
        }
    }
}
