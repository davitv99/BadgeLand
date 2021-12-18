package aua.badges.dao;

import aua.badges.model.Badge;
import aua.badges.repository.BadgeRepository;
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
public class BadgeDAO {
    @Autowired
    private BadgeRepository badgeRepository;
    public List<Badge> findUserCurrentBadges(String ownerId) {
        return badgeRepository.findAllByOwnerId(ownerId);
    }
    public List<Badge> findUnassignedBadges(){
        return badgeRepository.findAllByOwnerIdNull();
    }
    public void saveBadge(Badge badge) {
       badgeRepository.save(badge);
    }

    public Optional<Badge> findBadgeById(String badgeId) {
        return badgeRepository.findById(badgeId);
    }

    public void deleteBadge(Badge badge) {
    badgeRepository.delete(badge);
    }

}
