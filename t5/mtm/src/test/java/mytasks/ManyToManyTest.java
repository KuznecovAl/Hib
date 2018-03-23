package mytasks;

import mytasks.entity.Address;
import mytasks.entity.Order;
import mytasks.entity.User;
import mytasks.util.EMUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;


public class ManyToManyTest {
    @Before
    public void init() {
        EntityManager em = EMUtil.getEntityManager("mytasks-create");
        em.getTransaction().begin();
        User user = new User(null, "Alex", "Kuznecov", "7515313@mail.ru", null, new ArrayList<>(), null);
        User user1 = new User(null, "Yuri", "Gagarin", "Yuri@mail.ru", null, new ArrayList<>(), null);
        User user2 = new User(null, "German", "Titov", "g_titov@mail.ru", null, new ArrayList<>(), null);

        Address address = new Address(null, "Germanskaya", "Minsk", "", "Belarus", user);
        Address address1 = new Address(null, "Mira", "Moskva", "", "Russia", user1);
        Address address2 = new Address(null, "Stupki", "Kiev", "", "Ukraina", user2);

        Order order = new Order(null, 26.45, "Molotok", 5, new ArrayList<>());
        Order order1 = new Order(null, 126.45, "Perforator", 1, new ArrayList<>());
        Order order2 = new Order(null, 2.45, "otvertka", 1, new ArrayList<>());
        Order order3 = new Order(null, 5D, "gvozdi", 100, new ArrayList<>());

        em.persist(user);
        em.persist(user1);
        em.persist(user2);

        user.setAddress(address);
        user1.setAddress(address1);
        user2.setAddress(address2);


        user.getOrrders().add(order);
        user1.getOrrders().add(order1);
        user2.getOrrders().add(order2);
        user.getOrrders().add(order3);
        user.getOrrders().add(order3);

        order.getUsers().add(user);
        order1.getUsers().add(user1);
        order2.getUsers().add(user2);
        order3.getUsers().add(user);

        em.getTransaction().commit();
        em.close();
    }




    @Test
    public void ReadAndUpdateAndRemoveTest() {
        EntityManager em = EMUtil.getEntityManager("mytasks");
        em.getTransaction().begin();
        em.clear();
        User userFromDb = em.find(User.class,1l);
        System.out.println(userFromDb.toString());
        Assert.assertEquals("Alex", userFromDb.getFirstName());
        userFromDb.getAddress().setCity("Nesvij");
        em.merge(userFromDb);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.remove(userFromDb);
        em.getTransaction().commit();
        em.close();
    }



    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }
}
