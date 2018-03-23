package mytasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GenericGenerator(name = "one-one",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    @GeneratedValue(generator = "one-one")
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "SUMM")
    private Double summ;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", summ=" + summ +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
