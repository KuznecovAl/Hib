package mytasks.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "CAR")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CAR_TYPE",
        discriminatorType = DiscriminatorType.CHAR)
//@DiscriminatorFormula("case when COMPANY is not null then 'E' else 'S' end")
//@DiscriminatorOptions(force = false, insert = true)
@DiscriminatorValue("C")

@ToString
@EqualsAndHashCode
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer year;

    @Column
    private String model;

//    @OneToMany(cascade = CascadeType.PERSIST)
//    private List<Person> owners = new ArrayList<>(0);




}