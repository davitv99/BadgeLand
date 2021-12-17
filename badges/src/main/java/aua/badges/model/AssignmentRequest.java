package aua.badges.model;

import aua.badges.enums.AssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author davitv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "assigment_requests")
public class AssignmentRequest implements Serializable {
    @Id
    private String id;
    private AssignmentStatus status;
    private Badge badge;
    private String userId;
    private String userName;
    private LocalDate requestDate;
}
