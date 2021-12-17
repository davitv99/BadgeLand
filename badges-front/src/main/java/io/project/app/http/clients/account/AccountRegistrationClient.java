package io.project.app.http.clients.account;


import io.project.app.beans.handlers.ApplicationContextHandler;
import io.project.app.requests.UserCreationRequest;
import io.project.app.responses.RegisterResponse;
import io.project.app.util.FrontendConstants;
import io.project.app.util.FrontendGsonConverter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
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
public class AccountRegistrationClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(AccountRegistrationClient.class);

    @Inject
    private ApplicationContextHandler applicationContextHandler;

    private String service_path;

    public AccountRegistrationClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("AccountRegistrationClient called");
        service_path = applicationContextHandler.getAccountServicePath() + "api/users";
    }


    @SuppressWarnings("UnusedAssignment")
    public RegisterResponse registerNewAccount(UserCreationRequest register) {
        RegisterResponse returnedModel = new RegisterResponse();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(service_path + "/create");
            String jsonInString = FrontendGsonConverter.toJson(register);
            StringEntity params = new StringEntity(jsonInString, "UTF-8");
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            LOGGER.info("request " + request.toString());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = FrontendGsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), RegisterResponse.class);
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
