package bank.management.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.results.graph.Fetch;

import java.util.List;

@Builder
@Data
@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            joinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore

    private List<User> user;
}
