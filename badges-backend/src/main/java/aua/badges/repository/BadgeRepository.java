package aua.badges.repository;

import aua.badges.model.Badge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author davitv
 */

@Transactional
@Repository
public interface BadgeRepository extends MongoRepository<Badge, String> {
    public List<Badge> findAllByOwnerId(String ownerId);
    public List<Badge> findAllByOwnerIdNull();
}
