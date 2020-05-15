package uni.system.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import uni.system.webapp.filter.JWTAuthenFilter;
import uni.system.webapp.filter.JWTAuthorFilter;
import uni.system.webapp.repositories.UserRepository;

import static uni.system.webapp.filter.SecurityConstraints.COOKIE_NAME;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private UserRepository userRepository;
    private LoginAttemptService loginAttemptService;

    public SecurityConfig(UserService userService, UserRepository userRepository, LoginAttemptService loginAttemptService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/block")
                .antMatchers(HttpMethod.GET, "/login")
                .antMatchers(HttpMethod.GET, "/register")
                .antMatchers("/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/register", "/error").permitAll()
                    .antMatchers("/fees").hasRole("STUDENT")
                    .antMatchers("/edit_module").hasRole("STAFF")
                    .antMatchers("/grades").hasRole("STAFF")
                    .anyRequest().authenticated()
                    .and()
                .csrf()
                    .ignoringAntMatchers("/login")
                    .ignoringAntMatchers("/register")
                    .and()
                .requiresChannel()
                    .anyRequest()
                    .requiresSecure()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("login")
                    .deleteCookies(COOKIE_NAME)
                    .logoutSuccessHandler(logoutSuccessHandler())
                    .permitAll()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .addFilter(new JWTAuthenFilter(authenticationManager(), loginAttemptService))
                .addFilter(new JWTAuthorFilter(authenticationManager(), userRepository, loginAttemptService));
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userService);

        return daoAuthenticationProvider;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CookieLogoutSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new uniPasswordEncoder(); }
}
