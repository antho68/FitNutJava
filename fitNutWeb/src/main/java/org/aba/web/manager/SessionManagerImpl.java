package org.aba.web.manager;

import org.aba.web.SessionBean;
import org.aba.web.utils.CommonContextHolder;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Component("sessionManager")
public class SessionManagerImpl implements Serializable, SessionManager
{
    private static final long serialVersionUID = -6209027022706211101L;
    private Map<String, SessionBean> sessionBeans = new HashMap();
    @Value("${session.cleanup.minutesLoggedOutSessions:60}")
    private Integer minutesLoggedOutSessions;
    @Value("${session.cleanup.minutesLoggedInSessions:600}")
    private Integer minutesLoggedInSessions;

    public SessionManagerImpl()
    {
    }

    public void addSession(String sessionId, SessionBean sessionBean)
    {
        this.sessionBeans.put(sessionId, sessionBean);
    }

    public void removeSession(String sessionId)
    {
        this.sessionBeans.remove(sessionId);
    }

    public SessionBean getSession(String sessionId)
    {
        return this.sessionBeans.get(sessionId);
    }

    public void clearSessions()
    {
        this.sessionBeans.clear();
    }

    public Map<String, SessionBean> getSessions()
    {
        return this.sessionBeans;
    }

    @Scheduled(
            cron = "${session.cleanup.interval:0 0 0 * * *}"
    )
    @Async
    public void runScheduled()
    {
        CommonContextHolder.setId("SessionManager");
        CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG, "Clean up sessions " + CommonUtils.getDateAndTimeLongFormatted(new Date()) + ".");

        Date checkDateLoggedIn = CommonUtils.getDateWithMinutesOffset(new Date(), -1 * this.minutesLoggedInSessions);
        Date checkDateLoggedOut = CommonUtils.getDateWithMinutesOffset(new Date(), -1 * this.minutesLoggedOutSessions);
        Collection<String> sessionIdsToRemove = new ArrayList();
        Iterator<SessionBean> it = this.sessionBeans.values().iterator();

        while (true)
        {
            SessionBean sessionBean;
            do
            {
                if (!it.hasNext())
                {
                    Iterator var7 = sessionIdsToRemove.iterator();

                    while (var7.hasNext())
                    {
                        String sessionId = (String) var7.next();
                        this.removeSession(sessionId);
                    }

                    CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG, "Cleaned up sessions >" + sessionIdsToRemove.size() + "<.");
                    CommonContextHolder.clearAll();
                    return;
                }

                sessionBean = it.next();
            } while (!checkDateLoggedIn.after(sessionBean.getLastAccessedTs())
                    && (sessionBean.isUserLoggedIn()
                    || !checkDateLoggedOut.after(sessionBean.getLastAccessedTs())));

            sessionIdsToRemove.add(sessionBean.getSessionId());
            sessionBean.setLoggedIn(false);
        }
    }
}

