package io.project.app.beans.badges;

import io.project.app.dto.DashboardSummaryDTO;
import io.project.app.http.clients.account.UserInfoClient;
import io.project.app.security.SessionContext;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named(value = "dashboardBean")
@ViewScoped
public class DashboardBean implements Serializable {


    private static final Logger LOGGER = Logger.getLogger(io.project.app.beans.profile.ProfileBean.class);
    private static final long serialVersionUID = -8730742845262768978L;


    @Inject
    private SessionContext sessionContext;

    @Inject
    private UserInfoClient userInfoClient;


    private DashboardSummaryDTO dashboardSummary = null;


    public DashboardBean() {

    }

    @PostConstruct
    public void init() {
        LOGGER.info("Dashboard bean init");
        if (sessionContext.getUser().getId() != null) {
            dashboardSummary = userInfoClient.getDashboardSummary(sessionContext.getUser().getId(), sessionContext.getSessionToken());
        }
    }


    public DashboardSummaryDTO getDashboardSummary() {
        return dashboardSummary;
    }


}


