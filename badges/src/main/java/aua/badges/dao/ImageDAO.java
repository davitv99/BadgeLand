package aua.badges.dao;

import aua.badges.enums.ImageCategory;
import aua.badges.model.Image;
import aua.badges.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author davitv
 */
@Service
@Slf4j
public class ImageDAO {
@Autowired
    private ImageRepository imageRepository;

public Optional<Image> getImageById(String imageId){
    return imageRepository.findById(imageId);
}
public void saveImage(Image image){
    imageRepository.save(image);
}
public List<Image> getAllBadgeLogos(){
    return imageRepository.getAllByImageType(ImageCategory.BADGE);
}

    public void deleteImageById(String avatarId) {
    imageRepository.deleteById(avatarId);
    }
}

