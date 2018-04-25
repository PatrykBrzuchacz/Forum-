package Main.Validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import Main.model.Post;
import Main.model.Topic;
import Main.model.form.PostForm;
import Main.model.form.TopicForm;
import Main.service.TopicService;

@Component
public class TopicFormValidator implements Validator{
@Autowired
private TopicService topicService;

public boolean supports(Class<?> clazz) {
	
	return false;
}
public void validateTopic(Object target, Errors errors) {
	TopicForm topic = (TopicForm) target;
	
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "notEmpty");
	
	if(topic.getTitle().length() < 6 || topic.getTitle().length() > 20) 
		errors.rejectValue("title", "title.size");
	if(topicService.findByTitle(topic.getTitle()) != null) {
		errors.rejectValue("title", "title.duplication");
}	
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstPostContent", "notEmpty");
	if(topic.getFirstPostContent().length() < 8 || topic.getFirstPostContent().length() > 20) 
	errors.rejectValue("firstPostContent", "post.size");

}
	

	public void validatePost(Object target, Errors errors) {
		PostForm post = (PostForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "notEmpty");
		
		if(post.getContent().length() < 8|| post.getContent().length() > 100) 
			errors.rejectValue("post", "post.size");
	}
	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value,
			Class<?>... groups) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T unwrap(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ExecutableValidator forExecutables() {
		// TODO Auto-generated method stub
		return null;
	}

		}
	
