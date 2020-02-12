package uni.system.login_stuff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginDetailsRepository loginDetailsRepository;

    public boolean validateUser(String user, String pass) {
        if(loginDetailsRepository.existsById(user)) {
            return loginDetailsRepository.getOne(user).getPassword().equals(pass);
        }
        return false;
    }
}
