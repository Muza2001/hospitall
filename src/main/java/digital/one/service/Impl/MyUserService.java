package digital.one.service.Impl;

import digital.one.dto.request.AddressDto;
import digital.one.dto.request.EditClient;
import digital.one.dto.request.LoginRequest;
import digital.one.dto.request.RegisterDto;
import digital.one.dto.response.AddressResponse;
import digital.one.dto.response.AuthenticationResponse;
import digital.one.dto.response.ClientResponse;
import digital.one.dto.response.Response;
import digital.one.entity.Address;
import digital.one.entity.Auth;
import digital.one.entity.Client;
import digital.one.repository.AddressRepository;
import digital.one.repository.AuthRepository;
import digital.one.repository.ClientRepository;
import digital.one.security.JwtProvider;
import digital.one.service.RefreshTokenService;
import digital.one.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

import static digital.one.security.JwtProvider.EXPIRATION_TIME;

@RequiredArgsConstructor
@Service
public class MyUserService implements UserService, UserDetailsService {

    private final AuthRepository authRepository;

    private final ClientRepository clientRepository;

    private final AuthenticationManager authenticationManager;

    private final AddressRepository addressRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final RefreshTokenService refreshTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = authRepository.findByUsername(username).orElseThrow(()
                -> new IllegalArgumentException("Email not found"));
        return new org.springframework.security.core.userdetails.User(
                auth.getUsername(),
                auth.getPassword(),
                auth.isEnable(),
                true,
                true,
                true,
                grantedAuthority("USER")
        );
    }

    private Collection<? extends GrantedAuthority> grantedAuthority(String user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user));
    }

    public Auth getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authRepository.findByUsername(principal.getSubject()).orElseThrow(()
                -> new UsernameNotFoundException("Username Not Found"));
    }

    @Override
    public ResponseEntity<?> register(RegisterDto dto) {

        // there only create and save client table
        Response response = new Response();
        if (dto == null)
            return ResponseEntity.ok().body(new Response(false, "Bad request"));

        if (authRepository.existsByUsername(dto.getAuth().getUsername()))
            return ResponseEntity.ok().body(new Response(false, "Username already exists database"));

        if (clientRepository.existsByPhoneNumber(dto.getPhoneNumber()))
            return ResponseEntity.ok().body(new Response(false, "Phone number already exists database"));

        // create and save authentication table
        Auth auth = authRepository.save(
                new Auth(
                        dto.getAuth().getUsername(),
                        passwordEncoder.encode(dto.getAuth().getPassword()),
                        true,
                        Instant.now()));

        // create and save address table
        Address address = addressRepository.save(new Address(
                dto.getAddress().getCity(),
                dto.getAddress().getRegion(),
                dto.getAddress().getNeighborhood(),
                dto.getAddress().getHome()));

        // create and save client table
        Client client = clientRepository.save(new Client(
                dto.getFirstname(),
                dto.getLastname(),
                address,
                dto.getPhoneNumber(),
                Instant.now(),
                auth));

        // And here make a response
        response.setData(
                new ClientResponse(
                        client.getId(),
                        client.getFirstname(),
                        client.getLastname(),
                        client.getPhoneNumber(),
                        Instant.now(),
                        new AddressResponse(
                        address.getId(),
                        address.getCity(),
                        address.getRegion(),
                        address.getNeighborhood(),
                        address.getHome())));
        response.setSuccess(true);
        response.setStatus(HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest dto) {
        Authentication authenticate =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String generateToken = jwtProvider.generateToken(
                (org.springframework.security.core.userdetails.User) authenticate.getPrincipal());
        // TODO: 2/25/22 check if user login details match, if not handle it.

        AuthenticationResponse response = AuthenticationResponse.builder()
                .authenticationToken(generateToken)
                .refreshToken(refreshTokenService.generateRefreshToken().getRefreshToken())
                .username(dto.getUsername())
                .expirationData(Instant.now().plusMillis(EXPIRATION_TIME))
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> edit(EditClient editClient, Long id) {
        Client client = clientRepository.findById(id).orElseThrow(IllegalAccessError::new);
        AddressDto addressDto = editClient.getAddress();
        if ()
        return null;
    }

    public boolean isLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
