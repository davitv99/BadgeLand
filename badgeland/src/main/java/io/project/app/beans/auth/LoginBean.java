package io.project.app.beans.auth;


import io.project.app.beans.handlers.JSFContextHandler;
import io.project.app.http.clients.account.AccountLoginClient;
import io.project.app.requests.UserLoginRequest;
import io.project.app.responses.LoginResponse;
import io.project.app.security.SessionContext;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author davitv
 */
@Named(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LoginBean.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private AccountLoginClient accountLoginClient;

    @Inject
    private JSFContextHandler jSFContextHandler;

    @Inject
    private SessionContext sessionContext;

    private UserLoginRequest loginModel = new UserLoginRequest();

    public LoginBean() {

    }

    @PostConstruct
    public void init() {
        LOGGER.info("LoginBean called");
        loginModel = new UserLoginRequest();

    }

    public String loginUser() {

        final LoginResponse loginResponse = accountLoginClient.loginRequest(loginModel);

        if (loginResponse.getUserDTO().getId() == null) {
            FacesMessage msg = new FacesMessage(jSFContextHandler.getMessageResourceBundle().getString("nouser"), jSFContextHandler.getMessageResourceBundle().getString("nouser"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }

        if (loginResponse.getUserDTO().getId() != null) {
            sessionContext.setUser(loginResponse.getUserDTO());

            LOGGER.info("User logged in successfully");

            sessionContext.setSessionToken(loginResponse.getToken());
            LOGGER.info("this is Session Token " + sessionContext.getSessionToken());
            return "dashboard";
        }

        FacesMessage msg = new FacesMessage(jSFContextHandler.getMessageResourceBundle().getString("nouser"), jSFContextHandler.getMessageResourceBundle().getString("nouser"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;

    }

    public UserLoginRequest getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(UserLoginRequest loginModel) {
        this.loginModel = loginModel;
    }

}
