package Main.model.form;

import org.springframework.stereotype.Component;

import Main.model.Post;

@Component
public class PostForm {
private String content;

public Post createPost() {
	Post post = new Post();
	post.setContent(content);
	return post;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}
}
