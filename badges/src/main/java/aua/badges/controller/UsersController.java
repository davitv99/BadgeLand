package aua.badges.controller;

import aua.badges.dto.*;
import aua.badges.enums.UserRole;
import aua.badges.model.User;
import aua.badges.requests.UserCreationRequest;
import aua.badges.requests.UserLoginRequest;
import aua.badges.requests.UserPasswordChangeRequest;
import aua.badges.responses.LoginResponse;
import aua.badges.responses.RegisterResponse;
import aua.badges.security.SecurityService;
import aua.badges.service.UserService;
import aua.badges.utility.MapperUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author davitv
 */

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UsersController {
    @Autowired
    private UserService usersService;

    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private SecurityService securityService;

    @PutMapping(path = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginUser(
            @RequestBody(required = true) UserLoginRequest userLoginRequest
    ) {
        log.info("LOGIN USER");
        Optional<User> user = usersService.findUserByEmail(userLoginRequest.getEmail());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There is no user with this email");
        }
        if (!user.get().getPassword().equals(userLoginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Wrong password");
        }
        log.info("Success returning user" + user.get());
        return ResponseEntity.ok(new LoginResponse(mapperUtility.userToUserDTO(user.get()),securityService.generateAndSaveTokenForUser(user.get().getId())));
    }

    @DeleteMapping(path = "/user/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity logoutUser(
            @RequestParam(name = "Authorization",required = true) String token
    ) {
        if (!securityService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        securityService.destroyToken(securityService.getTokenByToken(token));

        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(
            final @RequestBody(required = true) UserCreationRequest userCreationRequest) {

        if (usersService.findUserByEmail(userCreationRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is user with this email");
        }
        UserDTO user = usersService.createUser(userCreationRequest);
        return ResponseEntity.ok().body( new RegisterResponse(user,securityService.generateAndSaveTokenForUser(user.getId())));
    }

    @PutMapping(path = "/edit/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editUser(
            final @RequestParam(name = "Authorization",required = true) String token,
            final @RequestParam(name = "userName",required = false) String name,
            final @RequestParam(name = "about",required = false) String about,
            final @RequestParam(name = "user_id",required = true) String id,
            final @RequestParam(name = "avatar",required = false) ImageDTO avatar
    ) {

        if (!securityService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Optional<User> user = usersService.findUserById(id);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user with this id");
        }

            User editedUser = usersService.updateUser(name, about, user.get(), avatar);
            return ResponseEntity.ok().body(usersService.getUserGeneralInformation(editedUser));


    }


    @PutMapping(path = "/edit/user/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editUserPassword(
            final @RequestParam(name = "Authorization",required = true) String token,
            final @RequestBody(required = true) UserPasswordChangeRequest userPasswordChangeRequest
    ) {
        if (!securityService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Optional<User> user = usersService.findUserById(userPasswordChangeRequest.getId());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user with this id");
        }
        usersService.updateUserPassword(user.get(), userPasswordChangeRequest.getPassword());
        return ResponseEntity.ok().body("User password changed successfully");


    }

    @GetMapping(path = "/find/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllUsers(
            final @RequestParam(name = "Authorization",required = true) String token
    ) {
        if (!securityService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        List<User> users = usersService.findAllUsers();
        if (!users.isEmpty()) {
            return ResponseEntity.ok().body(mapperUtility.usersToUserDTOS(users));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no users");
    }

    @GetMapping(path = "/find/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(
            final @RequestParam(name = "Authorization",required = true) String token,
            final @RequestParam(name = "user_id", required = true) String userId) {
        if (!securityService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Optional<User> user = usersService.findUserById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with this id");
        }
        return ResponseEntity.ok(mapperUtility.userToUserDTO(user.get()));
    }


    @GetMapping(path = "/user/dashboard/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDashboardSummary(
            final @RequestParam(name = "Authorization",required = true) String token,
            @RequestParam(name = "user_id", required = true) String userId
    ) {
        if (!securityService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Optional<User> user = usersService.findUserById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id does not exist");
        }
        DashboardSummaryDTO dashboardSummary = usersService.getDashboardSummary(user.get());

        return ResponseEntity.ok(dashboardSummary);
    }


    @GetMapping(path = "/user/badges/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserBadgesSummary(
            final @RequestParam(name = "Authorization",required = true) String token,
            @RequestParam(name = "userRole", required = true) UserRole userRole
    ) {
        if (!securityService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        if (!userRole.equals(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You should be admin to create a new badge");
        }

        List<UserBadgesSummaryDTO> userBadgesSummary = usersService.getUserBadgesSummary();

        return ResponseEntity.ok(userBadgesSummary);
    }

    @GetMapping(path = "/user/general/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserGeneralDetails(
            final @RequestParam(name = "Authorization", required = true) String token,
            @RequestParam(name = "user_id", required = true) String userId
    ) {
        if (!securityService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        Optional<User> user = usersService.findUserById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id does not exist");
        }
        UserGeneralInfoDTO userGeneralInfo = usersService.getUserGeneralInformation(user.get());

        return ResponseEntity.ok(userGeneralInfo);
    }
}
