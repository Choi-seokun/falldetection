package falldetection.spring.Controller;

import falldetection.spring.Domain.User;
import falldetection.spring.Domain.Userhomecam;
import falldetection.spring.Service.UserService;
import falldetection.spring.Service.UserhomecamService;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/sms")
public class SMSController {
    UserService userService;
    UserhomecamService userhomecamService;

    public SMSController(UserService userService, UserhomecamService userhomecamService) {
        this.userService = userService;
        this.userhomecamService = userhomecamService;
    }

    @GetMapping("/")
    @ResponseBody
    public Boolean send(@RequestParam String serialnum){
        Long id = 1L;
        String api_key = "NCSB9CXZTPJGHVZ2";
        String api_secret = "JORMYDM8NWOJINUEAWRBEML8RIFD23GZ";

        Message coolsms = new Message(api_key, api_secret);

        HashMap<String, String> params = new HashMap<String, String>();

        List<Userhomecam> userhomecamList = userhomecamService.getaccesshomecamlist(serialnum);
        User tempuser;
        for(int i=0; i<userhomecamList.size(); i++) {
            tempuser = userService.FindUser(userhomecamList.get(i).getUserid()).get();

            params.put("to", tempuser.getPhone_num());
            params.put("from", "010-3002-2174");
            params.put("type", "SMS");
            params.put("text", "현재 낙상이 감지되었습니다.");
            params.put("app_version", "test app 1.2");

            try {
                JSONObject obj = (JSONObject) coolsms.send(params);
                System.out.println(obj.toString());
            } catch (CoolsmsException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getCode());
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
