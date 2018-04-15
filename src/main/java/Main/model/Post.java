package Main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Post {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String content;
@ManyToOne(optional=false)
private User author;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public User getAuthor() {
	return author;
}
public void setAuthor(User author) {
	this.author = author;
}
}
