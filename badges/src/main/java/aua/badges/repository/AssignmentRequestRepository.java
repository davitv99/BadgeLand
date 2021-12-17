package aua.badges.repository;

import aua.badges.enums.AssignmentStatus;
import aua.badges.model.AssignmentRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author davitv
 */
@Transactional
@Repository
public interface AssignmentRequestRepository extends MongoRepository<AssignmentRequest, String> {
    List<AssignmentRequest> findAllByUserIdAndStatus(String userId, AssignmentStatus status);
    List<AssignmentRequest> findAllByBadge_IdAndStatus(String badgeId,AssignmentStatus status);
    List<AssignmentRequest> findByUserIdAndStatusNot(String userId,AssignmentStatus status);
    List<AssignmentRequest> findAllByBadge_Id(String badgeId);
}
