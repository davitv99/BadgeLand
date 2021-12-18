package aua.badges.dto;

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
public class UserGeneralInfoDTO implements Serializable {
    private String id;

    private String name;

    private Gender gender;

    private String about;
    private String email;

    private UserRole role = UserRole.USER;

    private byte[] avatar;
}
