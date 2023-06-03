package loginStudy.jwtSocialLogin.v1.controller.users;

import loginStudy.jwtSocialLogin.dto.Response;
import loginStudy.jwtSocialLogin.dto.users.UsersRequestDto;
import loginStudy.jwtSocialLogin.dto.users.UsersRequestDto.SignUp;
import loginStudy.jwtSocialLogin.jwt.JwtTokenProvider;
import loginStudy.jwtSocialLogin.lib.Helper;
import loginStudy.jwtSocialLogin.v1.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UsersController {

	private final JwtTokenProvider jwtTokenProvider;
	private final UsersService usersService;
	private final Response response;

	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@Validated @RequestBody SignUp signUp,@ApiIgnore Errors errors) {
		// validation check
		if (errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		return usersService.signUp(signUp);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Validated @RequestBody UsersRequestDto.Login login,@ApiIgnore Errors errors) {
		// validation check
		if (errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		return usersService.login(login);
	}

	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@Validated @RequestBody UsersRequestDto.Reissue reissue,@ApiIgnore Errors errors) {
		// validation check
		if (errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		return usersService.reissue(reissue);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@Validated @RequestBody UsersRequestDto.Logout logout,@ApiIgnore Errors errors) {
		// validation check
		if (errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		return usersService.logout(logout);
	}

	@GetMapping("/authority")
	public ResponseEntity<?> authority() {
		log.info("ADD ROLE_ADMIN");
		return usersService.authority();
	}

	@GetMapping("/userTest")
	public ResponseEntity<?> userTest() {
		log.info("ROLE_USER TEST");
		return response.success();
	}

	@GetMapping("/adminTest")
	public ResponseEntity<?> adminTest() {
		log.info("ROLE_ADMIN TEST");
		return response.success();
	}


}