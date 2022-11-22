package digital.one.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponse {

    private Long id;

    private String firstname;

    private String lastname;

    private String phoneNumber;

    private Instant created_at;

    private AddressResponse address;



}
