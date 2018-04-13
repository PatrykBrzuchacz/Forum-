package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoIMPL implements UserDao {
/*private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	} 
	public void create(User user) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		currentSession.save(user);
		
	}

	public void update(User user) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		currentSession.update(user);
		
	}
	
	public User edit(int id) {
		return find(id);
	}


	public void delete(int id) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		currentSession.delete(id);
		
	}
	public User find(int id) {
	return find(id);
	}
*/
}
