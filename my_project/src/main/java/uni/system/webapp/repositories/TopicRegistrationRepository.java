package uni.system.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.system.webapp.tables.TopicRegistration;

public interface TopicRegistrationRepository extends JpaRepository<TopicRegistration, String> {
}