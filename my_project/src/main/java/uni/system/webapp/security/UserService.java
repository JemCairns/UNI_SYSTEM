package uni.system.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextListener;
import uni.system.webapp.repositories.UserRepository;
import uni.system.webapp.tables.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        }

        return new UserInfo(user);
    }

    private String getIP() {
        String header = request.getHeader("X-Forwarded-For");
        if(header == null) {
            return request.getRemoteAddr();
        }
        return header.split(",")[0];
    }

}
