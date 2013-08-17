package vn.mangtuyendung.form.validator;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.ApplyForm;

@Service
public class ApplyValidator
        implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return ApplyValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ApplyForm form = (ApplyForm) target;
        if (MyValidator.validateNullOrEmpty(form.getTitle())) {
            errors.rejectValue("title", "job.apply.titlenotnull");
        }
    }
}