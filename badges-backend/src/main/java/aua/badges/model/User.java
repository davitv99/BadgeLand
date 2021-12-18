package aua.badges.model;

import aua.badges.enums.Gender;
import aua.badges.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author davitv
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User implements Serializable {
    @Id
    private String id;

    private String name;

    private Gender gender;

    private String about;
    @Indexed
    private String email;

    private UserRole role = UserRole.USER;

    private String password;

    private String avatarId;


}
