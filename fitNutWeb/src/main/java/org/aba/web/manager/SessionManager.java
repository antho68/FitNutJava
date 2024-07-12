package org.aba.web.manager;

import org.aba.web.SessionBean;

import java.util.Map;

public interface SessionManager
{
    void addSession(String var1, SessionBean var2);

    void removeSession(String var1);

    SessionBean getSession(String var1);

    void clearSessions();

    Map<String, SessionBean> getSessions();

    void runScheduled();
}
