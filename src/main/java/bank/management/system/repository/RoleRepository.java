package bank.management.system.repository;

import bank.management.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> getRoleByName(String name);
    Boolean existsByName(String name);
}
