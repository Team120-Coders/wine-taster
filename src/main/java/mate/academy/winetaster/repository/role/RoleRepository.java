package mate.academy.winetaster.repository.role;

import java.util.Optional;
import mate.academy.winetaster.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(Role.RoleName roleName);
}
