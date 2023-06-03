package loginStudy.jwtSocialLogin.dto.file;

import loginStudy.jwtSocialLogin.domain.uploadfile.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UploadFileDto {

	private Long id;

	private String uploadFileName;

	private String storeFileName;

	public static UploadFileDto toDto(UploadFile uploadFile) {
		return UploadFileDto.builder()
			.id(uploadFile.getId())
			.uploadFileName(uploadFile.getUploadFileName())
			.storeFileName(uploadFile.getStoreFileName())
			.build();
	}

}
