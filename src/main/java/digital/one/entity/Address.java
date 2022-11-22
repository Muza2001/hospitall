package digital.one.entity;

import digital.one.entity.template.AbcEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends AbcEntity {

    private String city;

    private String region;

    private String neighborhood;

    private String home;

}
