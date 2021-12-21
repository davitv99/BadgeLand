package io.project.app.beans.profile;

import io.project.app.dto.ImageDTO;
import io.project.app.dto.UserGeneralInfoDTO;
import io.project.app.http.clients.account.ProfileUpdateClient;
import io.project.app.requests.UserPasswordChangeRequest;
import io.project.app.requests.UserUpdateRequest;
import io.project.app.security.SessionContext;
import org.apache.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "profileBean")
@ViewScoped
public class ProfileBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ProfileBean.class);
    private static final long serialVersionUID = -8730742845262768978L;

    @Inject
    private ProfileUpdateClient profileUpdateClient;

    @Inject
    private SessionContext sessionContext;


    private UserGeneralInfoDTO userModel = null;
    private UploadedFile userAvatar;
    private String userPassword;

    public ProfileBean() {

    }

    @PostConstruct
    public void init() {
        LOGGER.info("profile bean init");
        if (sessionContext.getUser().getId() != null) {
            userModel = profileUpdateClient.getUserGeneralInfo(sessionContext.getUser().getId());
        }
    }

    public String updatePassword() {
        UserPasswordChangeRequest userPasswordChangeRequest = new UserPasswordChangeRequest(userModel.getId(), userPassword);
        int changePassword = profileUpdateClient.changePassword(userPasswordChangeRequest);

        if (changePassword != 200) {

            FacesMessage msg = new FacesMessage("Notificaation", "Password update failed");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            userPassword = null;

        } else {
            FacesMessage msg = new FacesMessage("Notificaation", "Password update success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            userPassword = null;
        }
        return null;
    }

    public String updateProfile() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setName( userModel.getName());
        userUpdateRequest.setAbout(userModel.getAbout());
        userUpdateRequest.setId(userModel.getId());

        ImageDTO imageDTO = new ImageDTO();
        if(userAvatar != null){
            imageDTO.setContentType(userAvatar.getContentType());
            imageDTO.setFileContent(userAvatar.getContent());
            imageDTO.setFileSize(userAvatar.getSize());
            userUpdateRequest.setAvatar(imageDTO);
        }

        UserGeneralInfoDTO updateProfile = profileUpdateClient.updateAccountProfile(userUpdateRequest);
        if (updateProfile.getEmail() != null) {
            sessionContext.getUser().setName(updateProfile.getName());
            FacesMessage msg = new FacesMessage("System message", "Profile updated");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error", "Profile update failed");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UserGeneralInfoDTO getUserModel() {
        return userModel;
    }

    public void setUserModel(UserGeneralInfoDTO userModel) {
        this.userModel = userModel;
    }

    public UploadedFile getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(UploadedFile userAvatar) {
        this.userAvatar = userAvatar;
    }
}
