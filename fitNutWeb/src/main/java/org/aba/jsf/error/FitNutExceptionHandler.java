package org.aba.jsf.error;

import org.aba.web.SessionBean;
import org.aba.web.manager.SessionManager;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.primefaces.context.PrimeRequestContext;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Iterator;


/**
 * Handler for exceptions
 */
@SuppressWarnings({"deprecation"})
public class FitNutExceptionHandler extends ExceptionHandlerWrapper
{
//	private ErrorService errorService;

    private ExceptionHandler wrappedExceptionHandler;

    public FitNutExceptionHandler(ExceptionHandler wrappedExceptionHandler)
    {
        this.wrappedExceptionHandler = wrappedExceptionHandler;
    }

    @Override
    public ExceptionHandler getWrapped()
    {
        return wrappedExceptionHandler;
    }

    @Override
    public void handle() throws FacesException
    {
        Iterator<ExceptionQueuedEvent> itr = getUnhandledExceptionQueuedEvents().iterator();
        while (itr.hasNext())
        {
            ExceptionQueuedEvent event = itr.next();
            ExceptionQueuedEventContext exceptionQueuedEventContext = (ExceptionQueuedEventContext) event.getSource();
            Throwable thr = exceptionQueuedEventContext.getException();

            if (thr instanceof Throwable)
            {
                FacesContext facesContext = exceptionQueuedEventContext.getContext();

                NavigationHandler navHandler = facesContext.getApplication().getNavigationHandler();

                try
                {
                    handleThrowable(thr, facesContext.getExternalContext().getRequest());

                    if (facesContext.getPartialViewContext().isAjaxRequest() && PrimeRequestContext.getCurrentInstance() != null)
                    {
                    }
                    else
                    {
                        // showing messages is buggy when redirecting to another directory http://java.net/jira/browse/JAVASERVERFACES-2136
                        navHandler.handleNavigation(facesContext, null, ConstantsWeb.Navigation.Common.ERROR); // redirect to error and logout
                    }
                }
                catch (Exception e)
                {
                    CommonUtils.logError(ConstantsWeb.ERROR_LOG, e);
                }
                finally
                {
                    itr.remove();
                }
            }
        }
        // let the parent handle the rest
        getWrapped().handle();
    }

    /**
     * handle the Exception
     *
     * @param t
     * @param request
     * @throws Exception
     */
    private void handleThrowable(Throwable t, Object request) throws Exception
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpRequest.getSession(true);
        String sessionId = httpSession.getId();

        SessionManager sessionManager = (SessionManager) CommonUtils.getSpringManagedBean(ConstantsWeb.Beans.SESSION_MANAGER);
        SessionBean sessionBean = null;
        if (sessionManager != null)
        {
            sessionBean = sessionManager.getSession(sessionId);
        }

        StringBuffer text = new StringBuffer();
        text.append("Timestamp: ").append(CommonUtils.getDateAndTimeLongFormatted(new Date())).append(" \n");
        text.append("IP-Adresse: ").append(httpRequest.getRemoteAddr()).append(" \n");
        text.append("SessionID: ").append(sessionId).append(" \n");

        if (sessionBean != null && sessionBean.isUserLoggedIn())
        {
            String userId = "errorHandlerWeb";
//            if (sessionBean.getUser() != null)
//            {
//                userId = "" + sessionBean.getUser().getPk();
//            }
//            text.append("UserId: ").append(userId).append(" \n");
//
//            if (sessionBean.getRole() != null)
//            {
//                text.append("RoleId: ").append(sessionBean.getRole().getPk()).append(" \n");
//            }
        }
//        else if (StringUtils.isNotEmpty("" + CommonContextHolder.getUserId()))
//        {
//            text.append("UserId: ").append(CommonContextHolder.getUserId()).append(" \n");
//        }

        text.append("Exception: \n");

        if (t instanceof ViewExpiredException)
        {
            text.append(t.getMessage());
        }
        else
        {
            text.append(CommonUtils.getStackTrace(t)).append(" \n");
        }

        CommonUtils.logError(ConstantsWeb.ERROR_LOG, text.toString());
    }
}