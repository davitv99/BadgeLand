package io.project.app.locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.io.IOException;
import java.util.Locale;

public class LocalePhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    private static void redirect(FacesContext facesContext, String url) {
        try {
            facesContext.getExternalContext().redirect(url);
        } catch (IOException e) {
            // throw new FacesException("Cannot redirect to " + url + " due to IO exception.", e);
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    @Override
    public synchronized void beforePhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        ExternalContext ex = context.getExternalContext();
        try {

            String localeCode = (String) ex.getSessionMap().get("localeCode");

            if (localeCode != null) {
                ex.getSessionMap().put("localeCode", localeCode);
                context.getViewRoot().setLocale(new Locale(localeCode));
            } else {
                ex.getSessionMap().put("localeCode", "hy");
                context.getViewRoot().setLocale(new Locale("hy"));
            }

        } catch (Exception ex1) {

            // ex1.printStackTrace();
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
