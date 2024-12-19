package falldetection.spring.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import falldetection.spring.Domain.Falldetectionvideo;
import falldetection.spring.Repository.HomecamRepository;
import falldetection.spring.Repository.VideoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class FalldetectionvideoService {
    private final AmazonS3 s3Client;
    private final String bucketName;
    VideoRepository videoRepository;
    HomecamRepository homecamRepository;

    public FalldetectionvideoService(
            @Value("${cloud.aws.credentials.access-key}") String accessKey,
            @Value("${cloud.aws.credentials.secret-key}") String secretKey,
            @Value("${cloud.aws.region.static}") String region,
            @Value("${cloud.aws.s3.bucket}") String bucketName,
            VideoRepository videoRepository, HomecamRepository homecamRepository) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        this.bucketName = bucketName;
        this.videoRepository = videoRepository;
        this.homecamRepository = homecamRepository;
    }

    public Long uploadFile(MultipartFile file, String serialnum) throws IOException {
        Falldetectionvideo falldetectionvideo = new Falldetectionvideo();
        File convertedFile = convertMultipartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(bucketName, fileName, convertedFile);
        convertedFile.delete();
        falldetectionvideo.setHomecamid(homecamRepository.findBySerialnum(serialnum).getId());
        String url = s3Client.getUrl(bucketName, fileName).toString();
        falldetectionvideo.setVideourl(url.substring(url.indexOf(".com/") + 5));
        falldetectionvideo.setSavetime(LocalDateTime.now());
        return videoRepository.save(falldetectionvideo).getId();
    }

    public byte[] downloadFile(String fileName) throws IOException {
        return s3Client.getObject(bucketName, fileName).getObjectContent().readAllBytes();
    }

    public InputStream getFileStream(Long videoid) {
        // S3에서 객체 가져오기
        String objectKey = videoRepository.findById(videoid).get().getVideourl();
        S3Object s3Object = s3Client.getObject(bucketName, objectKey);
        return s3Object.getObjectContent();
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    public List<Falldetectionvideo> getvideolist(Long homecamid){
        return videoRepository.findAllByHomecamid(homecamid);
    }
}
