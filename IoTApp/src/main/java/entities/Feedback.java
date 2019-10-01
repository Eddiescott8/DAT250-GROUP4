package entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)  
public abstract class Feedback implements Serializable {
	private static final long serialVersionUID = 1L;
	private User author;
	private int idDevice;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	public Feedback(User author, int idDevice) {
		this.author = author;
		this.setIdDevice(idDevice);
	}
	
	public Feedback() {
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public abstract int isRating();
	
	public abstract int getRating();

	public int getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(int idDevice) {
		this.idDevice = idDevice;
	}
}

