package mytasks;

import mytasks.entity.Address;
import mytasks.entity.User;
import mytasks.entity.Order;
import mytasks.util.EMUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;


public class OneToOneTest {
    @Test
    public void saveTest() {
        EntityManager em = EMUtil.getEntityManager("mytasks");
        em.getTransaction().begin();
        User user = new User(null, "Alex", "Kuznecov", "7515313@mail.ru", null, null, null);
        Address address = new Address(null, "Germanskaya", "Minsk", "", "Belarus", user);
        Order order = new Order(null, 26.45, "Molotok", 5, user);
        em.persist(user);
        user.setOrder(order);
        user.setAddress(address);
        em.getTransaction().commit();
        em.clear();
        User userFromDb = em.find(User.class, 1L);
        System.out.println(userFromDb.toString());
        Assert.assertEquals(user.getFirstName(), userFromDb.getFirstName());
    }

    @Test
    public void removeCascadeTest() {
        EntityManager em = EMUtil.getEntityManager("mytasks");
        em.getTransaction().begin();
        User user = new User(null, "Alex", "Kuznecov", "7515313@mail.ru", null, null, null);
        Address address = new Address(null, "Germanskaya", "Minsk", "", "Belarus", user);
        Order order = new Order(null, 26.45, "Molotok", 5, user);
        user.setOrder(order);
        user.setAddress(address);
        em.persist(user);
        em.getTransaction().commit();
        em.clear();
        User userFromDb = em.find(User.class, 1L);
        Assert.assertEquals(user.getFirstName(), userFromDb.getFirstName());
        em.getTransaction().begin();
        em.remove(userFromDb);
        em.getTransaction().commit();
    }

    @Test
    public void mergeCascadeTest() {
        EntityManager em = EMUtil.getEntityManager("mytasks");
        em.getTransaction().begin();
        User user = new User(null, "Alex", "Kuznecov", "7515313@mail.ru", null, null, null);
        Address address = new Address(null, "Germanskaya", "Minsk", "", "Belarus", user);
        Order order = new Order(null, 26.45, "Molotok", 5, user);
        user.setOrder(order);
        user.setAddress(address);
        em.persist(user);
        em.getTransaction().commit();
        em.clear();
        User userFromDb = em.find(User.class, 1L);
        userFromDb.getOrder().setProduct("otvertka");
        userFromDb.getOrder().setQuantity(6);
        em.getTransaction().begin();
        em.merge(userFromDb);
        em.getTransaction().commit();
        Assert.assertEquals(user.getFirstName(), userFromDb.getFirstName());
    }


    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }
}
