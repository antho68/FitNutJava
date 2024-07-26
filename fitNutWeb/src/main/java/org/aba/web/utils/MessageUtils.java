package org.aba.web.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Iterator;
import java.util.Locale;


/**
 * helper methods for internationalizing
 *
 * @author aba
 */
public class MessageUtils
{
    private static synchronized FacesContext getFacesContext()
    {
        return FacesContext.getCurrentInstance();
    }

    private static Locale getLocale()
    {
        return getLocale(getFacesContext());
    }

    private static Locale getLocale(FacesContext facesContext)
    {
        return facesContext.getViewRoot().getLocale();
    }

    public static void clearMessageList()
    {
        FacesContext facesContext = getFacesContext();

        MessageHandler msh = (MessageHandler) CommonUtils.getSpringManagedBean("messageHandler");
        msh.clearMessages(facesContext);

        if (!facesContext.getMessageList().isEmpty())
        {
            Iterator<FacesMessage> messages = facesContext.getMessages();
            while (messages.hasNext())
            {
                messages.next();
                messages.remove();
            }
        }
    }

    public static boolean hasErrorMessage()
    {
        return hasMessageWithSeverity(getFacesContext(), FacesMessage.SEVERITY_ERROR);
    }

    private static boolean hasMessageWithSeverity(FacesContext facesContext, FacesMessage.Severity severity)
    {
        Iterator<FacesMessage> messages = facesContext.getMessages();

        FacesMessage message;
        do
        {
            if (!messages.hasNext())
            {
                return false;
            }

            message = messages.next();
        } while (!severity.equals(message.getSeverity()));

        return true;
    }

    public static void addErrorMessageText(String text)
    {
        FacesContext facesContext = getFacesContext();
        facesContext.addMessage((String) null, createErrorMessageText(text, (String) null));
    }

    private static FacesMessage createErrorMessageText(String textSummary, String textDetail)
    {
        return createMessage(FacesMessage.SEVERITY_ERROR, textSummary, textDetail);
    }

    public static FacesMessage createMessage(FacesMessage.Severity severity, String messageSummary
            , String messageDetail)
    {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail(messageDetail);
        facesMessage.setSummary(messageSummary);
        facesMessage.setSeverity(severity);
        return facesMessage;
    }
}