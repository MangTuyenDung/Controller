package vn.mangtuyendung.form.validator.profile;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.profile.PersonEducationForm;

@Service
public class PersonEducationValidator
        implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return PersonEducationValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonEducationForm form = (PersonEducationForm) target;
        if (MyValidator.validateNullOrEmpty(form.getSchoolName())) {
            errors.rejectValue("schoolName", "profile.education.schoolnamenotnull");
        }
        if (MyValidator.validateNullOrEmpty(form.getSchoolFieldOfStudy())) {
            errors.rejectValue("schoolFieldOfStudy", "profile.education.schoolfieldnotnull");
        }
        if (form.getStartYear() > form.getEndYear()) {
            errors.rejectValue("startYear", "profile.education.startyeargreatthenendyear");
        }
    }
}