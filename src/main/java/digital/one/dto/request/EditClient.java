package digital.one.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditClient {

    private String firstname;

    private String lastname;

    private AddressDto address;

    private String phoneNumber;

    private Instant created_at;

    private AuthDto auth;

}
