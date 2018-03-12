package mytasks.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TRUCK")


public class Truck extends Car{

    private Integer carryingcapacity;
    private Integer axles;

    public Truck(Long id, Integer year, String model, Integer carryingcapacity, Integer axles) {
        super(id, year, model);
        this.carryingcapacity = carryingcapacity;
        this.axles = axles;
    }
}
