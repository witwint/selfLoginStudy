package loginStudy.jwtSocialLogin.domain.users;

import lombok.Getter;

@Getter
public enum AuthProvider {
	GOOGLE,
	KAKAO,
	NAVER
}