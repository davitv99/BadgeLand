package aua.badges.service;

import aua.badges.dao.ImageDAO;
import aua.badges.enums.FileType;
import aua.badges.enums.ImageCategory;
import aua.badges.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author davitv
 */
@Service
@Slf4j
public class ImageService {
    @Autowired
    private ImageDAO imageDAO;

    public boolean uploadLogo(MultipartFile logo) throws IOException {
        if (!logo.isEmpty()) {
            Tika tika = new Tika();
            byte[] logoContent = logo.getBytes();
            String contentType = tika.detect(logoContent);
            if (FileType.fromString(contentType) == null) {
                return false;
            }
            Image image = new Image();
            image.setImageType(ImageCategory.BADGE);
            image.setFileContent(logoContent);
            image.setFileSize(logo.getSize());
            image.setFileName(logo.getOriginalFilename());
            image.setContentType(contentType);
            imageDAO.saveImage(image);
            return true;
        }
        return false;
    }
    public List<Image> getAllBadgeLogos(){
            return imageDAO.getAllBadgeLogos();
    }
}
