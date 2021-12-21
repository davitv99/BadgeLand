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
public class UserCreationRequest{

    private String name;

    private String email;

    private String gender;

    private String role;

    private String password;
}
