package falldetection.spring.Service;

import falldetection.spring.Domain.Homecam;
import falldetection.spring.Domain.User;
import falldetection.spring.Repository.HomecamRepository;
import falldetection.spring.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Transactional
@Service
public class HomecamService {
    HomecamRepository homecamRepository;
    UserRepository userRepository;

    public HomecamService(HomecamRepository homecamRepository, UserRepository userRepository) {
        this.homecamRepository = homecamRepository;
        this.userRepository = userRepository;
    }

    public Long JoinHomecam(Homecam homecam, String userid){
        homecam.setUserid(userRepository.findByIdentifier(userid).getId());
        try {
            return homecamRepository.save(homecam).getId();
        }catch (Exception exception){
            return -1L;
        }
    }

    public Long HomecamLogin(String id, String password){
        Long userid = userRepository.findByIdentifier(id).getId();
        Homecam homecam = homecamRepository.findByUserid(userid);
        if(homecam.getPassword().equals(password)){
            return homecam.getUserid();
        }else{
            return -1L;
        }
    }

    public Homecam GetHomecamInfoBySerialnum(String serialnum){
        return homecamRepository.findBySerialnum(serialnum);
    }

    public Homecam GetHomecamInfoByUserid(Long userid){
        return homecamRepository.findByUserid(userid);
    }
}
