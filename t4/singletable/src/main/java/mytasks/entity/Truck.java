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
@Table(name = "TRUCK")
@DiscriminatorValue("T")
@DynamicInsert
@DynamicUpdate

public class Truck extends Car{

    private Integer carryingcapacity;
    private Integer axles;

    public Truck(Long id, Integer year, String model, Integer carryingcapacity, Integer axles) {
        super(id, year, model);
        this.carryingcapacity = carryingcapacity;
        this.axles = axles;
    }
}
