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
public class RegisterResponse {

    private UserDTO userDTO;
    private String token;

}