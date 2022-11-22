package digital.one;

import digital.one.dto.request.AddressDto;
import digital.one.dto.request.AuthDto;
import digital.one.dto.request.RegisterDto;
import digital.one.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@RequiredArgsConstructor
@SpringBootApplication
public class OnlineHospitalApplication implements CommandLineRunner{

	private final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(OnlineHospitalApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		userService.register(new RegisterDto(
				"Muzaffar",
				"Mahmudov",
				new AddressDto(
						"Toshkent",
						"Uchtepa",
						"Hojobod",
						"7-sharof 20-uy"),
				"+998900075336",
				Instant.now(),
				new AuthDto("muza5660",
						"123")
			)
		);

	}
}
