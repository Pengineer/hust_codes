package csdc.bean.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import csdc.bean.SystemOption;

public class SystemOptionStandardValidator implements ConstraintValidator<CheckSystemOptionStandard, SystemOption> {

	private String systemOptionStandard;

	public void initialize(CheckSystemOptionStandard constraintAnnotation) {
		this.systemOptionStandard = constraintAnnotation.value();
	}

	public boolean isValid(SystemOption systemOption, ConstraintValidatorContext constraintContext) {
		if (systemOption == null) {
			return true;
		} else {
			return systemOption.getStandard().equals(systemOptionStandard);
		}
	}

}
