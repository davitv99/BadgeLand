package io.project.app.dto;

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
public class UserBadgesSummaryDTO implements Serializable {
    private String name;
    private String email;
    private Integer numberOfBadges;
}
