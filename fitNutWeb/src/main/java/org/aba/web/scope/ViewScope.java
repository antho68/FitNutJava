package org.aba.web.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.util.Map;


/**
 * JSF View Scope
 */
public class ViewScope implements Scope
{
    public Object get(String name, ObjectFactory<?> objectFactory)
    {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        if (viewRoot != null)
        {
            Map<String, Object> viewMap = viewRoot.getViewMap();
            if (viewMap.containsKey(name))
            {
                return viewMap.get(name);
            }
            else
            {
                Object object = objectFactory.getObject();
                viewMap.put(name, object);
                return object;
            }
        }
        else
        {
            return null;
        }
    }

    public Object remove(String name)
    {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        if (viewRoot != null)
        {
            return viewRoot.getViewMap().remove(name);
        }
        else
        {
            return null;
        }
    }

    public void registerDestructionCallback(String name, Runnable callback)
    {
        // Do nothing
    }

    public Object resolveContextualObject(String key)
    {
        return null;
    }

    public String getConversationId()
    {
        return null;
    }
}