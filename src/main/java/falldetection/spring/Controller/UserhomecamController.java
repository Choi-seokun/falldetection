package falldetection.spring.Controller;

import falldetection.spring.Domain.Userhomecam;
import falldetection.spring.Service.UserhomecamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/homecam")
public class UserhomecamController {
    UserhomecamService userhomecamService;

    public UserhomecamController(UserhomecamService userhomecamService) {
        this.userhomecamService = userhomecamService;
    }

    @PostMapping("/access")
    public Long postaccess(@RequestBody Userhomecam userhomecam){
        return userhomecamService.tryaccesshomecam(userhomecam);
    }

    @GetMapping("/accesshomecam")
    public List<Userhomecam> getaccesshomecamlist(@RequestParam String serialnum){
        return userhomecamService.getaccesshomecamlist(serialnum);
    }

    @GetMapping("/useraccess")
    public List<Userhomecam> getuseraccesslist(@RequestParam Long userid){
        return userhomecamService.getuseraccesslist(userid);
    }

    @PutMapping("/access")
    public void putaccess(@RequestParam Long userhomecamid){
        userhomecamService.allowaccess(userhomecamid);
    }

}
