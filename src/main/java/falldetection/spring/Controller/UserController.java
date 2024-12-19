package falldetection.spring.Controller;

import falldetection.spring.Domain.User;
import falldetection.spring.Domain.UserDto;
import falldetection.spring.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public Long join(@RequestBody User user){
        return userService.JoinUser(user);
    }

    @GetMapping("/login")
    @ResponseBody
    public Long login(@RequestParam String id, @RequestParam String password){
        return userService.UserLogin(id,password);
    }

    @GetMapping("/")
    @ResponseBody
    public UserDto getinformation(@RequestParam Long id){
        User user = userService.getUserinfo(id);
        UserDto userDto = new UserDto(user.getId(),user.getIdentifier(),user.getName(),user.getPhone_num());
        return userDto;
    }
}
