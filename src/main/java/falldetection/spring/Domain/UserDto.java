package falldetection.spring.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;

    private String identifier;

    private String name;

    private String phone_num;
}
