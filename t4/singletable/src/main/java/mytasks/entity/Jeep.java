package mytasks.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "JEEP")
@DiscriminatorValue("J")
@DynamicInsert
@DynamicUpdate

public class Jeep extends Car{

    private Integer power;
    private Boolean zapaska;

    public Jeep(Long id, Integer year, String model, Integer power, Boolean zapaska) {
        super(id, year, model);
        this.power = power;
        this.zapaska = zapaska;
    }
}
