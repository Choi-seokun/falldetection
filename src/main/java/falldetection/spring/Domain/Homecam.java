package falldetection.spring.Domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Homecam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long userid;

    @Column(unique = true)
    private String serialnum;

    private String password;

    private String nickname;
}
