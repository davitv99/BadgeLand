package io.project.app.security;


import io.project.app.dto.UserDTO;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class SessionContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SessionContext.class);

    private UserDTO user = new UserDTO();


    private String sessionToken;

    public SessionContext() {
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }





}
