package uni.system.login_stuff;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean validateUser(String user, String pass) {
        return user.equals("andrew") && pass.equals("test");
    }
}
