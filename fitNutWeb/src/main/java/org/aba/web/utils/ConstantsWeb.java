package org.aba.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ConstantsWeb
{
    Logger APPL_LOG = LoggerFactory.getLogger("applLogger");
    Logger DEBUG_LOG = LoggerFactory.getLogger("debugLogger");
    Logger ERROR_LOG = LoggerFactory.getLogger("errorLogger");

    public final static int FALLBACK_AUTOLOGOUT_MINUTES = 600;
    String AUTH_COOKIE = "#123FGH456PF11$$$";
    String DEFAULT_APPNAME = "fitNut";

    interface Configurations
    {
        interface ConfigFileProperties
        {
            public final static String CONFIG_FILELOCATION = "configFileLocationFitNutWeb";
            public final static String BASE_URL = "base.url";
        }
    }

    public interface SystemStages
    {
        String DEVELOPMENT = SystemStage.DEVELOPMENT.getCode();
        String PRODUCTION = SystemStage.PRODUCTION.getCode();
    }

    interface Beans
    {
        String SESSION_BEAN = "sessionBean";
        String SESSION_MANAGER = "sessionManager";
        String APPLICATION_MANAGER = "applicationManager";
        String CRON_CONFIG_RELOAD_CHECK = "cronConfigReloadCheck";
    }

    interface Navigation
    {
        interface Common
        {
            String ERROR = "error";
            String HOME_PAGE = "/home.xhtml";
            String MY_DATA = "/misc/myData.xhtml";
        }
    }

    public interface Report
    {
        public final static String REPORT_IMAGE_DIRECTORY = "resources/images/";
    }

    interface Endpoints
    {
        interface HubConnector
        {
            public interface Properties // POST
            {
                String PK = "pk";
                String PARAMS = "params";
                String ENTITY_NAME = "entityName";
                String USER_ID = "userId";
                String GUI_LOG_ID = "guiLogId";
            }
        }
    }

    public interface Error
    {
        int UNKNOWN = -1;
        int CONNECT = 3000; // connect not possible
        int HTTP = 3001; // http error
    }
}
