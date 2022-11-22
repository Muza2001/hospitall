package digital.one.service;

import digital.one.dto.request.EditClient;
import digital.one.dto.request.LoginRequest;
import digital.one.dto.request.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> register(RegisterDto dto);

    ResponseEntity<?> login(LoginRequest dto);

    ResponseEntity<?> edit(EditClient editClient, Long id);
}
