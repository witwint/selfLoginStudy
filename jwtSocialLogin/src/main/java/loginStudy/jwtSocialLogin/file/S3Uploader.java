package loginStudy.jwtSocialLogin.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import loginStudy.jwtSocialLogin.dto.file.UploadFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	public String bucket;

	public List<UploadFileDto> uploads(List<MultipartFile> multipartFiles) throws IOException {
		if (multipartFiles == null) {
			return null;
		}
		List<UploadFileDto> storeFileResult = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			if (!multipartFile.isEmpty()) {
				storeFileResult.add(upload(multipartFile));
			}
		}
		return storeFileResult;
	}

	public UploadFileDto upload(MultipartFile multipartFile) throws IOException {
		String originalFilename = multipartFile.getOriginalFilename();
		log.info("originalFilename = {}", originalFilename);

		//서버에 저장하는 파일명
		String storeFileName = createStoreFileName(originalFilename);
		log.info("storeFileName = {}", storeFileName);
		File uploadFile = convert(multipartFile)        // 파일 생성
			.orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File convert fail"));
		upload(uploadFile, storeFileName);
		return new UploadFileDto(null,originalFilename, storeFileName);
	}

	private void upload(File uploadFile, String storeFileName) {
		putS3(uploadFile, storeFileName);    // s3로 업로드
		removeNewFile(uploadFile);
	}

	// 1. 로컬에 파일생성
	private Optional<File> convert(MultipartFile file) throws IOException {
		File convertFile = new File(file.getOriginalFilename());
		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}
			return Optional.of(convertFile);
		}

		return Optional.empty();
	}

	// 2. S3에 파일업로드
	private void putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
			CannedAccessControlList.PublicRead));
		log.info("File Upload : " + fileName);
	}

	// 3. 로컬에 생성된 파일삭제
	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.info("File delete success");
			return;
		}
		log.info("File delete fail");
	}


	public void delete(String fileName) {
		log.info("File Delete : " + fileName);
		amazonS3Client.deleteObject(bucket, fileName);
	}

	public String getFullPath(String fileName) {
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}


	private String createStoreFileName(String originalFilename) {
		String uuid = UUID.randomUUID().toString();
		String ext = extractExt(originalFilename);
		return uuid + "." + ext;
	}

	private String extractExt(String originalFilename) {
		int pos = originalFilename.lastIndexOf(".");
		return originalFilename.substring(pos + 1);
	}

}