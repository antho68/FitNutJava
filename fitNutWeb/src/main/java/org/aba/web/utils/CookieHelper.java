package org.aba.web.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper
{

    public static void setCookie(String name, String value, int expiry)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        Cookie[] userCookies = request.getCookies();

        if (userCookies != null && userCookies.length > 0)
        {
            for (int i = 0; i < userCookies.length; i++)
            {
                if (userCookies[i].getName().equals(name))
                {
                    cookie = userCookies[i];
                    cookie.setMaxAge(expiry);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                }
            }
        }

        if (cookie == null && expiry > 0)
        {
            cookie = new Cookie(name, value);
            cookie.setMaxAge(expiry);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }
    }

    public static String getCookie(String name)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0)
        {
            for (int i = 0; i < userCookies.length; i++)
            {
                if (userCookies[i].getName().equals(name))
                {
                    cookie = userCookies[i];
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
