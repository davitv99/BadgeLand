package io.project.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author davitv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO implements Serializable {

    private String id;
    private String contentType;

    private Long fileSize;

    private byte[] fileContent;
}
