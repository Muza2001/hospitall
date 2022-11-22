package digital.one.entity;

import digital.one.entity.template.AbcEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbcEntity {

    @Column(unique = true, nullable = false)
    private String name;

    private Instant created_at;

}
