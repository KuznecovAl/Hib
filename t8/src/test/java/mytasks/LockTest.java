package mytasks;

import mytasks.entity.Address;
import mytasks.entity.NameAdressDTO;
import mytasks.entity.Order;
import mytasks.entity.User;
import mytasks.util.EMUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public class LockTest {
    @Before
    public void init() {
        EntityManager em = EMUtil.getEntityManager("mytasks-create");
        em.getTransaction().begin();
//        User user = new User(null, "Alex", "Kuznecov", "7515313@mail.ru", 34, null, new ArrayList<>(), null);
//        User user1 = new User(null, "Yuri", "Gagarin", "Yuri@mail.ru", 80, null, new ArrayList<>(), null);
//        User user2 = new User(null, "German", "Titov", "g_titov@mail.ru", 70, null, new ArrayList<>(), null);
//        User user3 = new User(null, "Lavrentiy", "Beriya", "beriya@mail.ru", 100, null, new ArrayList<>(), null);
//        User user4 = new User(null, "Yuri", "Dolgorukiy", "Yuri_D@mail.ru", 1001, null, new ArrayList<>(), null);
//        User user5 = new User(null, "Yuri", "Petrov", "Yuri_P@mail.com", 29, null, new ArrayList<>(), null);
//        User user6 = new User(null, null, "Sidorov", "sid@mail.com", 12, null, new ArrayList<>(), null);
//        User user7 = new User(null, null, "", "", 1, null, new ArrayList<>(), null);

        User user = new User(null,null, "Alex", "Kuznecov", "7515313@mail.ru", 34, null, new ArrayList<>(), null);
        User user1 = new User(null,null, "Yuri", "Gagarin", "Yuri@mail.ru", 80, null, new ArrayList<>(), null);
        User user2 = new User(null,null, "German", "Titov", "g_titov@mail.ru", 70, null, new ArrayList<>(), null);
        User user3 = new User(null,null, "Lavrentiy", "Beriya", "beriya@mail.ru", 100, null, new ArrayList<>(), null);
        User user4 = new User(null,null, "Yuri", "Dolgorukiy", "Yuri_D@mail.ru", 1001, null, new ArrayList<>(), null);
        User user5 = new User(null,null, "Yuri", "Petrov", "Yuri_P@mail.com", 29, null, new ArrayList<>(), null);
        User user6 = new User(null,null, null, "Sidorov", "sid@mail.com", 12, null, new ArrayList<>(), null);
        User user7 = new User(null,null, null, "", "", 1, null, new ArrayList<>(), null);

        Address address = new Address(null, "Germanskaya", "Minsk", "", "Belarus", user);
        Address address1 = new Address(null, "Mira", "Moskva", "", "Russia", user1);
        Address address2 = new Address(null, "Stupki", "Kiev", "", "Ukraina", user2);
        Address address3 = new Address(null, "Shvili", "Tiflis", "", "Georgia", user3);
        Address address4 = new Address(null, "Kings", "Gorod-sad", "", "Utopia", user4);
        Address address5 = new Address(null, "Avenu", "Paris", "", "France", user5);
        Address address6 = new Address(null, "redstreet", "Minsk", "", "Belarus", user6);

        Order order = new Order(null, 26.45, "Molotok", 5, user);
        Order order1 = new Order(null, 126.45, "Perforator", 1, user1);
        Order order2 = new Order(null, 2.45, "otvertka", 1, user2);
        Order order3 = new Order(null, 5D, "gvozdi", 100, user);
        Order order4 = new Order(null, 5D, "gvozdi", 50, user3);
        Order order5 = new Order(null, 5D, "gvozdi", 5098, user4);
        Order order6 = new Order(null, 12.95, "kleschi", 9, user6);

        em.persist(user);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
        em.persist(user5);
        em.persist(user6);
        em.persist(user7);

        user.setAddress(address);
        user1.setAddress(address1);
        user2.setAddress(address2);
        user3.setAddress(address3);
        user4.setAddress(address4);
        user5.setAddress(address5);
        user6.setAddress(address6);

        user.getOrrders().add(order);
        user1.getOrrders().add(order1);
        user2.getOrrders().add(order2);
        user.getOrrders().add(order3);
        user3.getOrrders().add(order4);
        user4.getOrrders().add(order5);
        user6.getOrrders().add(order6);

        em.getTransaction().commit();
        em.close();
     }

    @Test
    public void noLockTest() {
        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, 3L);
        u.setFirstName("но лок тест");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void optimisticLockAllTest() {
        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, 4L);
        u.setFirstName("оп лок алл тест");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void optimisticLockModeDirtyTest() {
        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();
      User u = em.find(User.class, 1L);
        u.setFirstName("лок дирти тест");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void optimisticLockDirtyTest() {
        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, 1L);
        u.setLastName("дирти оптимистик");
        em.getTransaction().commit();
        em.close();
    }


    @Test
    public void optimisticLockVersionTest() {
        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, 1L);
        u.setLastName("версион");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void optimisticLockModeVersionTest() {
        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, 1L, LockModeType.OPTIMISTIC);
        u.setLastName("версион");
        em.getTransaction().commit();
        em.close();
    }

    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }
}
