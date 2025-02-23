package bank.management.system.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Builder
public class grantedAuthority implements GrantedAuthority {
    private final String role;
    @Override
    public String getAuthority() {
        return role;
    }
}
