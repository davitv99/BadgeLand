package aua.badges.service;

import aua.badges.dao.AssignmentDAO;
import aua.badges.dao.BadgeDAO;
import aua.badges.dto.BadgeDTO;
import aua.badges.enums.AssignmentStatus;
import aua.badges.model.AssignmentRequest;
import aua.badges.model.Badge;
import aua.badges.utility.MapperUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author davitv
 */
@Service
@Slf4j
public class BadgeService {
    @Autowired
    private BadgeDAO badgeDAO;
    @Autowired
    private AssignmentDAO assignmentDAO;
    @Autowired
    private MapperUtility mapperUtility;
    public void createBadge(String name, Integer level, String iconId) {
        Badge badge = new Badge();
        badge.setIconId(iconId);
        badge.setLevel(level);
        badge.setName(name);
        badgeDAO.saveBadge(badge);
    }

    public Boolean removeBadge(String badgeId) {
        Optional<Badge> badge = badgeDAO.findBadgeById(badgeId);
        if (!badge.isPresent()) {
            return false;
        }

        badgeDAO.deleteBadge(badge.get());
        List<AssignmentRequest> assignmentRequestsOfBadge = assignmentDAO.findByBadgeId(badgeId);
        if (!assignmentRequestsOfBadge.isEmpty()) {
            assignmentDAO.removeAssignmentRequests(assignmentRequestsOfBadge);
        }
        return true;
    }

    public List<Badge> findUserCurrentBadges(String userId) {
        return badgeDAO.findUserCurrentBadges(userId);
    }

    public List<BadgeDTO> findAllUserBadges(String userId) {
        List<Badge> badges = badgeDAO.findUserCurrentBadges(userId);
        List<BadgeDTO> badgesFinal;
        badgesFinal = mapperUtility.badgesToBadgeDTOS(badges);
        for(BadgeDTO badge: badgesFinal){
             badge.setStatus(AssignmentStatus.ACCEPTED);
        }
        List<AssignmentRequest> assignmentRequests = assignmentDAO.findRejectedAndPendingAssignmentRequestByUserId(userId);
        if (!assignmentRequests.isEmpty()) {
            List<Badge> rejectedBadgesOfUser = assignmentRequests.stream().filter(x->x.getStatus()==AssignmentStatus.REJECTED).map(AssignmentRequest::getBadge).collect(Collectors.toList());
            List<Badge> pendingBadgesOfUser = assignmentRequests.stream().filter(x->x.getStatus()==AssignmentStatus.PENDING).map(AssignmentRequest::getBadge).collect(Collectors.toList());
            if(!rejectedBadgesOfUser.isEmpty()){
                List<BadgeDTO> rejectedBadgesOfUserWithStatus = mapperUtility.badgesToBadgeDTOS(rejectedBadgesOfUser);
                for(BadgeDTO badge: rejectedBadgesOfUserWithStatus){
                    badge.setStatus(AssignmentStatus.REJECTED);
                }
                badgesFinal.addAll(rejectedBadgesOfUserWithStatus);
            }
        if(!pendingBadgesOfUser.isEmpty()){
            List<BadgeDTO> pendingBadgesOfUserWithStatus = mapperUtility.badgesToBadgeDTOS(pendingBadgesOfUser);
            for(BadgeDTO badge: pendingBadgesOfUserWithStatus){
                badge.setStatus(AssignmentStatus.PENDING);
            }
            badgesFinal.addAll(pendingBadgesOfUserWithStatus);
        }



        }
        return badgesFinal;
    }

    public List<Badge> findAllUnassignedBadges() {
        return badgeDAO.findUnassignedBadges();
    }

    public Optional<Badge> findBadgeById(String badgeId) {
        return badgeDAO.findBadgeById(badgeId);
    }

    public void editBadge(Badge badge, String name, Integer level, String iconId) {
        badge.setName(name);
        badge.setLevel(level);
        badge.setIconId(iconId);
        badgeDAO.saveBadge(badge);
    }
}
