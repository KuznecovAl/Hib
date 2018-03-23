package mytasks;

import mytasks.entity.Address;
import mytasks.entity.NameAdressDTO;
import mytasks.entity.Order;
import mytasks.entity.User;
import mytasks.util.EMUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public class CriteriaTest {
    @Before
    public void init() {
        EntityManager em = EMUtil.getEntityManager("mytasks-create");
        em.getTransaction().begin();
        User user = new User(null, "Alex", "Kuznecov", "7515313@mail.ru", 34, null, new ArrayList<>(), null);
        User user1 = new User(null, "Yuri", "Gagarin", "Yuri@mail.ru", 80, null, new ArrayList<>(), null);
        User user2 = new User(null, "German", "Titov", "g_titov@mail.ru", 70, null, new ArrayList<>(), null);
        User user3 = new User(null, "Lavrentiy", "Beriya", "beriya@mail.ru", 100, null, new ArrayList<>(), null);
        User user4 = new User(null, "Yuri", "Dolgorukiy", "Yuri_D@mail.ru", 1001, null, new ArrayList<>(), null);
        User user5 = new User(null, "Yuri", "Petrov", "Yuri_P@mail.com", 29, null, new ArrayList<>(), null);
        User user6 = new User(null, null, "Sidorov", "sid@mail.com", 12, null, new ArrayList<>(), null);

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
    public void JoinTest() {
        Logger log = LogManager.getLogger("my");
        EntityManager em = EMUtil.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);
        Join<User, Address> addressJoin = userRoot.join("address", JoinType.INNER);
        criteria.where(cb.equal(addressJoin.get("city"), "Minsk"));
        List<User> users = em.createQuery(criteria).getResultList();
        users.forEach(System.out::println);
    }

    @Test

    public void nativeQueryTest() {
        EntityManager em = EMUtil.getEntityManager();
        Session unwrap = em.unwrap(Session.class);
        List<NameAdressDTO> dtoshechki = unwrap.createNativeQuery
                ("SELECT u.user_id AS id, u.first_Name AS fn, a.country AS cou FROM USERS AS u " +
                        "LEFT JOIN ADDRESS AS a ON u.user_id=a.user_id")
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("fn", StandardBasicTypes.STRING)
                .addScalar("cou", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(NameAdressDTO.class))
                .getResultList();
        dtoshechki.forEach(System.out::println);
    }


    @Test
    public void mainTest() {
        Logger log = LogManager.getLogger("my");
        EntityManager em = EMUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);
        Join<User, Address> joinAdr = userRoot.join("address", JoinType.INNER);
        Predicate predicate = cb.and(
                cb.isNotNull(userRoot.get("firstName")),
                cb.between(userRoot.get("age"), 1, 999),
                cb.notLike(joinAdr.get("city"), "Minsk")
        );
        criteria.select(userRoot)
                .where(predicate)
                .orderBy(cb.desc(userRoot.get("age")));

        List<User> users = em.createQuery(criteria).getResultList();
        users.forEach(System.out::println);
    }

    @Test
    public void pagingTest() {
        EntityManager em = EMUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        int pageNumber = 1;
        int pageSize = 5;
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> usr = criteria.from(User.class);
        criteria.select(usr);
        TypedQuery<User> typedQuery = em.createQuery(criteria);
        typedQuery.setFirstResult(pageSize * (pageNumber - 1));
        typedQuery.setMaxResults(pageSize);
        List<User> users = typedQuery.getResultList();

        users.forEach(System.out::println);
    }


    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }
}
