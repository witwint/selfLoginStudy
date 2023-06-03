package loginStudy.jwtSocialLogin.domain.uploadfile;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import loginStudy.jwtSocialLogin.domain.base.BaseTime;
import loginStudy.jwtSocialLogin.domain.users.Users;
import loginStudy.jwtSocialLogin.dto.file.UploadFileDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Getter
@Builder
@Setter
public class UploadFile extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "upload_file_id")
	private Long id; // PK로 활용하기 위한 id값

	private String uploadFileName;


	private String storeFileName;

	@ManyToOne
	private Users usersImages;


	public UploadFile(String uploadFileName, String storeFileName) {
		this.uploadFileName = uploadFileName;
		this.storeFileName = storeFileName;
	}

	public static UploadFile getUploadFile(UploadFileDto uploadFileDto) {
		if (uploadFileDto == null) {
			return null;
		}
		return UploadFile.builder()
			.uploadFileName(uploadFileDto.getUploadFileName())
			.storeFileName(uploadFileDto.getStoreFileName())
			.build();
	}

	public static List<UploadFile> getUploadFileList(List<UploadFileDto> uploadFileDtoList) {
		if(uploadFileDtoList == null){
			return null;
		}
		List<UploadFile> entityList = new ArrayList<>();
		for (UploadFileDto uploadFileDto : uploadFileDtoList) {
			entityList.add(getUploadFile(uploadFileDto));
		}
		return entityList;
	}
}