package mytasks.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
@Entity
@Table(name = "ORRDERS")
public class Order implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "ORRDER_ID")
    private Long orderid;

    @Column(name = "SUMM")
    private Double summ;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ManyToMany(mappedBy = "orrders", cascade = CascadeType.ALL)
    private List<User> users;


}
