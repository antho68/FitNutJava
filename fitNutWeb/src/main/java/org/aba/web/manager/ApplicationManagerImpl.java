package org.aba.web.manager;

import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Common manager for the application
 */
@Component("applicationManager")
@Scope("singleton")
@EnableScheduling
public class ApplicationManagerImpl implements Serializable, ApplicationManager
{
    private static final long serialVersionUID = -8092744034765910319L;

    private String systemStage;

    @PostConstruct
    public void init()
    {
    }

    public String getSystemStage()
    {
        if (systemStage == null)
        {
            systemStage = CommonUtils.getSystemStage();
        }

        return systemStage;
    }

    @Override
    public boolean isDevelopmentSystem()
    {
        return false;
    }

    @Override
    public boolean isProductionSystem()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getApplDate()
    {
        return CommonUtils.getActualApplDateWithFallback();
    }

    @Override
    public Collection<String> getOpenURLs()
    {
        return List.of();
    }

    @Override
    public void reloadBundle()
    {

    }

    @Override
    public void reloadBundle(Locale locale)
    {

    }

    @Override
    public void clearCaches()
    {

    }

    @Override
    public void resetApplDate()
    {

    }

    public boolean isTestSystem()
    {
        return ConstantsWeb.SystemStages.DEVELOPMENT.equals(getSystemStage());
    }
}
