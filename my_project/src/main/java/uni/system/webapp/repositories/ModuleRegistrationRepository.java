package uni.system.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.system.webapp.tables.ModuleRegistration;

public interface ModuleRegistrationRepository extends JpaRepository<ModuleRegistration, Integer> {
}