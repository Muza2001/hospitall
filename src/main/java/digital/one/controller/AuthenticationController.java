package digital.one.controller;

import digital.one.dto.request.EditClient;
import digital.one.dto.request.LoginRequest;
import digital.one.dto.request.RegisterDto;
import digital.one.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto){
        return userService.register(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest dto){
        return userService.login(dto);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody EditClient editClient, @PathVariable Long id){
        return userService.edit(editClient, id);
    }
}
