package uni.system.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uni.system.webapp.repositories.UserRepository;
import uni.system.webapp.tables.User;

import java.util.Arrays;

public class uniPasswordEncoder implements PasswordEncoder {

    @Autowired
    UserRepository userRepository;

    @Override
    public String encode(CharSequence charSequence) {
        String username = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getParameter("username");
        String userType = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getParameter("user");

        if (userType.equals("student"))
            username += "STU";
        else
            username += "STA";

        User u = userRepository.findByUsername(username);
        return Arrays.toString(PasswordUtils.hash(charSequence.toString().toCharArray(), u.getSalt().getBytes()));
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String username = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getParameter("username");
        String userType = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getParameter("user");

        if (userType.equals("student"))
            username += "STU";
        else
            username += "STA";

        User u = userRepository.findByUsername(username);
        return PasswordUtils.verifyUserPassword(charSequence.toString(), s, u.getSalt());
    }
}
