package bank.management.system.dto.authentication;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterRequest {
    private String firstName;

    private String lastName;

    private String password;

    private String gender;

    private String address;

    private String stateOfOrigin;

    private String phoneNumber;

    private String email;
}
