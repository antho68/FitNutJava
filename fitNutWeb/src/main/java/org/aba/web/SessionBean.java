package org.aba.web;

import org.aba.data.domain.User;
import org.aba.rest.service.LoginRestService;
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
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;


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
    @Autowired
    private LoginRestService loginRestService;

    private String logoName;
    private String lastPageCalled = "";
    private String sessionId;
    private boolean loggedIn;
    private Long autoLogoutTimeout;
    private String currentFunctionInfos;
    private User user;
    private String actualToken;
    private Date lastTokenSync = new Date();
    private Date lastAccessedTs;

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

    public void logout()
    {
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

    public String getCookieName()
    {
        return ConstantsWeb.AUTH_COOKIE + applicationManager.getSystemStage();
    }

    protected void setAutoLogoutTimeout(Integer autoLogoutTimeoutParam)
    {
        if (autoLogoutTimeoutParam == null)
        {
            autoLogoutTimeoutParam = ConstantsWeb.FALLBACK_AUTOLOGOUT_MINUTES;
        }

        this.autoLogoutTimeout = 60000L * (long) autoLogoutTimeoutParam;
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
//        CommonUtils.logInfo(ConstantsWeb.DEBUG_LOG, "setLastPageCalled was: "
//                + getLastPageCalled() + " Go to:" + lastPageCalled);
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

    public String getCurrentFunctionInfos()
    {
        return currentFunctionInfos;
    }

    public void setCurrentFunctionInfos(String currentFunctionInfos)
    {
        this.currentFunctionInfos = currentFunctionInfos;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getActualToken()
    {
        boolean shouldCheckToken = false;
        if (actualToken == null)
        {
            shouldCheckToken = true;
        }

        long difference = lastTokenSync.getTime() - new Date().getTime();
        if (difference > (30 * 60000) )
        {
            lastTokenSync = new Date();
            shouldCheckToken = true;
        }

        if (shouldCheckToken)
        {
            User user = loginRestService.login("Aba", "2fc051754");
            if (StringUtils.isNotEmpty(user.getRefreshToken()))
            {
                user.setAccessToken(user.getRefreshToken());
            }

            actualToken = user.getAccessToken();
        }

        return actualToken;
    }
}