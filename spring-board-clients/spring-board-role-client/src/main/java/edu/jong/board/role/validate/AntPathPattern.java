package edu.jong.board.role.validate;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;

@Constraint(validatedBy = AntPathPattern.Validator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface AntPathPattern {

	String message() default "{javax.validation.constraints.NotBlank.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	public static class Validator implements ConstraintValidator<AntPathPattern, String> {

		private static final AntPathMatcher MATCHER = new AntPathMatcher();
		
		private boolean setValidMessage(ConstraintValidatorContext context, String message) {
			
	        //기본 메시지 비활성화
	        context.disableDefaultConstraintViolation();
	        //새로운 메시지 추가
	        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

	        return false;
		}
		
		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {

			if (StringUtils.isBlank(value)) return true;
			
			if (!value.startsWith("http")) 
				return setValidMessage(context, "Protocol이 존재하지 않습니다.");
				
			if (!MATCHER.isPattern(value)) 
				return setValidMessage(context, "Ant Path Pattern이 아닙니다.");
			
			return true;
		}
		
	}
	
}
