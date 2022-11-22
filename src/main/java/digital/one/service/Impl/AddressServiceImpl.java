package digital.one.service.Impl;

import digital.one.dto.request.AddressDto;
import digital.one.dto.response.AddressResponse;
import digital.one.dto.response.Response;
import digital.one.entity.Address;
import digital.one.repository.AddressRepository;
import digital.one.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public ResponseEntity<?> create(AddressDto dto) {
        Address address = Address
                .builder()
                .city(dto.getCity())
                .home(dto.getHome())
                .neighborhood(dto.getNeighborhood())
                .region(dto.getRegion())
                .build();

        Address saveAddress = addressRepository.save(address);

        AddressResponse addressResponse = new AddressResponse(
                saveAddress.getId(),
                saveAddress.getCity(),
                saveAddress.getRegion(),
                saveAddress.getNeighborhood(),
                saveAddress.getHome());

        Response response = new Response(
                true,
                "Address successfully saved",
                addressResponse);

        return ResponseEntity.status(201).body(response);
    }
}
