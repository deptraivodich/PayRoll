package springapp.web.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springapp.web.model.Users;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean CheckUser(String username) {
        Session session = sessionFactory.openSession();
        try {
            // SỬA: Dùng "userName" (tên biến trong Java) thay vì "User_Name"
            List lstUser = session.createQuery("from Users where userName=:username")
                    .setParameter("username", username)
                    .list();
            return !lstUser.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean Login(String username, String password) {
        Session session = sessionFactory.openSession();
        try {
            // SỬA: Dùng "userName" và "password" cho khớp với file Users.java mới
            List lstUser = session.createQuery("from Users where userName=:username and password=:password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .list();
            return !lstUser.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public List<Users> listUser() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("from Users").list();
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }
}