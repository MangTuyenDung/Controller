package vn.mangtuyendung.form.validator;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.SearchForm;

@Service
public class SearchValidator
        implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return SearchValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchForm form = (SearchForm) target;
        if (MyValidator.validateNullOrEmpty(form.getStext())) {
            errors.rejectValue("stext", "search.textnotnull");
        }
    }
}