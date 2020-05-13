package uni.system.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class AuthSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) e.getAuthentication().getDetails();

        if(authenticationDetails != null)
            loginAttemptService.loginSuccess(authenticationDetails.getRemoteAddress());
    }
}
