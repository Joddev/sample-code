package com.chy.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MyValidator implements ConstraintValidator<MyAnnotation, String> {
    private boolean upperCase;
    private int min;
    private int max;
    
	@Override
	public void initialize(MyAnnotation constraintAnnotation) {
		this.upperCase = constraintAnnotation.upperCase();
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null)
			return false;
		int length = value.length();
		
		if(length < min || length > max)
			return false;
		
		if(upperCase && !value.toUpperCase().equals(value))
			return false;
		
		return true;
	}

}
