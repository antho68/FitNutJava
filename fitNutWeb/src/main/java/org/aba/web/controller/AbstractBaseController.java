package org.aba.web.controller;

import org.aba.web.controller.searchForm.AbstractSearchForm;
import org.aba.web.utils.CommonUtils;
import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

@Component
public abstract class AbstractBaseController<SF extends AbstractSearchForm> implements Serializable
{
    private static final long serialVersionUID = 8196417318793225252L;
    protected SF searchForm;

    protected AbstractBaseController()
    {
        ParameterizedType p = getParameterizedType();
        Type[] actualTypeArguments = p.getActualTypeArguments();

        for (int i = 0; i < actualTypeArguments.length; ++i)
        {
            Class<SF> persistentClass = (Class) actualTypeArguments[i];
            if (AbstractSearchForm.class.isAssignableFrom(persistentClass))
            {
                this.searchForm = (SF) CommonUtils.getSpringManagedBean(persistentClass);
                break;
            }
        }
    }

    public void clearFilter()
    {
        searchForm.clearForm();
        search();
    }

    public void search()
    {

    }

    protected ParameterizedType getParameterizedType()
    {
        Class<?> clazz = null;

        Type type;
        for (type = null; !(type instanceof ParameterizedType); type = clazz.getGenericSuperclass())
        {
            if (clazz == null)
            {
                clazz = this.getClass();
            }
            else
            {
                clazz = clazz.getSuperclass();
            }

            if (clazz == null)
            {
                break;
            }
        }

        if (!(type instanceof ParameterizedType))
        {
            throw new IllegalStateException(type + " is not ParameterizedType");
        }
        else
        {
            return (ParameterizedType) type;
        }
    }

    public SF getSearchForm()
    {
        return searchForm;
    }

    public void setSearchForm(SF searchForm)
    {
        this.searchForm = searchForm;
    }

    public static synchronized void updateOnRequestContext(String clientId)
    {
        PrimeFaces context = PrimeFaces.current();
        if (context != null)
        {
            context.ajax().update(clientId);
        }
    }

    public static synchronized void updateOnRequestContext(Collection<String> clientIds)
    {
        PrimeFaces context = PrimeFaces.current();
        if (context != null)
        {
            context.ajax().update(clientIds);
        }
    }

    public static void showErrorDialog()
    {
        showDialog("errorDialog");
    }

    public static void showDialog(String dialogName)
    {
        executeOnRequestContext("PF('" + dialogName + "').show();");
    }

    public static void hideDialog(String dialogName)
    {
        executeOnRequestContext("PF('" + dialogName + "').hide(); " +
                "if(typeof setFocusToFilter == 'function') { setFocusToFilter(); };");
    }

    public static synchronized void executeOnRequestContext(String executeCommand)
    {
        PrimeFaces context = PrimeFaces.current();
        if (context != null)
        {
            context.executeScript(executeCommand);
        }
    }
}
