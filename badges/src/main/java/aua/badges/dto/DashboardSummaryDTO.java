package aua.badges.dto;

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
public class DashboardSummaryDTO implements Serializable {
    private String name;
    private Integer receivedBadges;
    private Integer pendingBadges;
    private Integer rejectedBadges;
}
