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
public class BadgeDTO {
    private String id;
    private String name;
    private String ownerId;
    private Integer level;
    private AssignmentStatus status;
    private LocalDate assignmentDate;
    private byte[] icon;
}
