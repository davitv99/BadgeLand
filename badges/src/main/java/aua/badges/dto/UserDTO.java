package aua.badges.dto;

import aua.badges.enums.Gender;
import aua.badges.enums.UserRole;
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

    private String avatarId;
}
