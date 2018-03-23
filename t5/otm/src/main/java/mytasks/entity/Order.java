package mytasks.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

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

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;


}
