package aua.badges.repository;

import aua.badges.enums.ImageCategory;
import aua.badges.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author davitv
 */
@Transactional
@Repository
public interface ImageRepository extends MongoRepository<Image,String> {
    public List<Image> getAllByImageType(ImageCategory imageCategory);
}
