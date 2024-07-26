package org.aba.web.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils implements Serializable
{
    private static final long serialVersionUID = 3858849234772896654L;

    public static Locale LOCALE_DE_CH = new Locale("de", "CH");
    public static String PATTERN_DATE_TIME_GERMAN = "dd.MM.yyyy HH:mm:ss";
    public static String BASE_URL = "Undefined";
    private static String REPORT_URL = "Undefined";

    public static synchronized void navigateTo(String navigateTo, boolean withRedirect)
    {
        try
        {
            if (StringUtils.isEmpty(navigateTo))
            {
                navigateTo = "timeout";
            }
            else if (withRedirect)
            {
                navigateTo = navigateTo + (navigateTo.contains("?") ? "&" : "?") + "faces-redirect=true";
            }

            ConfigurableNavigationHandler navigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
            navigationHandler.performNavigation(navigateTo);
        }
        catch (Exception var3)
        {
            CommonUtils.logError(ConstantsWeb.ERROR_LOG, var3.getMessage(), var3);
        }
    }

    public static String getSystemStage()
    {
        return getSystemStage(getAdditionalWebAppName());
    }

    public static String getSystemStage(String additionalName)
    {
        return getSystemStage(ConstantsWeb.Configurations.ConfigFileProperties.CONFIG_FILELOCATION, additionalName);
    }

    public static String getSystemStage(String configFileLocation, String additionalName)
    {
        return getConfigFilePropertyWithFallback(configFileLocation, additionalName, "system.stage", ConstantsWeb.SystemStages.PRODUCTION);
    }

    public static String getConfigFilePropertyWithFallback(String configFileLocation, String additionalName, String propertyName, String fallback)
    {
        String property = fallback;

        try
        {
            Properties properties = getConfigFileProperties(configFileLocation + WordUtils.capitalize(StringUtils.defaultString(additionalName)));
            if (properties != null)
            {
                property = properties.getProperty(propertyName, fallback);
            }
        }
        catch (IOException var7)
        {
            logError(ConstantsWeb.ERROR_LOG, var7.getMessage(), var7);
        }

        return property;
    }

    public static Properties getConfigFileProperties(String configFileParam) throws IOException
    {
        Properties properties = null;
        File file = getConfigFile(configFileParam);
        if (file != null)
        {
            InputStream inp = null;

            try
            {
                inp = new FileInputStream(file);
                if (inp != null)
                {
                    properties = new Properties();
                    properties.load(inp);
                }
            }
            finally
            {
                if (inp != null)
                {
                    inp.close();
                }

            }
        }
        else
        {
            logWarn(ConstantsWeb.ERROR_LOG, "No valid config file given!");
        }

        return properties;
    }

    public static File getConfigFile(String systemPropertyPathToFile)
    {
        String fileLocation = System.getProperty(systemPropertyPathToFile);
        if (fileLocation != null)
        {
            File file = getFile(fileLocation);
            if (file != null)
            {
                return file;
            }

            logError(ConstantsWeb.ERROR_LOG, "Error reading file for systemPropertyPathToFile >" + systemPropertyPathToFile + "<!");
        }

        logWarn(ConstantsWeb.ERROR_LOG, "No system property for >" + systemPropertyPathToFile + "< found in startup!");
        return null;
    }

    public static File getFile(String pathToFile)
    {
        if (!StringUtils.isEmpty(pathToFile))
        {
            pathToFile = pathToFile.replaceAll("file:", "");
            File file = new File(pathToFile);
            if (file != null && file.exists())
            {
                return file;
            }

            logError(ConstantsWeb.ERROR_LOG, "Error reading file for path >" + pathToFile + "<!");
        }

        logError(ConstantsWeb.ERROR_LOG, "No pathToFile given >" + pathToFile + "<!");
        return null;
    }

    private static String getAdditionalWebAppName()
    {
        ServletContext servletContext = CommonUtils.getServletContext();
        if (servletContext != null && StringUtils.isNotEmpty(servletContext.getContextPath()))
        {
            return servletContext.getContextPath().replaceAll("/" + ConstantsWeb.DEFAULT_APPNAME, "");
        }
        else
        {
            return "";
        }
    }

    public static Date getActualApplDateWithFallback()
    {
        Date date = CommonContextHolder.getApplDate();
        return date == null ? getDate() : date;
    }

    public static Date getDate()
    {
        return getCalendar().getTime();
    }

    public static ServletContext getServletContext()
    {
        if (AppContext.getApplicationContext() == null)
        {
            return null;
        }
        return ((WebApplicationContext) AppContext.getApplicationContext()).getServletContext();
    }

    public static String getDateAndTimeLongFormatted(Date date)
    {
        return getDateFormatted(date, PATTERN_DATE_TIME_GERMAN);
    }

    public static String getDateFormatted(Date date, String pattern)
    {
        if (date == null)
        {
            return "";
        }
        else
        {
            DateFormat dfmt = new SimpleDateFormat(pattern, getLocaleFromContextWithFallback());
            dfmt.setCalendar(getCalendar());
            return dfmt.format(date);
        }
    }

    public static Locale getLocaleFromContextWithFallback()
    {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale == null)
        {
            locale = LOCALE_DE_CH;
        }

        return locale;
    }

    public static Calendar getCalendar()
    {
        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(getTimeZone());
        cal.setFirstDayOfWeek(2);
        return cal;
    }

    public static TimeZone getTimeZone()
    {
        return getTimeZone("CET");
    }

    public static TimeZone getTimeZone(String timeZone)
    {
        return TimeZone.getTimeZone(timeZone);
    }

    public static Date getDateWithMinutesOffset(Date date, int minutesOffset)
    {
        return getDateWithOffset(date, 12, minutesOffset);
    }

    public static Date getDateWithOffset(Date date, int field, int value)
    {
        if (date == null)
        {
            return null;
        }
        else
        {
            Calendar cal = getCalendar(date);
            cal.add(field, value);
            return cal.getTime();
        }
    }

    public static Calendar getCalendar(Date date)
    {
        if (date == null)
        {
            return null;
        }
        else
        {
            Calendar cal = getCalendar();
            cal.setTime(date);
            return cal;
        }
    }


    public static void logError(Logger logger, Throwable t)
    {
        logError(logger, t.getMessage(), t);
    }

    public static void logError(Logger logger, String message, Throwable t)
    {
        logger.error(getLogId() + ": " + StringUtils.defaultString(message, "message is null!"), t);
    }

    public static void logInfo(Logger logger, String message)
    {
        logger.info(getLogId() + ": " + message);
    }

    public static void logDebug(Logger logger, String message)
    {
        logger.debug(getLogId() + ": " + message);
    }

    private static String getLogId()
    {
        return StringUtils.defaultString(CommonContextHolder.getId(), "LOG_ID NOT SET");
    }

    public static void logWarn(Logger logger, String message)
    {
        logger.warn(getLogId() + ": " + message);
    }

    public static void logError(Logger logger, String message)
    {
        logger.error(getLogId() + ": " + message);
    }

    public static <T> T getSpringManagedBean(Class<T> persistentClass)
    {
        LinkedHashMap<String, T> x = (LinkedHashMap) getSpringManagedBeans(persistentClass);
        return x != null && !x.isEmpty() ? x.values().iterator().next() : null;
    }

    public static Object getSpringManagedBeans(Class<?> persistentClass)
    {
        return getApplicationContext().getBeansOfType(persistentClass);
    }

    public static ApplicationContext getApplicationContext()
    {
        return AppContext.getApplicationContext();
    }

    public static Object getSpringManagedBean(String name)
    {
        return getApplicationContext().getBean(name);
    }

    public static String getStackTrace(Throwable error)
    {
        try
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            error.printStackTrace(pw);
            return sw.toString();
        }
        catch (Exception var3)
        {
            return var3.toString();
        }
    }

    public static String getLog4JFileLocation(String configFileLocation, String additionalName)
    {
        String log4JFileLocation = null;

        try
        {
            Properties properties = getConfigFileProperties(configFileLocation + WordUtils.capitalize(StringUtils.defaultString(additionalName)));
            if (properties != null)
            {
                log4JFileLocation = properties.getProperty("log4j.fileLocation", (String) null);
            }
        }
        catch (IOException var4)
        {
            logError(ConstantsWeb.ERROR_LOG, var4.getMessage(), var4);
        }

        return log4JFileLocation;
    }

    public static String getBaseUrl(String configLocation, String additionalName)
    {
        String baseUrl = CommonUtils.getConfigFileProperty(configLocation, additionalName
                , ConstantsWeb.Configurations.ConfigFileProperties.BASE_URL);

        if (baseUrl == null)
        {
            baseUrl = CommonUtils.getConfigFilePropertyWithFallback(configLocation, additionalName
                    , ConstantsWeb.Configurations.ConfigFileProperties.BASE_URL,
                    "Undefined");
        }

        return baseUrl;
    }

    public static String getConfigFileProperty(String configFileLocation, String additionalName, String propertyName)
    {
        String property = null;

        try
        {
            Properties properties = getConfigFileProperties(configFileLocation + WordUtils.capitalize(StringUtils.defaultString(additionalName)));
            if (properties != null)
            {
                property = properties.getProperty(propertyName);
            }
        }
        catch (IOException var6)
        {
            logError(ConstantsWeb.ERROR_LOG, var6.getMessage(), var6);
        }

        return property;
    }

    public static void initBaseUrl(String baseUrl)
    {
        BASE_URL = baseUrl;
    }

    public static void initReportUrl(String reportUrl)
    {
        REPORT_URL = reportUrl;
    }

    public static void logTrace(Logger logger, String message)
    {
        logger.trace(getLogId() + ": " + message);
    }

    public static boolean isCollectionEmpty(Collection<?> collection)
    {
        return collection == null || collection.isEmpty();
    }
}
