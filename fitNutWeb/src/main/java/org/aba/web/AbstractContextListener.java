package org.aba.web;

import org.aba.web.manager.SessionManager;
import org.aba.web.utils.CommonContextHolder;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.Serializable;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public abstract class AbstractContextListener implements ServletContextListener, Serializable
{
    private static final long serialVersionUID = -7849746628455448418L;

    protected AbstractContextListener()
    {
    }

    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        CommonContextHolder.setId("ContextListener");
        CommonUtils.logInfo(ConstantsWeb.APPL_LOG, "Initializing context "
                + event.getServletContext().getContextPath() + " (version: " + VersionProvider.getVersion() + ")");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        CommonContextHolder.setId("ContextListener");
        CommonUtils.logInfo(ConstantsWeb.DEBUG_LOG, "Destroying context " + event.getServletContext().getContextPath());
        SessionManager sessionManager = (SessionManager) CommonUtils.getSpringManagedBean("sessionManager");
        if (sessionManager != null)
        {
            sessionManager.clearSessions();
        }

        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements())
        {
            Driver driver = drivers.nextElement();

            try
            {
                DriverManager.deregisterDriver(driver);
                CommonUtils.logInfo(ConstantsWeb.DEBUG_LOG, String.format("Deregistering jdbc driver: %s", driver));
            }
            catch (SQLException var6)
            {
                CommonUtils.logError(ConstantsWeb.ERROR_LOG, String.format("Error deregistering driver %s", driver), var6);
            }
        }

        CommonUtils.logInfo(ConstantsWeb.APPL_LOG, event.getServletContext().getContextPath() + "-Context destroyed!");
    }
}

