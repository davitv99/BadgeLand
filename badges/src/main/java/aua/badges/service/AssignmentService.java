package aua.badges.service;


import aua.badges.dao.AssignmentDAO;
import aua.badges.dao.BadgeDAO;
import aua.badges.dao.UserDAO;
import aua.badges.enums.AssignmentStatus;
import aua.badges.model.AssignmentRequest;
import aua.badges.model.Badge;
import aua.badges.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author davitv
 */
@Service
@Slf4j
public class AssignmentService {
    @Autowired
    private AssignmentDAO assignmentDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BadgeDAO badgeDAO;

    public void createAssignmentRequest(User user, Badge badge) {
        AssignmentRequest assignmentRequest = new AssignmentRequest();
        assignmentRequest.setStatus(AssignmentStatus.PENDING);
        assignmentRequest.setBadge(badge);
        assignmentRequest.setUserId(user.getId());
        assignmentRequest.setUserName(user.getName());
        assignmentRequest.setRequestDate(LocalDate.now());
        assignmentDAO.saveAssignmentRequest(assignmentRequest);
    }

    public Optional<AssignmentRequest> findAssignmentById(String assignmentId) {
        return assignmentDAO.findAssignmentById(assignmentId);
    }

    public Boolean acceptAssignmentRequest(AssignmentRequest assignmentRequest) {
        Optional<User> user = userDAO.findUserById(assignmentRequest.getUserId());
        if (!user.isPresent()) {
            return false;
        }
        Badge badge = assignmentRequest.getBadge();
        List<AssignmentRequest> pendingAssignmentRequestsOfCurrentBadge = assignmentDAO.findPendingAssignmentsByBadgeId(badge.getId());
        List<AssignmentRequest> pendingAssignmentRequestsOfCurrentBadgeFiltered = pendingAssignmentRequestsOfCurrentBadge.stream().filter(x -> !x.getUserId().equals(assignmentRequest.getUserId())).collect(Collectors.toList());
        for (AssignmentRequest ar : pendingAssignmentRequestsOfCurrentBadgeFiltered) {
            rejectAssignmentRequest(ar);
        }
        badge.setOwnerEmail(user.get().getEmail());
        badge.setOwnerId(user.get().getId());
        badge.setAssignmentDate(LocalDate.now());
        badgeDAO.saveBadge(badge);
        assignmentRequest.setStatus(AssignmentStatus.ACCEPTED);
        assignmentDAO.saveAssignmentRequest(assignmentRequest);
        return true;
    }

    public void rejectAssignmentRequest(AssignmentRequest assignmentRequest) {
        assignmentRequest.setStatus(AssignmentStatus.REJECTED);
        assignmentDAO.saveAssignmentRequest(assignmentRequest);
    }

    public Boolean assignBadgeToUser(Badge badge, String userId) {
        badge.setOwnerId(userId);
        badgeDAO.saveBadge(badge);
        List<AssignmentRequest> assignmentRequests = assignmentDAO.findByBadgeId(badge.getId());
        if(!assignmentRequests.isEmpty()){
            for (AssignmentRequest assignmentRequest: assignmentRequests){
                if(!assignmentRequest.getUserId().equals(userId)){
                    rejectAssignmentRequest(assignmentRequest);
                }
                acceptAssignmentRequest(assignmentRequest);
            }
        }
        return true;
    }

    public void unassignBadgeFromUser(Badge badge) {
        badge.setOwnerId(null);
        badgeDAO.saveBadge(badge);
    }
}
