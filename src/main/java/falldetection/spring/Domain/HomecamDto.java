package falldetection.spring.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class HomecamDto {
    private Long id;

    private Long userid;

    private String serialnum;

    private String nickname;
}
