package io.project.app.locale;

import lombok.Data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
@Data
public class ChangeLocale implements Serializable {

    private static final long serialVersionUID = 6187192972313171129L;

    private FacesContext context;
    private ExternalContext ex;

    private String languageCode;

    private String english = "en";


    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public ChangeLocale() {

    }

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        ex = context.getExternalContext();

    }



    public void redirectAfterChangeLocale() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf?locale=" + ex.getSessionMap().get("localeCode"));
        } catch (IOException ex) {
            Logger.getLogger(ChangeLocale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
