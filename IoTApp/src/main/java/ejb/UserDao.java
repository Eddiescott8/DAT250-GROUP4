package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.User;
import entities.IoTDevice;

@Stateless
public class UserDao {
	@PersistenceContext(unitName="Dat250IoTApp")
    private EntityManager em;
	
	public void persist(User user) {
		em.persist(user);
	}

    @SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		Query query = em.createQuery("SELECT u FROM AppUser u");
		List<User> users = new ArrayList<User>();
		users = query.getResultList();
		return users;
	}
    
    @SuppressWarnings("unchecked")
	public List<User> getUser(long id) {
		Query query = em.createQuery("SELECT u FROM AppUser u WHERE u.id = "+id);
		List<User> users = new ArrayList<User>();
		users = query.getResultList();
		return users;
	}
    
    //REVISE - query not correct 100%
    @SuppressWarnings("unchecked")
	public List<User> getRequestedFollows(IoTDevice device) {
		Query query = em.createQuery("SELECT u FROM RequestedFollows r, AppUser u WHERE "+device.getId()+" = r.id_device AND r.id_user = u.id");
		List<User> users = new ArrayList<User>();
		users = query.getResultList();
		return users;
	}
    
    //REVISE - query not correct 100%
    @SuppressWarnings("unchecked")
	public List<User> getDevicesFollowed(IoTDevice device) {
		Query query = em.createQuery("SELECT u FROM DevicesFollowed df, AppUser u WHERE df.ID_DEVICE = "+device.getId()+" AND u.ID = df.ID_USER");
		List<User> users = new ArrayList<User>();
		users = query.getResultList();
		return users;
	}
    
    public static void main(String [] args) {
    	UserDao dao = new UserDao();
		List<User> users = dao.getAllUsers();
		
		for(User u : users) {
			System.out.println(u.getUsername());
		}
	}
    
}
