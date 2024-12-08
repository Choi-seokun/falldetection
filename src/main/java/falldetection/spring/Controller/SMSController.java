package falldetection.spring.Controller;

import falldetection.spring.Domain.User;
import falldetection.spring.Service.UserService;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/sms")
public class SMSController {
    UserService userService;

    public SMSController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @ResponseBody
    public Boolean send(@RequestParam String serialnum){
        Long id = 1L;
        String api_key = "NCS6LFGK08YO9UW9";
        String api_secret = "2Q9APUOOX0BMTYFADZ5OVUTOJMNDEXEG";

        Message coolsms = new Message(api_key, api_secret);

        HashMap<String, String> params = new HashMap<String, String>();

        User user = userService.FindUser(id).get();

        params.put("to", user.getPhone_num());
        params.put("from", "01047958805");
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
        return Boolean.TRUE;
    }
}
