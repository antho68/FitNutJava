package org.aba.web.controller;

import org.aba.web.controller.searchForm.AbstractSearchForm;
import org.aba.web.utils.CommonUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

}
