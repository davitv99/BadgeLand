package aua.badges.utility;

import aua.badges.dao.ImageDAO;
import aua.badges.dto.BadgeDTO;
import aua.badges.dto.ImageDTO;
import aua.badges.dto.UserDTO;
import aua.badges.enums.Gender;
import aua.badges.enums.UserRole;
import aua.badges.model.Badge;
import aua.badges.model.Image;
import aua.badges.model.User;
import aua.badges.requests.UserCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author davitv
 */
@Service
@Slf4j
public class MapperUtility {
    @Autowired
    private ImageDAO imageDAO;


    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setGender(user.getGender().getValue());
        userDTO.setRole(user.getRole().getValue());
        return userDTO;
    }

    public List<UserDTO> usersToUserDTOS(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTO.setGender(user.getGender().getValue());
            userDTO.setRole(user.getRole().getValue());
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public User userCreationRequestToUser(UserCreationRequest userCreationRequest) {
        User user = new User();
        BeanUtils.copyProperties(userCreationRequest, user);
        user.setGender(Gender.valueOf(userCreationRequest.getGender()));
        user.setRole(UserRole.valueOf(userCreationRequest.getRole()));
        return user;
    }

    public List<BadgeDTO> badgesToBadgeDTOS(List<Badge> badges) {
        List<BadgeDTO> badgeDTOS = new ArrayList<>();
        for (Badge badge : badges) {
            BadgeDTO badgeDTO = new BadgeDTO();
            BeanUtils.copyProperties(badge, badgeDTO);
            if(badge.getIconId()!=null){
                imageDAO.getImageById(badge.getIconId()).ifPresent(image -> badgeDTO.setIcon(image.getFileContent()));
            }
            badgeDTOS.add(badgeDTO);
        }
        return badgeDTOS;
    }



    public List<ImageDTO> imagesToImageDTOS(List<Image> images) {
        List<ImageDTO> imageDTOS = new ArrayList<>();
        for (Image image : images) {
            ImageDTO imageDTO = new ImageDTO();
            BeanUtils.copyProperties(image, imageDTO);
            imageDTOS.add(imageDTO);
        }
        return imageDTOS;
    }
}
