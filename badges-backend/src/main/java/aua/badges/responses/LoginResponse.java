package aua.badges.responses;

import aua.badges.dto.UserDTO;
import lombok.*;

/**
 * @author davitv
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class LoginResponse {

    private UserDTO userDTO;
    private String token;

}
