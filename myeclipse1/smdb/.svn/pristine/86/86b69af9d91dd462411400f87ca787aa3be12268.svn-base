package csdc.bean.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SystemOptionStandardValidator.class)
@Documented
public @interface CheckSystemOptionStandard {

	String message() default "{csdc.bean.SystemOption.checkStandard}";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String value();

}
