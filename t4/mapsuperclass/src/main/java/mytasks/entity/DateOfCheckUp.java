package mytasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@MappedSuperclass
public class DateOfCheckUp {


    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private LocalDateTime firstCheckUp;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime lastCheckUp;

}
