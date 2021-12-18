package aua.badges.controller;

import aua.badges.enums.UserRole;
import aua.badges.model.AssignmentRequest;
import aua.badges.model.Badge;
import aua.badges.model.User;
import aua.badges.security.SecurityService;
import aua.badges.service.AssignmentService;
import aua.badges.service.BadgeService;
import aua.badges.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author davitv
 */


@RestController
@RequestMapping("/api/assignment")
@Slf4j
public class AssignmentController {
    @Autowired
    private BadgeService badgesService;
    @Autowired
    private UserService userService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private SecurityService securityService;
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createAssignmentRequest(
            final @RequestParam(name = "authorization",required = true) String token,
            @RequestParam(name = "user_id") String userId,
            @RequestParam(name = "badgeId") String badgeId) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Optional<User> user = userService.findUserById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id does not exist");
        }
        Optional<Badge> badge = badgesService.findBadgeById(badgeId);
        if (!badge.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Badge with this id does not exist");
        }
        assignmentService.createAssignmentRequest(user.get(),badge.get());
        return ResponseEntity.ok("Request created successfully");
    }
    @PutMapping(path="/accept/request", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity acceptAssignmentRequest(
            final @RequestParam(name = "authorization",required = true) String token,
            @RequestParam(name = "assignmentId") String assignmentId,
            @RequestParam(name = "userRole") UserRole role
    ) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        if (!role.equals(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You should be admin to accept a request");
        }
        Optional<AssignmentRequest> assignmentRequest = assignmentService.findAssignmentById(assignmentId);
        if (!assignmentRequest.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment request with this id does not exist");
        }
        Boolean status = assignmentService.acceptAssignmentRequest(assignmentRequest.get());
        if (!status) {
            return ResponseEntity.badRequest().body("Error occurred");
        }
    return ResponseEntity.ok("Request Accepted");
    }

    @PutMapping(path="/assign/badge/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity asssignBadgeToUser(
            final @RequestParam(name = "authorization",required = true) String token,
            @RequestParam(name = "badgeId") String badgeId,
            @RequestParam(name = "userRole") UserRole role,
            @RequestParam(name = "user_id") String userId
    ) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        if (!role.equals(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You should be admin to assign badge");
        }
        Optional<Badge> badge = badgesService.findBadgeById(badgeId);
        if (!badge.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Badge with this id does not exist");
        }
        if(!userService.findUserById(userId).isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id does not exist");
        }
        Boolean status = assignmentService.assignBadgeToUser(badge.get(),userId);
        if (!status) {
            return ResponseEntity.badRequest().body("Error occurred");
        }
        return ResponseEntity.ok("Badge assigned");
    }

    @PutMapping(path="/unassign/badge/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity unassignBadgeFromUser(
            final @RequestParam(name = "authorization",required = true) String token,
            @RequestParam(name = "badgeId") String badgeId,
            @RequestParam(name = "userRole") UserRole role
    ) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        if (!role.equals(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You should be admin to assign badge");
        }
        Optional<Badge> badge = badgesService.findBadgeById(badgeId);
        if (!badge.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Badge with this id does not exist");
        }
        assignmentService.unassignBadgeFromUser(badge.get());
        return ResponseEntity.ok("Badge assigned");
    }
}
