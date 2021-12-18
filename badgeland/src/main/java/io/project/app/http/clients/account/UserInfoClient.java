package io.project.app.http.clients.account;

import io.project.app.beans.handlers.ApplicationContextHandler;
import io.project.app.dto.DashboardSummaryDTO;
import io.project.app.security.SessionContext;
import io.project.app.util.FrontendConstants;
import io.project.app.util.FrontendGsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
@Slf4j
public class UserInfoClient implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(ProfileUpdateClient.class);

    @Inject
    private ApplicationContextHandler applicationContextHandler;

    private String service_path;

    @Inject
    private SessionContext sessionContext;

    public UserInfoClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("UserInfoClient called");
        service_path = applicationContextHandler.getAccountServicePath() + "api/users";
    }

    public DashboardSummaryDTO getDashboardSummary(String id,String token) {

        DashboardSummaryDTO returnedModel = new DashboardSummaryDTO();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {


            HttpGet request = new HttpGet(service_path + "/user/dashboard/summary");
            log.info("Token is" + token);
            request.addHeader("authorization", token);
            request.addHeader("user_id", id);
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            long startTime = System.currentTimeMillis();
            LOGGER.info("request " + request.toString());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status code " + httpResponse.getStatusLine().getStatusCode());
                LOGGER.info("" + httpResponse.getStatusLine().getReasonPhrase());
                LOGGER.info("" + httpResponse.getStatusLine().toString());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = FrontendGsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), DashboardSummaryDTO.class);
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
