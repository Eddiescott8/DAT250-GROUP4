package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.*;


/**
 * @Author Alvaro Sanchez Romero
 * 
 * The persistent class for the IOTDEVICE database table.
 * 
 */

@Entity
@Table(name="iotdevice")
@NamedQuery(name="IoTDevice.findAll", query="SELECT i FROM IoTDevice i")
public class IoTDevice implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String url;
	private String picture;
	private String description;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "Categories")
	@Column(name = "Name")
	private Set<String> categories;
	private Status status;
	
	private List<Feedback> feedbacks;
	
	@ManyToMany
	@JoinTable(
		name = "requestedFollows",
		joinColumns = @JoinColumn(name = "id_device"),
		inverseJoinColumns = @JoinColumn(name = "id_appuser")
			)
	private Set<User> requestedFollows;
	private int numFollowers;
	
	//Create elements ids automatically, incremented 1 by 1
	/*@TableGenerator(
			  name = "yourTableGenerator",
			  allocationSize = 1,
			  initialValue = 1)*/

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	public IoTDevice(String name, String url, String picture, String description, String ... categories) {
		this.name = name;
		this.url = url;
		this.picture = picture;
		this.description = description;
		this.categories = new TreeSet<>();
		this.addCategories(categories);
		this.status = Status.PUBLISHED;
		this.feedbacks = new ArrayList<>();
		this.categories = new HashSet<>();
		this.numFollowers = 0;
		this.requestedFollows = new HashSet<>();
	}
	
	public IoTDevice() {		
	}

	
	public int getNumFollowers() {
		return numFollowers;
	}


	public void incNumFollowers() {
		this.numFollowers++;
	}
	
	public void decNumFollowers() {
		this.numFollowers--;
	}

	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public Set<User> getRequestedFollows() {
		return requestedFollows;
	}

	public void setRequestedFollows(Set<User> requestedFollows) {
		this.requestedFollows = requestedFollows;
	}
	
	public boolean addRequestedFollow(User user) {
		return this.requestedFollows.add(user);
	}
	
	public boolean removeRequestedFollow(User user) {
		return this.requestedFollows.remove(user);
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<String> getCategories() {
		return categories;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void addCategories(String ... categories) {
		this.categories.addAll(Arrays.asList(categories));
	}
	
	public double getAverageRating() {
		double sum = 0;
		double ratingQuantity = 0;
		
		for(Feedback f : this.feedbacks) {
			sum += f.getRating();
			ratingQuantity += f.isRating();
		}
		return sum/ratingQuantity;
	}

}
