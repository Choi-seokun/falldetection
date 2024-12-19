package falldetection.spring.Controller;

import falldetection.spring.Domain.Homecam;
import falldetection.spring.Domain.HomecamDto;
import falldetection.spring.Service.HomecamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/homecam")
public class HomecamController {
    HomecamService homecamService;

    public HomecamController(HomecamService homecamService) {
        this.homecamService = homecamService;
    }

    @PostMapping("/")
    @ResponseBody
    public Long join(@RequestBody Homecam homecam, @RequestParam String userid){
        return homecamService.JoinHomecam(homecam, userid);
    }

    @GetMapping("/register")
    @ResponseBody
    public Long login(@RequestParam String id, @RequestParam String password){
        return homecamService.HomecamLogin(id,password);
    }

    @GetMapping("/")
    @ResponseBody
    public HomecamDto gethomecaminfo(@RequestParam String serialnum){
        Homecam homecam = homecamService.GetHomecamInfoBySerialnum(serialnum);
        HomecamDto homecamDto = new HomecamDto(homecam.getId(),homecam.getUserid(),homecam.getSerialnum(),homecam.getNickname());
        return  homecamDto;
    }

    @GetMapping("/info")
    @ResponseBody
    public HomecamDto gethomecaminfo2(@RequestParam Long userid){
        Homecam homecam = homecamService.GetHomecamInfoByUserid(userid);
        HomecamDto homecamDto = new HomecamDto(homecam.getId(),homecam.getUserid(),homecam.getSerialnum(),homecam.getNickname());
        return  homecamDto;
    }
}
