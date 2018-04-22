package Main.model.form;

import java.util.Date;

import org.springframework.stereotype.Component;

import Main.model.Topic;


@Component
public class TopicForm {
	private String title;
	private String firstPostContent;
	
	
	

	@Override
	public String toString() {
		return "topicForm [title=" + title + ", firstPostContent=" + firstPostContent + "]";
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstPostContent() {
		return firstPostContent;
	}
	public void setFirstPostContent(String firstPostContent) {
		this.firstPostContent = firstPostContent;
	}
	public Topic createTopica() {
		Topic topic = new Topic();
			topic.setTitle(this.title);
		
			
		
		return topic;
	}
	
	
	
	
}

