package entities;

import javax.persistence.Entity;

@Entity
public class Comment extends Feedback{
	String comment;
	
	public Comment() {
	}
	
	public Comment(User author, String comment, int idDevice) {
		super(author, idDevice);
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public int isRating() {
		return 0;
	}
	
	@Override
	public int getRating() {
		return 0;
	}
}
