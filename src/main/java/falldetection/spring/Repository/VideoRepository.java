package falldetection.spring.Repository;

import falldetection.spring.Domain.Falldetectionvideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Falldetectionvideo, Long> {
    List<Falldetectionvideo> findAllByHomecamid(Long homecamid);
    Optional<Falldetectionvideo> findById(Long id);
}
