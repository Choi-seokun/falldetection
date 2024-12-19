package falldetection.spring.Repository;

import falldetection.spring.Domain.Userhomecam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserhomecamRepository extends JpaRepository<Userhomecam, Long> {
    List<Userhomecam> findAllByUserhomecam(String serialnum);

    List<Userhomecam> findAllByUserid(Long userid);
}
