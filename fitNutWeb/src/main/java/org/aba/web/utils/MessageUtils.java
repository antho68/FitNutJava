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
}