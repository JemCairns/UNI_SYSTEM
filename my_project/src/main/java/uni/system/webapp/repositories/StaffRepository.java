package uni.system.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.system.webapp.tables.Staff;

public interface StaffRepository extends JpaRepository<Staff, String> {
}
