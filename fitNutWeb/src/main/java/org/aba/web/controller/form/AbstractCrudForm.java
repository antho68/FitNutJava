package org.aba.web.controller.form;

import org.aba.data.domain.AbstractDomain;
import org.aba.web.SessionBean;
import org.aba.web.exeption.ServiceException;
import org.aba.web.utils.CrudMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component()
public abstract class AbstractCrudForm<DO extends AbstractDomain>
        implements Serializable
{
    private static final long serialVersionUID = -6464673953748662853L;

    @Autowired
    protected SessionBean sessionBean;

    protected DO selectedDo;
    protected List<DO> createdDos;
    private String lastModification;
    private CrudMode mode;

    protected AbstractCrudForm()
    {
    }

    public abstract void clearForm();

    public abstract void fillForm(DO var1, CrudMode var2);

    public void setSelectedDo(DO dto)
    {
        this.selectedDo = dto;
    }

    public DO getSelectedDto()
    {
        return this.selectedDo;
    }

    public abstract void save() throws ServiceException;

    public abstract void delete();

    public abstract boolean validateForm(CrudMode var1);

    public boolean isEditable()
    {
        return this.isAddMode() || this.isCopyMode() || this.isEditMode();
    }

    public boolean isAddOrCopyMode()
    {
        return this.isAddMode() || this.isCopyMode();
    }

    public boolean isAddMode()
    {
        return this.mode == CrudMode.ADD;
    }

    public boolean isAddMode(CrudMode mode)
    {
        return mode == CrudMode.ADD;
    }

    public boolean isEditMode()
    {
        return this.mode == CrudMode.EDIT;
    }

    public boolean isEditMode(CrudMode mode)
    {
        return mode == CrudMode.EDIT;
    }

    public boolean isDeleteMode()
    {
        return this.mode == CrudMode.DELETE;
    }

    public boolean isDeleteMode(CrudMode mode)
    {
        return mode == CrudMode.DELETE;
    }

    public boolean isCopyMode()
    {
        return this.mode == CrudMode.COPY;
    }

    public boolean isCopyMode(CrudMode mode)
    {
        return mode == CrudMode.COPY;
    }

    public boolean isShowMode()
    {
        return this.mode == CrudMode.SHOW;
    }

    public boolean isShowMode(CrudMode mode)
    {
        return mode == CrudMode.SHOW;
    }

    public boolean isDeletable()
    {
        return this.selectedDo == null ? false : this.selectedDo.isDeletable();
    }

    public String getLastModification()
    {
        return this.lastModification;
    }

    public void setLastModification(String lastModification)
    {
        this.lastModification = lastModification;
    }

    public CrudMode getMode()
    {
        return this.mode;
    }

    public void setMode(CrudMode mode)
    {
        this.mode = mode;
    }

    public List<DO> getCreatedDos()
    {
        return this.createdDos;
    }

    public void setCreatedDos(List<DO> createdDos)
    {
        this.createdDos = createdDos;
    }
}

