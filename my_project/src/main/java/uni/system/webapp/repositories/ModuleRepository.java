package uni.system.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.system.webapp.tables.Module;

public interface ModuleRepository extends JpaRepository<Module, String> {
}