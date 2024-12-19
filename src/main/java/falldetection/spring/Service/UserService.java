package falldetection.spring.Service;

import falldetection.spring.Domain.User;
import falldetection.spring.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long JoinUser(User user){
        try {
            return userRepository.save(user).getId();
        }catch (Exception exception){
            return -1L;
        }
    }

    public Optional<User> FindUser(Long id){
        return userRepository.findById(id);
    }

    public Long UserLogin(String id, String password){
        User user = userRepository.findByIdentifier(id);
        if(user.getPassword().equals(password)){
            return user.getId();
        }else{
            return -1L;
        }
    }

    public User getUserinfo(Long id){
        return userRepository.findById(id).get();
    }

}
