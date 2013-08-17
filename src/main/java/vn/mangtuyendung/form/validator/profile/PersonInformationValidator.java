package vn.mangtuyendung.form.validator.profile;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.profile.PersonInformationForm;

@Service
public class PersonInformationValidator
        implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return PersonInformationValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonInformationForm form = (PersonInformationForm) target;
        if (MyValidator.validateNullOrEmpty(form.getPersonName())) {
            errors.rejectValue("personName", "profile.information.personnamenotnull");
        }
        if (MyValidator.validateNullOrEmpty(form.getPersonMobile())) {
            errors.rejectValue("personMobile", "profile.information.personmobilenotnull");
        } else if (!MyValidator.validateMobile(form.getPersonMobile())) {
            errors.rejectValue("personMobile", "profile.information.personmobilewrongformat");
        }
    }
}