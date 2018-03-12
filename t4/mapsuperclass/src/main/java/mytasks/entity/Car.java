package mytasks.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Car extends DateOfCheckUp {

//    @Id
//    @GeneratedValue
//    @Getter
//    @Setter
//    private Long id;

    @Getter
    private Integer year;

    @Getter
    private String model;

    @Getter
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Person> owners = new ArrayList<>(0);



//    @Column
//    public String getModel() {
//        return "Model"+model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
}