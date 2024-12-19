package falldetection.spring.Controller;

import com.amazonaws.services.s3.model.S3Object;
import falldetection.spring.Domain.Falldetectionvideo;
import falldetection.spring.Service.FalldetectionvideoService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/video")
public class VideoController {
    private final FalldetectionvideoService s3Service;

    public VideoController(FalldetectionvideoService s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    @ResponseBody
    public Long uploadVideo(@RequestParam("file") MultipartFile file, @RequestParam("serialnum")String serialnum) {
        try {
            return s3Service.uploadFile(file, serialnum);
        } catch (Exception e) {
            return -1L;
        }
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Falldetectionvideo> GetVideoList(@RequestParam Long homecamid){
        return s3Service.getvideolist(homecamid);
    }

    @GetMapping("/stream")
    public ResponseEntity<InputStreamResource> streamVideo(
            @RequestParam Long videoid,
            @RequestHeader(value = "Range", required = false) String range) {

        // S3에서 동영상 스트림 가져오기
        InputStream videoStream = s3Service.getFileStream(videoid);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        // HTTP Range 요청 처리 (기본적으로 전체 반환)
        if (range != null) {
            headers.add(HttpHeaders.CONTENT_RANGE, range); // 실제 Range 구현은 추가 가능
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(videoStream));
    }
}
