package vn.mangtuyendung.form.validator.profile;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.profile.PersonPositionForm;

@Service
public class PersonPositionValidator
        implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return PersonPositionValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonPositionForm form = (PersonPositionForm) target;
        if (MyValidator.validateNullOrEmpty(form.getTitle())) {
            errors.rejectValue("title", "profile.position.titlenotnull");
        }
        if (MyValidator.validateNullOrEmpty(form.getCompanyName())) {
            errors.rejectValue("companyName", "profile.position.companynamenotnull");
        }
        if (MyValidator.validateNullOrEmpty(form.getCompanyName())) {
            errors.rejectValue("description", "profile.position.descriptionnotnull");
        }
        if ((MyValidator.validateNullOrEmpty(form.getStartDateYear())) || (MyValidator.validateNullOrEmpty(form.getEndDateYear()))) {
            errors.rejectValue("currentHere", "profile.position.startdateyearnotnull");
        } else if ((!MyValidator.validateNumberInt(form.getStartDateYear())) || (!MyValidator.validateNumberInt(form.getEndDateYear()))) {
            errors.rejectValue("currentHere", "profile.position.startdateyearnotnumber");
        } else {
            int startYear = Integer.valueOf(form.getStartDateYear()).intValue();
            int endYear = Integer.valueOf(form.getEndDateYear()).intValue();
            if (startYear > endYear) {
                errors.rejectValue("currentHere", "profile.position.startyeargreatthanendyear");
            }
        }
    }
}