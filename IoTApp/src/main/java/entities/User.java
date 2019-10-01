package entities;

import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Table(name="appuser")
//@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	//Create elements ids automatically, incremented 1 by 1
	/*@TableGenerator(
			  name = "yourTableGenerator",
			  allocationSize = 1,
			  initialValue = 1)*/
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String username;
	private String email;
	private String password;
	
	
	@ManyToMany
	@JoinTable(
		name = "devicesFollowed",
		joinColumns = @JoinColumn(name = "id_appuser"),
		inverseJoinColumns = @JoinColumn(name = "id_device")
			)
	private Set<IoTDevice> devicesFollowed;
	
	@OneToMany
	@JoinTable(
			name = "devicesOwned",
			joinColumns = @JoinColumn(name = "id_appuser"),
			inverseJoinColumns = @JoinColumn(name = "id_device")
				)
	private Set<IoTDevice> devicesOwned;
	
	public User() {
	}
	
	public User(String u, String e, String p) {
		username = u;
		email = e;
		password = p;
		devicesFollowed = new TreeSet<IoTDevice>();
		devicesOwned = new TreeSet<IoTDevice>();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public boolean followDevice(IoTDevice device) {
		if (devicesFollowed.contains(device)) {
			return false;
		}
		else {
			return device.addRequestedFollow(this);
		}
	}
	
	public boolean unfollowDevice(IoTDevice device) {
		if(devicesFollowed.contains(device)) {
			devicesFollowed.remove(device);
			device.decNumFollowers();
			return true;
		}
		else if(device.getRequestedFollows().contains(this)) {
			device.getRequestedFollows().remove(this);
			device.decNumFollowers();
			return true;
		}
		return false;
	}
	
	public boolean addDevice(String n, String url, String p, String d, String ... c) {
		IoTDevice device = new IoTDevice(n, url, p, d, c);
		return devicesOwned.add(device);
	}

	public boolean addDevice(IoTDevice device) {
		return devicesOwned.add(device);
	}
	
	public boolean deleteDevice(IoTDevice device) {
		return devicesOwned.remove(device);
	}
	
	public void adminFollowRequest(IoTDevice device, User user, boolean accept) {
		if(devicesOwned.contains(device)) {
			for(User aux: device.getRequestedFollows()) {
				if(aux.equals(user)) {
					device.removeRequestedFollow(user);
					if(accept) {
						user.devicesFollowed.add(device);
						device.incNumFollowers();
					}
					else {
						device.removeRequestedFollow(this);
					}
					return;
				}
			}
		}
	}
	
	public boolean equals(User user) {
		if(this.username == user.getUsername()) {
			return true;
		}	
		return false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<IoTDevice> getDevicesFollowed() {
		return devicesFollowed;
	}

	public void setDevicesFollowed(Set<IoTDevice> devicesFollowed) {
		this.devicesFollowed = devicesFollowed;
	}

	public Set<IoTDevice> getDevicesOwned() {
		return devicesOwned;
	}

	public void setDevicesOwned(Set<IoTDevice> devicesOwned) {
		this.devicesOwned = devicesOwned;
	}
	
	
}
