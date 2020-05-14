package uni.system.webapp.security;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;
import uni.system.webapp.logger.Logging;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private int attempts = 3;
    private LoadingCache<String, Integer> cache;

    public LoginAttemptService() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    public void loginSuccess(String key, HttpServletResponse response) throws IOException {
        if(isBlocked(key)) {
            response.sendRedirect("/block");
        }
        else {
            Logging.getInstance().info("User with id=" + key + " successfully logged in from IP=" /*+IP*/ + ".");
            cache.invalidate(key);
        }
    }

    public void loginFail(String key, HttpServletResponse response) throws IOException {
        int n = 0;
        try {
            n = cache.get(key);
        } catch (ExecutionException ignored) {
        }

        n++;
        cache.put(key, n);
        Logging.getInstance().info("User with id=" + key + " failed to log in from IP=" /*+IP*/ + ".");


        if(isBlocked(key)) {
            Logging.getInstance().warning("User with IP=" /*+IP*/ + " has been blocked.");
            response.sendRedirect("/block");
        }
    }

    public boolean isBlocked(String key) {
        try {
            return cache.get(key) >= attempts;
        } catch (ExecutionException e) {
            return false;
        }
    }


}
