package io.project.app.http.clients.account;



import io.project.app.beans.handlers.ApplicationContextHandler;
import io.project.app.requests.UserLoginRequest;
import io.project.app.responses.LoginResponse;
import io.project.app.security.SessionContext;
import io.project.app.util.FrontendConstants;
import io.project.app.util.FrontendGsonConverter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@Dependent
public class AccountLoginClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(AccountLoginClient.class);

    @Inject
    private ApplicationContextHandler applicationContextHandler;

    private String service_path;


    public AccountLoginClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("AccountLoginClient called");
        service_path = applicationContextHandler.getAccountServicePath() + "api/users";
    }

    public LoginResponse loginRequest(UserLoginRequest model) {

        LoginResponse returnedModel = new LoginResponse();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut request = new HttpPut(service_path + "/user/login");
            String toJson = FrontendGsonConverter.toJson(model);
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status Code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {


                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        returnedModel = FrontendGsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), LoginResponse.class);
                        return returnedModel;
                    }
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
