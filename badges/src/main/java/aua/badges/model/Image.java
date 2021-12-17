package aua.badges.model;

import aua.badges.enums.ImageCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author davitv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "badge_icon")
public class Image implements Serializable {

    @Id
    private String id;

    private String fileName;

    private String contentType;

    private Long fileSize;
    private ImageCategory imageType;
    private byte[] fileContent;
}
