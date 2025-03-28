package bank.management.system.service.Role;

import bank.management.system.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role addRole(String name);
    Role findRoleByname(String string);

    List<Role> findAll();
}
