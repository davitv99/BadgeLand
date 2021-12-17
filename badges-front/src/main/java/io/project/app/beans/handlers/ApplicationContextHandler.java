package io.project.app.beans.handlers;

import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.PropertyResourceBundle;

@Named
@ApplicationScoped
public class ApplicationContextHandler implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ApplicationContextHandler.class);


    private String gatewayUrl = this.getBackendPathBundle().getString("gateway");

    public ApplicationContextHandler() {
    }

    @PostConstruct
    public void init() {
        LOGGER.info("ApplicationContextHandler init");
        gatewayUrl = this.getBackendPathBundle().getString("gateway");

    }

    public PropertyResourceBundle getBackendPathBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{path}", PropertyResourceBundle.class);
    }


    public String getAccountServicePath() {
        return gatewayUrl;
    }


    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

}
