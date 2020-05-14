package uni.system.webapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import uni.system.webapp.logger.Logging;
import uni.system.webapp.repositories.UserRepository;
import uni.system.webapp.tables.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static uni.system.webapp.filter.SecurityConstraints.*;

public class CookieLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    UserRepository userRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_NAME))
                    token = cookie.getValue();
            }

            if (token != null) {
                String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                        .build()
                        .verify(token.replace(TOKEN_PREFIX, ""))
                        .getSubject();

                if (user != null) {
                    User u = userRepository.findByUsername(user);
                    Logging.getInstance().info("User with id=" + u.getUsername() + " logged out.");

                    Cookie cookie = new Cookie(COOKIE_NAME, null);
                    cookie.setMaxAge(0);
                    cookie.setSecure(true);
                    cookie.setHttpOnly(true);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }

            super.onLogoutSuccess(request, response, authentication);
        }
    }
}
