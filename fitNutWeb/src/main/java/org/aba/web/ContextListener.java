package org.aba.web;

import org.aba.web.manager.ApplicationManager;
import org.aba.web.scheduling.CronConfigReloadCheck;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;

import javax.servlet.ServletContextEvent;


/**
 * Listener, called at startup and shutdown of context
 *
 * @author aba
 */
public class ContextListener extends AbstractContextListener
{
    private static final long serialVersionUID = -7849746628455448418L;

    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        super.contextInitialized(event);

        try
        {
            ApplicationManager am = (ApplicationManager) CommonUtils.getSpringManagedBean(ConstantsWeb.Beans.APPLICATION_MANAGER);

            CronConfigReloadCheck cronConfigReloadCheck =
                    (CronConfigReloadCheck) CommonUtils.getSpringManagedBean(ConstantsWeb.Beans.CRON_CONFIG_RELOAD_CHECK);
            cronConfigReloadCheck.runScheduled();

        }
        catch (Exception e)
        {
            CommonUtils.logError(ConstantsWeb.ERROR_LOG, e);
        }
    }
}