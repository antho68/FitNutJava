package org.aba.web;

import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.aba.web.utils.SystemStage;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.web.Log4jServletContextListener;
import org.apache.logging.log4j.web.Log4jServletFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;

/**
 * Initializes the webapp together with web.xml
 *
 * @author aba
 */
public class WebAppInitializerFitNutWeb implements WebApplicationInitializer
{
    @Override
    public void onStartup(ServletContext servletContext)
    {
        String contextName = servletContext.getContextPath().replaceAll("/", ""); // e.g. kranWeb or kranWebArchiv
        String additionalName = contextName.replaceAll("fitNutWeb", ""); // get the additionalName, that is after /kranWeb (e.g. kranWebArchiv --> Archiv)

        servletContext.setInitParameter("webAppRootKey", contextName);
        servletContext.setInitParameter("primefaces.THEME", "saga");
        servletContext.setInitParameter("com.sun.faces.validateXml", "false");
        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE",
                SystemStage.get(CommonUtils.getSystemStage(additionalName)).getJsfProjectStage());
        servletContext.setInitParameter("javax.faces.VALIDATE_EMPTY_FIELDS", "true");
        servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
        servletContext.setInitParameter("primefaces.MOVE_SCRIPTS_TO_BOTTOM", "true");

        String log4JConfigLocation = CommonUtils.getLog4JFileLocation(
                ConstantsWeb.Configurations.ConfigFileProperties.CONFIG_FILELOCATION, additionalName);
        if (log4JConfigLocation == null && ConstantsWeb.Configurations.ConfigFileProperties.CONFIG_FILELOCATION != null)
        {
            log4JConfigLocation = CommonUtils.getLog4JFileLocation(
                    ConstantsWeb.Configurations.ConfigFileProperties.CONFIG_FILELOCATION, additionalName);
        }

        if (StringUtils.isNotEmpty(log4JConfigLocation))
        {
            Configurator.initialize(null, log4JConfigLocation);
        }

        XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
        rootContext.setConfigLocations(new String[]{"classpath*:applicationContext.xml",
                "WEB-INF/" + contextName + ".xml",
                "WEB-INF/jsf-context.xml"});

        rootContext.getEnvironment().setDefaultProfiles(contextName);

        servletContext.addListener(new Log4jServletContextListener());
        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addListener(new RequestContextListener());

        servletContext.addFilter("log4jServletFilter", Log4jServletFilter.class).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
        servletContext.addFilter("requestFilter", org.springframework.web.filter.DelegatingFilterProxy.class).addMappingForUrlPatterns(null, false, "/*");
        servletContext.addFilter("hibernateFilter", org.springframework.orm.hibernate5.support.OpenSessionInViewFilter.class).addMappingForUrlPatterns(null, false, "/*");

        servletContext.addListener(new ContextListener());

        CommonUtils.initBaseUrl(CommonUtils.getBaseUrl(
                ConstantsWeb.Configurations.ConfigFileProperties.CONFIG_FILELOCATION, additionalName));
        CommonUtils.initReportUrl(servletContext.getRealPath(ConstantsWeb.Report.REPORT_IMAGE_DIRECTORY));
    }
}