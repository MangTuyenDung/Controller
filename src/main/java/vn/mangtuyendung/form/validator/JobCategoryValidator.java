package vn.mangtuyendung.form.validator;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.job.JobCategoryForm;

@Service
public class JobCategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return JobCategoryValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JobCategoryForm form = (JobCategoryForm) target;
        if (MyValidator.validateNullOrEmpty(form.getJobCategories())) {
            errors.rejectValue("jobCategories", "job.create.categorynotnull");
        }
    }
}
