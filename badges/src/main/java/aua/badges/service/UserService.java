package aua.badges.service;

import aua.badges.dao.AssignmentDAO;
import aua.badges.dao.BadgeDAO;
import aua.badges.dao.ImageDAO;
import aua.badges.dao.UserDAO;
import aua.badges.dto.*;
import aua.badges.enums.ImageCategory;
import aua.badges.model.Image;
import aua.badges.model.User;
import aua.badges.requests.UserCreationRequest;
import aua.badges.utility.MapperUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author davitv
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BadgeDAO badgeDAO;
    @Autowired
    private AssignmentDAO assignmentDAO;
    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private ImageDAO imageDAO;

    public Optional<User> findUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    public UserDTO createUser(UserCreationRequest userCreationRequest) {
        User user = mapperUtility.userCreationRequestToUser(userCreationRequest);
        return mapperUtility.userToUserDTO(userDAO.saveUser(user));
    }

    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

    public Optional<User> findUserById(String userId) {
        return userDAO.findUserById(userId);
    }

    public DashboardSummaryDTO getDashboardSummary(User user) {
        DashboardSummaryDTO dashboardSummary = new DashboardSummaryDTO();
        dashboardSummary.setName(user.getName());
        dashboardSummary.setReceivedBadges(badgeDAO.findUserCurrentBadges(user.getId()).size());
        dashboardSummary.setPendingBadges(assignmentDAO.findAssignmentRequestByUserIdAndPendingStatus(user.getId()).size());
        dashboardSummary.setRejectedBadges(assignmentDAO.findAssignmentRequestByUserIdAndRejectedStatus(user.getId()).size());
        return dashboardSummary;
    }

    public List<UserBadgesSummaryDTO> getUserBadgesSummary() {
        List<User> users = userDAO.findAllUsers();
        if (users.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserBadgesSummaryDTO> userBadgesSummaryDTOS = new ArrayList<>();
        for (User user : users) {
            UserBadgesSummaryDTO userBadgesSummaryDTO = new UserBadgesSummaryDTO();
            userBadgesSummaryDTO.setEmail(user.getEmail());
            userBadgesSummaryDTO.setName(user.getName());
            userBadgesSummaryDTO.setNumberOfBadges(badgeDAO.findUserCurrentBadges(user.getId()).size());
            userBadgesSummaryDTOS.add(userBadgesSummaryDTO);
        }
        return userBadgesSummaryDTOS;
    }

    public UserGeneralInfoDTO getUserGeneralInformation(User user) {
        UserGeneralInfoDTO userGeneralInfo = new UserGeneralInfoDTO();
        userGeneralInfo.setName(user.getName());
        userGeneralInfo.setAbout(user.getAbout());
        userGeneralInfo.setGender(user.getGender());

        Optional<Image> userAvatar = imageDAO.getImageById(user.getAvatarId());
        userAvatar.ifPresent(image -> userGeneralInfo.setAvatar(image.getFileContent()));
        userGeneralInfo.setRole(user.getRole());
        userGeneralInfo.setEmail(user.getEmail());
        userGeneralInfo.setId(user.getId());

        return userGeneralInfo;
    }

    public void updateUserPassword(User user, String password) {
        user.setPassword(password);
        userDAO.saveUser(user);
    }

    public User updateUser(String name, String about, User user, ImageDTO avatar) {
        if (name != null) {
            user.setName(name);
        }
        if (about != null) {
            user.setAbout(about);
        }
        if (avatar != null) {
            Image image = new Image();
            image.setImageType(ImageCategory.ACCOUNT);
            image.setFileContent(avatar.getFileContent());
            image.setFileSize(avatar.getFileSize());
            image.setFileName(user.getName() + " Avatar");
            image.setContentType(avatar.getContentType());
            imageDAO.saveImage(image);
            if (user.getAvatarId() != null) {
                imageDAO.deleteImageById(user.getAvatarId());
            }
            user.setAvatarId(image.getId());
        }
        userDAO.saveUser(user);
        return user;
    }

}