package digital.one.entity;

import digital.one.entity.template.AbcEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends AbcEntity {

    private String firstname;

    private String lastname;

    @ManyToOne
    private Address address;

    @Column(length = 13, precision = 13, unique = true)
    private String phoneNumber;

    private Instant created_at;

    @OneToOne
    private Auth auth;


}
