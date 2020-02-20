package uni.system.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.system.webapp.tables.Topic;

public interface TopicRepository extends JpaRepository<Topic, String> {
}