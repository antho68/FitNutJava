package org.aba.web.manager;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * Common manager for the application
 */
public interface ApplicationManager
{
    /**
     * get the applDate
     *
     * @return
     */
    Date getApplDate();

    /**
     * get all accessable urls without login
     *
     * @return
     */
    Collection<String> getOpenURLs();

    /**
     * reload text bundle!
     */
    void reloadBundle();

    void reloadBundle(Locale locale);

    /**
     * clear all caches
     */
    void clearCaches();

    void resetApplDate();

    /**
     * @return
     */
    String getSystemStage();

    /**
     * is it a development system?
     *
     * @return
     */
    boolean isDevelopmentSystem();

    /**
     * is it a production system?
     *
     * @return
     */
    boolean isProductionSystem();
}