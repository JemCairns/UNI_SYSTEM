package uni.system.webapp.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uni.system.webapp.security.LoginAttemptService;
import uni.system.webapp.security.UserInfo;
import uni.system.webapp.repositories.UserRepository;
import uni.system.webapp.tables.User;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static uni.system.webapp.filter.SecurityConstraints.*;

public class JWTAuthorFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    private LoginAttemptService loginAttemptService;

    public JWTAuthorFilter(AuthenticationManager authenticationManager, UserRepository userRepository, LoginAttemptService loginAttemptService) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        Cookie [] cookies = request.getCookies();
        String token = null;


        if(cookies!=null){

            for(Cookie cookie: cookies){
                if(cookie.getName().equals(COOKIE_NAME))
                    token = cookie.getValue();
            }}
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token, request, response);

        if(authentication == null) {
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (token != null) {
            String user = null;
            try {
                 user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                        .build()
                        .verify(token.replace(TOKEN_PREFIX, ""))
                        .getSubject();
            } catch (TokenExpiredException e) {
                SecurityContextHolder.clearContext();
                loginAttemptService.loginFail(request.getRemoteAddr(), response);
                if(!response.isCommitted()) {
                    Cookie c = new Cookie(COOKIE_NAME, null);
                    c.setMaxAge(0);
                    response.addCookie(c);
                    response.sendRedirect("/login");
                }
            }

            if (user != null) {
                User u = userRepository.findByUsername(user);
                UserInfo info = new UserInfo(u);
                return new UsernamePasswordAuthenticationToken(user, null, info.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
