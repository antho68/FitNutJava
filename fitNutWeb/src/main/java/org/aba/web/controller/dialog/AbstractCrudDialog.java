package org.aba.web.controller.dialog;

import org.aba.data.domain.AbstractDomain;
import org.aba.web.SessionBean;
import org.aba.web.controller.form.AbstractCrudForm;
import org.aba.web.exeption.ServiceException;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.CrudMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
public abstract class AbstractCrudDialog<DO extends AbstractDomain, CF extends AbstractCrudForm<DO>>
        implements Serializable
{
    private static final long serialVersionUID = -6464673953748662853L;

    @Autowired
    private SessionBean sessionBean;

    protected DO selectedDo;
    private CrudMode mode;
    protected CF crudForm;

    private String dialogName = "crudDialog";
    protected Long dummyPk = -1L;
    protected boolean formChanged = false;

    @PostConstruct
    public void init()
    {
    }

    protected AbstractCrudDialog()
    {
        ParameterizedType p = this.getParameterizedType();
        Type[] actualTypeArguments = p.getActualTypeArguments();

        for (int i = 0; i < actualTypeArguments.length; ++i)
        {
            Class<CF> persistentClass = (Class) actualTypeArguments[i];
            if (AbstractCrudForm.class.isAssignableFrom(persistentClass))
            {
                this.crudForm = (CF) CommonUtils.getSpringManagedBean(persistentClass);
                break;
            }
        }

    }

    public void initDialog()
    {
        this.clearDialog();
    }


    public DO getSelectedDto()
    {
        return (DO) this.selectedDo;
    }

    public void setSelectedDto(DO domain)
    {
        this.selectedDo = domain;
        this.crudForm.setSelectedDo(domain);
    }

    public void clearDialog()
    {
        this.crudForm.clearForm();
        this.formChanged = false;
    }

    public void fillDialog(DO domain)
    {
        this.formChanged = false;
        this.selectedDo = domain;
        this.crudForm.fillForm(domain, this.getMode());
    }

    public boolean validateForm()
    {
        return this.crudForm.validateForm(this.getMode());
    }

    public List<DO> saveForm() throws ServiceException
    {
        this.formChanged = false;
        this.crudForm.save();
        return this.crudForm.getCreatedDos();
    }

    public void deleteForm()
    {
        this.crudForm.delete();
        this.formChanged = false;
    }

    public CrudMode getMode()
    {
        return this.mode;
    }

    public String getModeText()
    {
        if (this.getMode() == null)
        {
            return "";
        }

        String text = "";
        switch (this.getMode())
        {
            case ADD ->
            {
                text = "Nouveau";
            }
            case COPY ->
            {
                text = "Copier";
            }
            case DELETE ->
            {
                text = "Supprimer";
            }
            case EDIT ->
            {
                text = "Modifier";
            }
            case SHOW ->
            {
                text = "Lecture";
            }
        }

        return text;
    }

    public void setMode(CrudMode mode)
    {
        this.mode = mode;
        this.crudForm.setMode(mode);
    }

    public boolean isAddMode()
    {
        return this.mode == CrudMode.ADD;
    }

    public boolean isEditMode()
    {
        return this.mode == CrudMode.EDIT;
    }

    public boolean isDeleteMode()
    {
        return this.mode == CrudMode.DELETE;
    }

    public boolean isCopyMode()
    {
        return this.mode == CrudMode.COPY;
    }

    public boolean isShowMode()
    {
        return this.mode == CrudMode.SHOW;
    }

    public String getDialogName()
    {
        return this.dialogName;
    }

    public void setDialogName(String dialogName)
    {
        this.dialogName = dialogName;
    }

    public CF getCrudForm()
    {
        return this.crudForm;
    }

    public boolean isFormChanged()
    {
        return this.formChanged;
    }

    public void setFormChanged(boolean formChanged)
    {
        this.formChanged = formChanged;
    }

    public void inputChanged()
    {
        this.formChanged = true;
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

