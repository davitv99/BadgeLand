package io.project.app.requests;

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
public class UserPasswordChangeRequest{
private String id;
private String password;
}
