//package io.project.app.util;
//
//import io.project.app.beans.schedule.ScheduleDataBean;
//import io.project.app.dto.UserDTO;
//
//import javax.el.ValueExpression;
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.convert.Converter;
//import javax.faces.convert.FacesConverter;
//
//@FacesConverter(value = "feConverter")
//public class FEConverter implements Converter {
//
//    @Override
//    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String id) {
//        ValueExpression vex =
//                ctx.getApplication().getExpressionFactory()
//                        .createValueExpression(ctx.getELContext(),
//                                "#{scheduleDataBean}", ScheduleDataBean.class);
//
//        ScheduleDataBean scheduleDataBean = (ScheduleDataBean) vex.getValue(ctx.getELContext());
//        return scheduleDataBean.getAccountById(id);
//    }
//
//    @Override
//    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object account) {
//
//        return ((UserDTO) account).getId();
//    }
//
//}
