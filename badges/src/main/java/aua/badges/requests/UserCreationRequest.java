package aua.badges.requests;

import aua.badges.enums.Gender;
import aua.badges.enums.UserRole;
import lombok.*;

import java.io.Serializable;

/**
 * @author davitv
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserCreationRequest implements Serializable {

    private String name;

    private String email;

    private Gender gender;

    private UserRole role;

    private String password;
}
