package aua.badges.requests;

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
public class UserLoginRequest implements Serializable {
    private String email;
    private String password;
}
