package digital.one.service;

import digital.one.dto.request.AddressDto;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    ResponseEntity<?> create(AddressDto dto);
}
