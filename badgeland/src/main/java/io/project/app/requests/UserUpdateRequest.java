package io.project.app.requests;

import io.project.app.dto.ImageDTO;
import lombok.*;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserUpdateRequest {
    private String about;
    private String name;
    private String id;
    private ImageDTO avatar;
}
