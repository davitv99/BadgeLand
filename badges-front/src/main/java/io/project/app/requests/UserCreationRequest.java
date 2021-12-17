package io.project.app.requests;


import io.project.app.enums.Gender;
import io.project.app.enums.UserRole;
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
