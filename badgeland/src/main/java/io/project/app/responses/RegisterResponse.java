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
public class RegisterResponse {

    private UserDTO userDTO;
    private String token;

}