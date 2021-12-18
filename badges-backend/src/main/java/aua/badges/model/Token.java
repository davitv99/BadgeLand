package aua.badges.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author davitv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "token")
public class Token implements Serializable {
    @Id
    private String id;
    private String token;
    private String userId;
}
