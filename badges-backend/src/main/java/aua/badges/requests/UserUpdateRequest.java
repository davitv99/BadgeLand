package aua.badges.requests;

import aua.badges.dto.ImageDTO;
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
