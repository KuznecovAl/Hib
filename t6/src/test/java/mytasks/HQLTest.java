package mytasks;

import mytasks.entity.Address;
import mytasks.entity.Order;
import mytasks.entity.User;
import mytasks.util.EMUtil;
import org.apache.logging.log4j.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;


public class HQLTest {
    @Before
    public void init() {
        EntityManager em = EMUtil.getEntityManager("mytasks-create");
        em.getTransaction().begin();
        User user = new User(null, "Alex", "Kuznecov", "7515313@mail.ru",34, null, new ArrayList<>(), null);
        User user1 = new User(null, "Yuri", "Gagarin", "Yuri@mail.ru",40, null, new ArrayList<>(), null);
        User user2 = new User(null, "German", "Titov", "g_titov@mail.ru",80, null, new ArrayList<>(), null);
        User user3 = new User(null, "Lavrentiy", "Beriya", "beriya@mail.ru",67, null, new ArrayList<>(), null);
        User user4 = new User(null, "Yuri", "Dolgorukiy", "Yuri_D@mail.ru",879, null, new ArrayList<>(), null);

        Address address = new Address(null, "Germanskaya", "Minsk", "", "Belarus", user);
        Address address1 = new Address(null, "Mira", "Moskva", "", "Russia", user1);
        Address address2 = new Address(null, "Stupki", "Kiev", "", "Ukraina", user2);
        Address address3 = new Address(null, "Shvili", "Tiflis", "", "Georgia", user3);
        Address address4 = new Address(null, "Kings", "Gorod-sad", "", "Utopia", user4);

        Order order = new Order(null, 26.45, "Molotok", 5, user);
        Order order1 = new Order(null, 126.45, "Perforator", 1, user1);
        Order order2 = new Order(null, 2.45, "otvertka", 1, user2);
        Order order3 = new Order(null, 5D, "gvozdi", 100, user);
        Order order4 = new Order(null, 5D, "gvozdi", 50, user3);
        Order order5 = new Order(null, 5D, "gvozdi", 5098, user4);

        em.persist(user);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);

        user.setAddress(address);
        user1.setAddress(address1);
        user2.setAddress(address2);
        user3.setAddress(address3);
        user4.setAddress(address4);

        user.getOrrders().add(order);
        user1.getOrrders().add(order1);
        user2.getOrrders().add(order2);
        user.getOrrders().add(order3);
        user3.getOrrders().add(order4);
        user4.getOrrders().add(order5);

        em.getTransaction().commit();
        em.close();
    }

//TODO---------------
    @Test
    public void PagenatorTest() {

    }


    @Test
    public void FindTest() {
        Logger log = LogManager.getLogger("my");
        EntityManager em = EMUtil.getEntityManager();
        Session session = em.unwrap(Session.class);

        //по имени
        Query query = session.createQuery("SELECT U.firstName, U.lastName from User AS U where U.firstName='Yuri'");
        query.getResultList().forEach(result -> {
            Object[] res = (Object[]) result;
            log.info(res[0]+" "+res[1]);});

        //по id
        query = session.createQuery("SELECT U from User AS U where U.userId=4L");
        User u=(User)query.getSingleResult();
        log.info(u);

        //по двум полям + разные типы
        query = session.createQuery("SELECT U.firstName, U.age from User AS U where U.firstName='Yuri' and U.age<50");
        query.getResultList().forEach(result -> {
            Object[] res = (Object[]) result;
            log.info(res[0]+" "+res[1]);});

        //групирование
        query = session.createQuery("SELECT count(distinct U.firstName),U.firstName from User AS U group by U.firstName");
        query.getResultList().forEach(result -> {
            Object[] res = (Object[]) result;
            log.info(res[0]+" "+res[1]);});



    }

    @Test
    public void CopyFieldsAndDeleteTest() {
        Logger log = LogManager.getLogger("my");
        EntityManager em = EMUtil.getEntityManager();
        Session session = em.unwrap(Session.class);

        Query query = session.createQuery("from User where userId=1L");
        User userFromDb1 = (User) query.getSingleResult();

        query = session.createQuery("from User where userId=2L");
        User userFromDb2 = (User) query.getSingleResult();

        em.getTransaction().begin();
        userFromDb1.getAddress().setCity(userFromDb2.getOrrders().get(0).getProduct());
        em.getTransaction().commit();

        em.getTransaction().begin();
        query = session.createQuery("delete from User where userId=2L");
        System.out.println(query.executeUpdate());
        em.getTransaction().commit();
    }


    @Test
    public void AgregationTest() {
        Logger log = LogManager.getLogger("my");
        EntityManager em = EMUtil.getEntityManager();
        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("select count(u) from User as u");
        log.info("количество элементов в user: " + query.getSingleResult());
        query = session.createQuery("select sum(o.summ),avg(o.summ),max(o.summ) " +
                "from User u inner join u.orrders o");
        query.getResultList().forEach(result -> {
            Object[] res = (Object[]) result;
            log.info("по ctоимосtи в ордер !!!сумма: " + res[0] + " !!!сред: " + res[1] + " !!!max: " + res[2]);
        });
    }


    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }
}
