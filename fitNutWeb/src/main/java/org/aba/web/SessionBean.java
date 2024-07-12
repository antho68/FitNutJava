package org.aba.web;

import org.aba.web.manager.ApplicationManager;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.aba.web.utils.CookieHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Bean for session specific data
 *
 * @author aba
 */
@Component("sessionBean")
@Scope("session")
public class SessionBean implements Serializable
{
    private static final long serialVersionUID = 2416174991497702624L;

    @Autowired
    private ApplicationManager applicationManager;

    private String logoName;
    private String autoLogin = null;
    private boolean skipAutoLogin = false;
    private boolean skipChangePassword = false;
    private Map<String, Double> datatableScrollheightByController = new HashMap<>();
    private Double lastScrollHeight = null;
    private Date lastAccessedTs;
    private boolean shouldInitSupplier = false;
    private String lastPageCalled = "";
    private String sessionId;
    private boolean loggedIn;

    @PostConstruct
    public void init()
    {
        setAutoLogoutTimeout(ConstantsWeb.FALLBACK_AUTOLOGOUT_MINUTES);
    }

    public String getLogo()
    {
        return logoName;
    }

    @Override
    public String toString()
    {
        return "SessionBean [sessionId=" + getSessionId() + "]";
    }

    public String getAutoLogin()
    {
        return autoLogin;
    }

    public void setAutoLogin(String autoLogin)
    {
        this.autoLogin = autoLogin;
    }

    public boolean isSkipAutoLogin()
    {
        return skipAutoLogin;
    }

    public void setSkipAutoLogin(boolean skipAutoLogin)
    {
        this.skipAutoLogin = skipAutoLogin;
    }

    public boolean isSkipChangePassword()
    {
        return skipChangePassword;
    }

    public void setSkipChangePassword(boolean skipChangePassword)
    {
        this.skipChangePassword = skipChangePassword;
    }

    public void logout()
    {
        // Faces.removeResponseCookie(KranConstantsWeb.AUTH_COOKIE, "/");
        CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG, "clear login Cookie");
        CookieHelper.setCookie(getCookieName(), "undefined", 0);

        setLoggedIn(false);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        CommonUtils.navigateTo(ConstantsWeb.Navigation.Common.HOME_PAGE + "?keepConnected=false", true);
    }

    public void myData()
    {
        CommonUtils.navigateTo(ConstantsWeb.Navigation.Common.MY_DATA, true);
    }

    public void setDatatableScrollheightByController(String controllerId, Double scrollHeight)
    {
        this.datatableScrollheightByController.put(controllerId, scrollHeight);
        this.lastScrollHeight = scrollHeight;
    }

    public Double getDatatableScrollheightByController(String controllerId)
    {
        Double result = this.datatableScrollheightByController.get(controllerId);
        if (result == null && lastScrollHeight != null)
        {
            return lastScrollHeight;
        }
        return result;
    }

    public String getCookieName()
    {
        return ConstantsWeb.AUTH_COOKIE + applicationManager.getSystemStage();
    }

    protected void setAutoLogoutTimeout(Integer autoLogoutTimeout)
    {
        if (autoLogoutTimeout == null)
        {
            autoLogoutTimeout = ConstantsWeb.FALLBACK_AUTOLOGOUT_MINUTES;
        }

        setAutoLogoutTimeout(autoLogoutTimeout);
    }

    public boolean isShouldInitSupplier()
    {
        return shouldInitSupplier;
    }

    public void setShouldInitSupplier(boolean shouldInitSupplier)
    {
        this.shouldInitSupplier = shouldInitSupplier;
    }

    public void keepAlive()
    {
        applicationManager.getApplDate();
    }

    public void idleListener()
    {
        if (!isUserLoggedIn())
        {
            if (StringUtils.isNotEmpty(getLastPageCalled()))
            {
                CommonUtils.logInfo(ConstantsWeb.DEBUG_LOG, "idleListener - isUserLoggedIn, go to: " + getLastPageCalled());
                CommonUtils.navigateTo(getLastPageCalled(), true);
                return;
            }

            CommonUtils.logInfo(ConstantsWeb.DEBUG_LOG, "idleListener isUserLoggedIn");
            CommonUtils.navigateTo(ConstantsWeb.Navigation.Common.HOME_PAGE, true);
            return;
        }

        if (StringUtils.isNotEmpty(getLastPageCalled()))
        {
            CommonUtils.logInfo(ConstantsWeb.DEBUG_LOG, "idleListener, go to: " + getLastPageCalled());
            CommonUtils.navigateTo(getLastPageCalled(), true);
            return;
        }

        CommonUtils.logInfo(ConstantsWeb.DEBUG_LOG
                , "idleListener, go to: " + ConstantsWeb.Navigation.Common.HOME_PAGE);
        CommonUtils.navigateTo(ConstantsWeb.Navigation.Common.HOME_PAGE, true);
    }

    public boolean isUserLoggedIn()
    {
        return this.loggedIn;
    }

    public String getLastPageCalled()
    {
        return lastPageCalled;
    }

    public void setLastPageCalled(String lastPageCalled)
    {
        CommonUtils.logInfo(ConstantsWeb.DEBUG_LOG, "setLastPageCalled, go to: " + getLastPageCalled());
        this.lastPageCalled = lastPageCalled;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public Date getLastAccessedTs()
    {
        return lastAccessedTs;
    }

    public void setLastAccessedTs(Date lastAccessedTs)
    {
        this.lastAccessedTs = lastAccessedTs;
    }
}