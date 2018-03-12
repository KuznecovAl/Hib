package mytasks.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "CAR")

@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
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