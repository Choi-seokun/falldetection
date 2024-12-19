package falldetection.spring.Repository;

import falldetection.spring.Domain.Homecam;
import falldetection.spring.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomecamRepository extends JpaRepository<Homecam, Long> {
    Homecam findByUserid(Long id);
    Homecam findBySerialnum(String serialnum);
}
