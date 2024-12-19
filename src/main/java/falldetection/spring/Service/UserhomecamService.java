package falldetection.spring.Service;

import falldetection.spring.Domain.Userhomecam;
import falldetection.spring.Repository.UserhomecamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class UserhomecamService {
    UserhomecamRepository userhomecamRepository;

    public UserhomecamService(UserhomecamRepository userhomecamRepository) {
        this.userhomecamRepository = userhomecamRepository;
    }

    public Long tryaccesshomecam(Userhomecam userhomecam){
        userhomecam.setAccess(Boolean.FALSE);
        return userhomecamRepository.save(userhomecam).getId();
    }

    public List<Userhomecam> getaccesshomecamlist(String serialnum){
        return userhomecamRepository.findAllByUserhomecam(serialnum);
    }

    public List<Userhomecam> getuseraccesslist(Long userid){
        return userhomecamRepository.findAllByUserid(userid);
    }


    public void allowaccess(Long userhomecamid){
        Userhomecam userhomecam = userhomecamRepository.findById(userhomecamid).get();
        userhomecam.setAccess(Boolean.TRUE);
        return;
    }
}
