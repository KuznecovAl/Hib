package mytasks;

import mytasks.entity.Car;
import mytasks.entity.Person;
import mytasks.utils.EMUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.EntityManager;


@FixMethodOrder(MethodSorters.JVM)
public class PersonAndCarEntityManagerTest {


    @Test//(expected = AssertionError.class)
    public void saveCarTest() {
        Person p=new Person(1L,25,"Alex", "Kuznecov!!!");
        Car car = new Car(3L, 25, "Opel", p);
        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();
        car=em.merge(car);
        em.getTransaction().commit();
        em.clear();

        em.getTransaction().begin();
        Car carFromDb = em.find(Car.class, car.getId());
        Assert.assertEquals(car, carFromDb);
//        em.remove(carFromDb);
        em.getTransaction().commit();
    }



    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }
}
