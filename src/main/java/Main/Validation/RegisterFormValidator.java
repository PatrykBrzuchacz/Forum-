package Main.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import Main.model.User;
import Main.service.UserService;


@Component
public class RegisterFormValidator implements Validator {
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "notEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "notEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "notEmpty");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNumber", "notEmpty");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nationality", "notEmpty");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "notEmpty");
		if(user.getEmail().length() < 6 || user.getEmail().length() > 20) 
			errors.rejectValue("email", "email.size");
		
		//Tutaj warunek że username nie wystepuje w bazie danych

			if(userService.findByEmail(user.getEmail()) != null) {
				errors.rejectValue("email", "email.duplication");
				
				if(!user.getEmail().contains("@"))
					errors.rejectValue("email", "email.monkey");
			} 
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "notEmpty");
			
			if(user.getPassword().length() < 5 || user.getPassword().length() > 24)
				errors.rejectValue("password", "password.size");
	}}
	