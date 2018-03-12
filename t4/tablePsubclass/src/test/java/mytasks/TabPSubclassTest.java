package mytasks;

import mytasks.entity.Car;
import mytasks.entity.Jeep;
import mytasks.entity.Truck;
import mytasks.utils.EMUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.EntityManager;


@FixMethodOrder(MethodSorters.JVM)
public class TabPSubclassTest {


    @Test
    public void saveCarTest() throws InterruptedException {

        Car car= new Car(null,1954,"Mers");
        Jeep jeep=new Jeep(null,1999,"Mers",500,true);
        Truck truck=new Truck(null,2013,"Mers",36000,3);

        EntityManager em = EMUtil.getEntityManager("mytasks");
        em.getTransaction().begin();
        em.persist(car);
        em.persist(jeep);
        em.persist(truck);
        em.getTransaction().commit();
        em.clear();

        em.getTransaction().begin();
        Car carFromDb = em.find(Car.class, car.getId());
        Assert.assertEquals(car, carFromDb);
        em.getTransaction().commit();


    }


    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }
}

