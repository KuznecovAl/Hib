package mytasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="USERS")

//0

//1
//@DynamicUpdate
//@OptimisticLocking(type = OptimisticLockType.ALL)
//2
@DynamicUpdate
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@SelectBeforeUpdate  //???
//3
//@OptimisticLocking(type = OptimisticLockType.VERSION)


public class User implements Serializable {
    //3
    //@Version
    private Integer ver;
    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "AGE")
    private Integer age;

    @CreationTimestamp
    private LocalDateTime date;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Order> orrders;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.ALL})
    private Address address;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(date, user.date) &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", age='" + age + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                ", order=" + orrders +
                ", address=" + address +
                '}';
    }

    public void removeOrder(Order order) {
        orrders.remove(order);
        order.setUser(null);
    }
}
