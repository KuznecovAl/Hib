package mytasks;

import mytasks.entity.Car;
import mytasks.entity.DateOfCheckUp;
import mytasks.entity.Person;
import mytasks.utils.EMUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;


@FixMethodOrder(MethodSorters.JVM)
public class ComponentTest {


    @Test
    public void saveCarTest() {


        Car car = new Car(null, 1954, "Mersedes Benz",
                new Person(35, "Alex", "Kuznecov"),
                new DateOfCheckUp(LocalDateTime.now(), LocalDateTime.now()));

        EntityManager em = EMUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(car);
        em.getTransaction().commit();
        em.clear();
        Car carFromDb = em.find(Car.class, 1L);
        Assert.assertEquals(car.getPerson(), carFromDb.getPerson());


    }


    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }
}

