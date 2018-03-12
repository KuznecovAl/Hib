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
import java.util.ArrayList;


@FixMethodOrder(MethodSorters.JVM)
public class MapSuperClassTest {


    @Test
    public void saveCarTest() throws InterruptedException {

        Person pers=new Person(35,"Alex","Kuznecov");
        Car car = new Car(1954,"Mers Benz",new ArrayList<>());
        car.getOwners().add(pers);

        EntityManager em = EMUtil.getEntityManager("mytasks");
        em.getTransaction().begin();
        em.persist(car);
        em.persist(pers);
        em.getTransaction().commit();
        em.clear();

        Person persFromDb = em.find(Person.class, 2L);
        System.out.println(persFromDb);
        em.getTransaction().begin();
        persFromDb.setAge(20000);
        Thread.sleep(10);
        em.getTransaction().commit();
        System.out.println(persFromDb);
    }


    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }
}

