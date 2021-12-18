package aua.badges.dto;

import aua.badges.enums.AssignmentStatus;
import lombok.*;

import java.time.LocalDate;

/**
 * @author davitv
 */


@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AssignmentRequestDTO {
    private String id;
    private AssignmentStatus status;
    private BadgeDTO badge;
    private String userId;
    private String userName;
    private LocalDate requestDate;
}
