package aua.badges.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author davitv
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "badges")
public class Badge implements Serializable {
    @Id
    private String id;
    private String ownerId;
    private String name;
    @Min(1)
    @Max(3)
    private Integer level;
    private String ownerEmail;

    private LocalDate assignmentDate;
    private String iconId;
    private LocalDate creationDate = LocalDate.now();

}
