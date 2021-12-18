package aua.badges.dao;

import aua.badges.enums.AssignmentStatus;
import aua.badges.model.AssignmentRequest;
import aua.badges.repository.AssignmentRequestRepository;
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
public class AssignmentDAO {
    @Autowired
    private AssignmentRequestRepository assignmentRequestRepository;

    public List<AssignmentRequest> findAssignmentRequestByUserIdAndPendingStatus(String userId) {
        return assignmentRequestRepository.findAllByUserIdAndStatus(userId, AssignmentStatus.PENDING);
    }

    public List<AssignmentRequest> findAssignmentRequestByUserIdAndRejectedStatus(String userId) {
        return assignmentRequestRepository.findAllByUserIdAndStatus(userId, AssignmentStatus.REJECTED);
    }

    public List<AssignmentRequest> findRejectedAndPendingAssignmentRequestByUserId(String userId) {
        return assignmentRequestRepository.findByUserIdAndStatusNot(userId, AssignmentStatus.ACCEPTED);
    }

    public void saveAssignmentRequest(AssignmentRequest assignmentRequest) {
        assignmentRequestRepository.save(assignmentRequest);
    }

    public Optional<AssignmentRequest> findAssignmentById(String assignmentId) {
        return assignmentRequestRepository.findById(assignmentId);
    }

    public List<AssignmentRequest> findPendingAssignmentsByBadgeId(String badgeId) {
        return assignmentRequestRepository.findAllByBadge_IdAndStatus(badgeId, AssignmentStatus.PENDING);

    }

    public List<AssignmentRequest> findByBadgeId(String badgeId) {
        return assignmentRequestRepository.findAllByBadge_Id(badgeId);
    }

    public void removeAssignmentRequests(List<AssignmentRequest> assignmentRequestsOfBadge) {
         assignmentRequestRepository.deleteAll(assignmentRequestsOfBadge);
    }
}
