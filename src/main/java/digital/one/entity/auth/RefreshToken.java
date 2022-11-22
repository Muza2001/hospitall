package digital.one.entity.auth;

import digital.one.entity.template.AbcEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken extends AbcEntity {

    private String refreshToken;

    private Instant expiration_time;

}
