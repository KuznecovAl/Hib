package mytasks;

import mytasks.entity.Person;
import mytasks.entity.Car;
import mytasks.utils.EMUtil;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.EntityManager;


@FixMethodOrder(MethodSorters.JVM)
public class PersonAndCarEntityManagerTest {


    @Test
    public void saveCarTest() {
        Car car = new Car(1L, 25, "Opel", "Alex");
        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(car);
        em.getTransaction().commit();
        em.clear();

        em.getTransaction().begin();
        Car carFromDb = em.find(Car.class, car.getId());
        Assert.assertEquals(car, carFromDb);
        em.remove(carFromDb);
        em.getTransaction().commit();
    }

    @Test
    public void savePersonTest() {
        Person person = new Person(null, 34, "Alex", "Kuznecov");
        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();
        person = em.merge(person);
        em.getTransaction().commit();
        em.clear();

        em.getTransaction().begin();
        Person personFromDb = em.find(Person.class, person.getId());
        Assert.assertEquals(person, personFromDb);
        em.getTransaction().commit();
    }

    @Test
    public void getPersonTest() {

        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();

        Session session = em.unwrap(Session.class);
        Person personFromDb = session.get(Person.class, 1L);

        em.getTransaction().commit();
        em.clear();
        System.out.println(personFromDb.toString());
        Assert.assertEquals(personFromDb.getName(), "Alex");
    }

    @Test(expected = LazyInitializationException.class)
    public void loadPersonTest() {

        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();

        Session session = em.unwrap(Session.class);
        Person personFromDb = session.load(Person.class, 1L);
        em.getTransaction().commit();
        //System.out.println(personFromDb.toString());

        em.clear();
        System.out.println(personFromDb.toString());
        em.close();

    }


    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }
}
