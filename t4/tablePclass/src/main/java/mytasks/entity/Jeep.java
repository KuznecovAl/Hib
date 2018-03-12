package mytasks.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "JEEP")

public class Jeep extends Car{

    private Integer power;
    private Boolean zapaska;

    public Jeep(Long id, Integer year, String model, Integer power, Boolean zapaska) {
        super(id, year, model);
        this.power = power;
        this.zapaska = zapaska;
    }
}
