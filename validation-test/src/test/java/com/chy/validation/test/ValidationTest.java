package com.chy.validation.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;

import org.junit.Before;
import org.junit.Test;

import com.chy.validation.MyAnnotation;
import com.chy.validation.MyValidator;

public class ValidationTest {
	private Validator validator;

	private static class Sample {
		@MyAnnotation(min=10, upperCase=true)
		String value;
		
		@Email
		String email;

		public void setValue(String value) {
			this.value = value;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
	}

	@Before
	public void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void fieldValidation() {
		Sample sample = new Sample();

		Set<ConstraintViolation<Sample>> violations = validator.validate(sample);
		assertFalse(violations.isEmpty());

		// less than 10
		sample.setValue("short");
		violations = validator.validate(sample);
		assertFalse(violations.isEmpty());

		// more than 20
		sample.setValue("too long string value");
		violations = validator.validate(sample);
		assertFalse(violations.isEmpty());

		// lower case
		sample.setValue("lower case word");
		violations = validator.validate(sample);
		assertFalse(violations.isEmpty());

		sample.setValue("THIS WILL PASS");
		violations = validator.validate(sample);
		assertTrue(violations.isEmpty());
	}
	
	@Test
	public void multipleValidation() {
		Sample sample = new Sample();
		sample.setEmail("Not email");
		sample.setValue("THIS WILL PASS");
		
		Set<ConstraintViolation<Sample>> violations = validator.validate(sample);
		
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void singleValidation() {
		MyAnnotation annotation = new MyAnnotation() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
			
			@Override
			public Class<?>[] groups() {
				return null;
			}
			
			@Override
			public String message() {
				return null;
			}
			
			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}
			
			@Override
			public boolean upperCase() {
				return true;
			}
			
			@Override
			public int min() {
				return 10;
			}
			
			@Override
			public int max() {
				return 20;
			}
		};
		
		MyValidator validator = new MyValidator();
		validator.initialize(annotation);
		
		assertFalse(validator.isValid("short", null));
		assertFalse(validator.isValid("too long string value", null));
		assertFalse(validator.isValid("lower case word", null));
		assertTrue(validator.isValid("THIS WILL PASS", null));
	}
}
