package mytasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.*;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "CAR")
@Inheritance(strategy= InheritanceType.JOINED)
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class Car implements CarInterface{

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer year;

    @Column
    private String model;





}