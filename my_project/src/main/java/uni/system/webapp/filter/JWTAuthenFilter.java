package uni.system.webapp.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uni.system.webapp.security.UserInfo;
import uni.system.webapp.repositories.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static uni.system.webapp.filter.SecurityConstraints.*;

public class JWTAuthenFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    public JWTAuthenFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("user");

        if(request.getMethod().equals("POST") && request.getRequestURL().toString().equals("https://localhost:8443/login")) {
            if (userType.equals("student"))
                username += "STU";
            else
                username += "STA";
        }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        String token = JWT.create()
            .withSubject(((UserInfo) authentication.getPrincipal()).getUsername())
            .withExpiresAt(new Date((System.currentTimeMillis() + EXPIRATION_TIME)))
            .sign(Algorithm.HMAC512(SECRET.getBytes()));

        addCookie(token, response);
        if(request.getMethod().equals("POST")) {
            String csrfToken = JWT.create()
                    .withSubject(((UserInfo) authentication.getPrincipal()).getUsername())
                    .withExpiresAt(new Date((System.currentTimeMillis() + 30000)))
                    .sign(Algorithm.HMAC512(SECRET.getBytes()));
            response.addHeader(TOKEN_HEADER, TOKEN_PREFIX + csrfToken);
        }
        new DefaultRedirectStrategy().sendRedirect(request, response,"/welcome");
    }

    private void addCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setMaxAge((int) EXPIRATION_TIME);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
