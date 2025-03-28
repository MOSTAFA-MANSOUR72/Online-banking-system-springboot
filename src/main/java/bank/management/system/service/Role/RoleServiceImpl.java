package bank.management.system.service.Role;

import bank.management.system.entity.Role;
import bank.management.system.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    @Override
    public Role addRole(String name) {
        return roleRepository.save(
                Role.builder()
                        .name(name)
                        .build()
        );
    }

    @Override
    public Role findRoleByname(String string) {
        return roleRepository.getRoleByName(string)
                .orElseThrow(()->new RuntimeException("Role not found"));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
