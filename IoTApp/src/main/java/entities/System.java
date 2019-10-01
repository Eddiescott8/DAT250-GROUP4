package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class System {
	private static System instance;
	private Set<User> users;
	private Set<IoTDevice> devices;
	
	private System() {
		this.users = new HashSet<>();
		this.devices = new HashSet<>();
	}
	
	public static System getInstance() {
		if (instance == null) {
			instance = new System();
		}
		return instance;
	}
	
	public Set<IoTDevice> searchDevice(String searched) {
		Set<IoTDevice> devices = new TreeSet<>();
		for(IoTDevice d : this.devices) {
			if(d.getName().contains(searched)) {
				devices.add(d);
			}
		}
		return devices;
	}
	
	public Set<IoTDevice> searchCategory(String searched) {
		Set<IoTDevice> devices = new TreeSet<>();
		for(IoTDevice d : this.devices) {
			for(String s : d.getCategories()) {
				if(s.equals(searched)) {
					devices.add(d);
					break;
				}
			}
		}
		return devices;
	}
	
	public Set<IoTDevice> getTopRatedDevices() {
		Set<IoTDevice> devices = new TreeSet<IoTDevice>(new Comparator<IoTDevice>() {

	        @Override
	        public int compare(IoTDevice dev1, IoTDevice dev2) {
	        	if(Double.compare(dev2.getAverageRating(), dev1.getAverageRating()) == 0) {
	        		return 0;
	        	}
	            return ((dev2.getAverageRating() - dev1.getAverageRating()) > 0) ? 1 : -1;
	        }
	    });
		
		devices.addAll(this.devices);
		return devices;
	}
	
	public Set<IoTDevice> getPopularDevices() {
		Set<IoTDevice> devices = new TreeSet<IoTDevice>(new Comparator<IoTDevice>() {

	        @Override
	        public int compare(IoTDevice dev1, IoTDevice dev2) {
	        	return dev2.getNumFollowers() - dev1.getNumFollowers();
	        }
	    });
		
		devices.addAll(this.devices);
		return devices;
	}
	
	public void register( ) {
		
	}
	
	public void login( ) {
		
	}
	
	public void logOut( ) {
		
	}
	
}