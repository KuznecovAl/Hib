package mytasks.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "JEEP")
@PrimaryKeyJoinColumn(name="CAR_ID")
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class Jeep extends Car{

    private Integer power;
    private Boolean zapaska;

    public Jeep(Long id, Integer year, String model, Integer power, Boolean zapaska) {
        super(id, year, model);
        this.power = power;
        this.zapaska = zapaska;
    }
}
