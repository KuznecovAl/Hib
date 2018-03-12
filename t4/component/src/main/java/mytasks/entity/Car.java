package mytasks.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Car {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column
    @Getter
    @Access(AccessType.FIELD)
    private Integer year;


    private String model;

    @Embedded
    @Getter
    private Person person;

    @Embedded
    @Getter
    private DateOfCheckUp dateOfCheckUp;


    @Column
    public String getModel() {
        return "Model"+model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}