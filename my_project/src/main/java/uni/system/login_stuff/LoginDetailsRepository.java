package uni.system.login_stuff;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginDetailsRepository extends JpaRepository<LoginDetails, String> {
}
