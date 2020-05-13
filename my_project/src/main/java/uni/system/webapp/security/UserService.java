package uni.system.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.UserRepository;
import uni.system.webapp.tables.User;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpServletRequest request;

    @Autowired
    LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String ip = getIP();
        User user = userRepository.findByUsername(s);

        if(loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("BLOCKED");
        }

        UserInfo userInfo = new UserInfo(user);
        return userInfo;
    }

    private String getIP() {
        String header = request.getHeader("X-Forwarded-For");
        if(header == null) {
            return request.getRemoteAddr();
        }
        return header.split(",")[0];
    }
}
