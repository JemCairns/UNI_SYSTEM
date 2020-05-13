package uni.system.webapp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.annotation.WebListener;

@Configuration
@WebListener
public class MyRequestContextListener extends RequestContextListener {
}