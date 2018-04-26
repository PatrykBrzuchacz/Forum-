package Main.web.controller;

import static org.junit.Assert.assertEquals;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import Main.Validation.RegisterFormValidator;
import Main.model.User;

public class RegisterFormValidatorTest {
	
	

	 @Test public void cityIsNull() {
	        User User = new User();
	        User.setFirstName(null); // Already null, but only to be explicit here...
	        RegisterFormValidator registervalidator= new RegisterFormValidator();
	        Errors errors = new BeanPropertyBindingResult(User, "");
	        registervalidator.validate(User, errors);
	        assertEquals(
	                "Field error in object '' on field 'accountName': ",
	                errors.getFieldError("firstName").toString());
	    }
}