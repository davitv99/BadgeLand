package aua.badges.controller;

import aua.badges.dto.BadgeDTO;
import aua.badges.enums.UserRole;
import aua.badges.model.Badge;
import aua.badges.model.User;
import aua.badges.security.SecurityService;
import aua.badges.service.BadgeService;
import aua.badges.service.UserService;
import aua.badges.utility.MapperUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author davitv
 */


@RestController
@RequestMapping("/api/badges")
@Slf4j
public class BadgesController {
    @Autowired
    private BadgeService badgesService;
    @Autowired
    private UserService usersService;
    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private SecurityService securityService;
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBadge(
            final @RequestHeader(name = "authorization",required = true) String token,
            final @RequestParam(name = "badgeName", required = true) String badgeName,
            final @RequestParam(name = "badgeLevel", required = true) Integer badgeLevel,
            final @RequestParam(name = "badgeIconId", required = true) String iconId,
            final @RequestParam(name = "userRole", required = true) UserRole role) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        if (!role.equals(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You should be admin to create a new badge");
        }
        badgesService.createBadge(badgeName, badgeLevel, iconId);
        return ResponseEntity.ok().body("Badge created successfully");
    }

    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteBadge(
            final @RequestHeader(name = "authorization",required = true) String token,
            @RequestParam(name = "badgeId", required = true) String badgeId,
            @RequestParam(name = "userRole", required = true) UserRole role
    ) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        if (!role.equals(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You should be admin to delete a badge");
        }

        Boolean status = badgesService.removeBadge(badgeId);
        if (status) {
            return ResponseEntity.ok("Badge deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Badge does not exist");
    }



    @GetMapping(path = "/find/current/badges/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findExistingUserBadges(
            final @RequestHeader(name = "authorization",required = true) String token,
            @RequestParam(name = "userId", required = true) String userId
    ) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Optional<User> user = usersService.findUserById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id does not exist");
        }
        List<Badge> badges = badgesService.findUserCurrentBadges(userId);
        if (!badges.isEmpty()) {
            return ResponseEntity.ok(mapperUtility.badgesToBadgeDTOS(badges));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not have any badges");
    }

    @GetMapping(path = "/find/pending/rejected/accepted/badges/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllUserBadges(
            final @RequestHeader(name = "authorization",required = true) String token,
            @RequestParam(name = "userId", required = true) String userId
    ) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Optional<User> user = usersService.findUserById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id does not exist");
        }
        List<BadgeDTO> badges = badgesService.findAllUserBadges(userId);
        if (!badges.isEmpty()) {
            return ResponseEntity.ok(badges);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not have any badges");
    }

    @GetMapping(path = "/find/unassigned/badges", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllUnassignedBadges(
            final @RequestHeader(name = "authorization",required = true) String token
            ) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        List<Badge> badges = badgesService.findAllUnassignedBadges();
        if (badges.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no unassigned badges");
        }
        return ResponseEntity.ok(mapperUtility.badgesToBadgeDTOS(badges));
    }
    @PutMapping(path = "/edit/badge", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editBadge(
            final @RequestHeader(name = "authorization",required = true) String token,
            final @RequestParam(name = "badgeName",required = false) String name,
            final @RequestParam(name = "badgeLevel",required = false) Integer level,
            final @RequestParam(name = "badgeId",required = true) String id,
            final @RequestParam(name = "badgeIconId",required = false) String iconId
    ) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Optional<Badge> badge = badgesService.findBadgeById(id);
        if (!badge.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no badge with this id");
        }
        badgesService.editBadge(badge.get(),name,level,iconId);
        return ResponseEntity.ok("Badge edited successfully");

    }
}
