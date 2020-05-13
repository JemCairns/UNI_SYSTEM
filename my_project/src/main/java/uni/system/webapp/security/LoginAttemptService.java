package uni.system.webapp.security;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

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

    public void loginSuccess(String key) {
        System.out.println("SUCCESS");
        cache.invalidate(key);
    }

    public void loginFail(String key) {
        System.out.println("FAIL");
        int n = 0;
        try {
            n = cache.get(key);
        } catch (ExecutionException ignored) {
        }

        n++;
        cache.put(key, n);
    }

    public boolean isBlocked(String key) {
        try {
            return cache.get(key) >= attempts;
        } catch (ExecutionException e) {
            return false;
        }
    }


}
