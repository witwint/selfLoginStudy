package loginStudy.jwtSocialLogin.v1.repository.users;

import java.util.Optional;
import loginStudy.jwtSocialLogin.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmail(String email);
	boolean existsByEmail(String email);
}