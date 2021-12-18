package io.project.app.responses;


import io.project.app.dto.UserDTO;
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
