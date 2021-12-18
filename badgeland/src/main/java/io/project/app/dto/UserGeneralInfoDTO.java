package io.project.app.dto;


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
public class UserGeneralInfoDTO implements Serializable {
    private String id;

    private String name;

    private String gender;

    private String about;

    private String email;

    private String role;

    private byte[] avatar;
}
