package mytasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="USERS")
public class User implements Serializable {
    @Id @GeneratedValue
    @Column(name = "USER_ID", unique = true)
    private Long userId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL")
    private String email;

    @CreationTimestamp
    private LocalDateTime date;

    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.ALL})
    private Order order;

    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.ALL})
    private Address address;


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                ", order=" + order +
                ", address=" + address +
                '}';
    }
}
