package vn.mangtuyendung.form.validator.profile;

import com.hadoopvietnam.common.MyValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.mangtuyendung.form.profile.PersonSkillForm;

@Service
public class PersonSkillValidator
        implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return PersonSkillValidator.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonSkillForm form = (PersonSkillForm) target;
        if (MyValidator.validateNullOrEmpty(form.getSkillName())) {
            errors.rejectValue("skillName", "profile.skill.skillnotnull");
        }
    }
}