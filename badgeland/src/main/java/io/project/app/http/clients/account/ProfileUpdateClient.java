package io.project.app.http.clients.account;



import io.project.app.beans.handlers.ApplicationContextHandler;

import io.project.app.dto.ImageDTO;
import io.project.app.dto.UserDTO;
import io.project.app.dto.UserGeneralInfoDTO;
import io.project.app.requests.UserPasswordChangeRequest;
import io.project.app.security.SessionContext;
import io.project.app.util.FrontendConstants;
import io.project.app.util.FrontendGsonConverter;
import io.project.app.util.GsonConverter;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named(value = "profileUpdateClient")
@Dependent
public class ProfileUpdateClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(ProfileUpdateClient.class);

    @Inject
    private ApplicationContextHandler applicationContextHandler;

    private String service_path;

    @Inject
    private SessionContext sessionContext;

    public ProfileUpdateClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("ProfileUpdateClient called");
        service_path = applicationContextHandler.getAccountServicePath() + "api/users";
    }

    public int changePassword(UserPasswordChangeRequest userPasswordChangeRequest) {

        int status = 0;

        long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOGGER.info("Change Password called ");
            HttpPut request = new HttpPut(service_path + "/edit/user/password");

            String toJson = GsonConverter.toJson(userPasswordChangeRequest);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("authorization", sessionContext.getSessionToken());
            request.setEntity(params);
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    LOGGER.info("Password update status is " + status);
                    status = 200;
                } else {
                    status = 500;
                }
            }

            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("ChangePassword request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return status;
    }

    public UserGeneralInfoDTO updateAccountProfile(String id, String about, String name, ImageDTO avatar) {

        UserGeneralInfoDTO returnedModel = new UserGeneralInfoDTO();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {


            HttpPut request = new HttpPut(service_path + "/edit/user");
            request.addHeader("authorization", sessionContext.getSessionToken());
            request.addHeader("about",id);
            request.addHeader("user_id",about);
            request.addHeader("userName",name);
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            if(avatar.getFileContent() !=null){
                String toJson = GsonConverter.toJson(avatar);
                StringEntity params = new StringEntity(toJson, "UTF-8");
                request.setEntity(params);
            }
            long startTime = System.currentTimeMillis();
            LOGGER.info("request " + request.toString());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status code " + httpResponse.getStatusLine().getStatusCode());
                LOGGER.info("" + httpResponse.getStatusLine().getReasonPhrase());
                LOGGER.info("" + httpResponse.getStatusLine().toString());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {

                    returnedModel = FrontendGsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), UserGeneralInfoDTO.class);


                    return returnedModel;
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return returnedModel;
    }


    public UserGeneralInfoDTO getUserGeneralInfo(String userId) {

        UserGeneralInfoDTO returnedModel = new UserGeneralInfoDTO();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(service_path + "/user/general/info");
            request.addHeader("authorization", sessionContext.getSessionToken());
            request.addHeader("user_id", userId);
            long startTime = System.currentTimeMillis();
            LOGGER.info("request " + request.toString());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status code " + httpResponse.getStatusLine().getStatusCode());
                LOGGER.info("" + httpResponse.getStatusLine().getReasonPhrase());
                LOGGER.info("" + httpResponse.getStatusLine().toString());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {

                    returnedModel = FrontendGsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), UserGeneralInfoDTO.class);


                    return returnedModel;
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return returnedModel;
    }


}
