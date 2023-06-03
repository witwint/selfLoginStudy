package loginStudy.jwtSocialLogin.v1.repository.file;

import loginStudy.jwtSocialLogin.domain.uploadfile.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<UploadFile, Long> {

}
