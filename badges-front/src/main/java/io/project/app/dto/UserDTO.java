package io.project.app.dto;


import io.project.app.enums.Gender;
import io.project.app.enums.UserRole;
import lombok.*;

/**
 * @author davitv
 */

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserDTO {
    private String id;

    private String name;

    private String email;

    private Gender gender;

    private UserRole role;


}
