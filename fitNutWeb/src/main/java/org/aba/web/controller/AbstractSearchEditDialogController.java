package org.aba.web.controller;

import org.aba.data.domain.AbstractDomain;
import org.aba.data.domain.IngredientFamily;
import org.aba.web.SessionBean;
import org.aba.web.controller.dialog.AbstractCrudDialog;
import org.aba.web.controller.form.AbstractCrudForm;
import org.aba.web.controller.searchForm.AbstractSearchForm;
import org.aba.web.exeption.ServiceException;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.aba.web.utils.CrudMode;
import org.aba.web.utils.MessageUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.datatable.DataTableState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.LinkedList;
import java.util.List;


/**
 * @author aba
 */
@Component
public abstract class AbstractSearchEditDialogController<DIA extends AbstractCrudDialog<DO, CF>
        , SF extends AbstractSearchForm
        , CF extends AbstractCrudForm<DO>
        , DO extends AbstractDomain>
        extends AbstractBaseController<SF>
{
    private static final long serialVersionUID = 2502383961097526130L;

    @Autowired
    protected SessionBean sessionBean;

    protected DIA dialog;
    protected DO selectedDo;
    protected String tableComponentRef;
    protected List<Boolean> visibleColumns;
    protected boolean initFiltersOnce = true;
    private LinkedList<DO> dos;
    private List<DO> filteredDos;

    protected AbstractSearchEditDialogController()
    {
        super();
    }

    protected SessionBean getSessionBean()
    {
        return sessionBean;
    }

    protected void initFilter()
    {
        initFiltersOnce = false;
    }

    public void clearAllFilters()
    {
        clearAllFilters(true);
    }

    public void clearAllFilters(boolean saveFilter)
    {
        this.getSearchForm().clearForm();
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
                .findComponent(this.getTableComponentRef());
        if (dataTable != null)
        {
            DataTableState ts = dataTable.getMultiViewState(false);
            if (ts != null)
            {
                ts.setFilterBy(null);
            }

            if (!dataTable.getFilterByAsMap().isEmpty())
            {
                dataTable.reset();
                dataTable.setFilteredValue((List) null);
                dataTable.setFilteredValue(null);
            }

            updateOnRequestContext(this.getTableComponentRef());
        }
    }

    /**
     * save the form
     */
    public void saveFormAction(boolean closeTheDialog)
    {
        MessageUtils.clearMessageList();

        if (dialog.validateForm())
        {
            try
            {
                dialog.saveForm();
                if (dos == null)
                {
                    dos = new LinkedList<DO>();
                }

                if (dialog.isAddMode() || dialog.isCopyMode())
                {
                    dos.add(selectedDo);

                    if (filteredDos != null)
                    {
                        filteredDos.add(selectedDo);
                    }

                    doAfterSave();
                    updateColumns();
                }
                else
                {
                    if (!CommonUtils.isCollectionEmpty(dos))
                    {
                        int index = dos.indexOf(selectedDo);
                        if (index >= 0)
                        {
                            dos.set(index, selectedDo);
                        }
                    }

                    doAfterSave();
                }

                if (closeTheDialog)
                {
                    hideDialog(dialog.getDialogName());
                    clearDialog(false);
                }
                else
                {
                    if (dialog.isAddMode() || dialog.isCopyMode())
                    {
                        dialog.setMode(CrudMode.EDIT);
                        fillDialog();
                    }
                }

                return;
            }
            catch (ServiceException e)
            {
                CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG, e.getMessage());
                MessageUtils.addErrorMessageText(e.getMessage());
            }
        }

        if (MessageUtils.hasErrorMessage())
        {
            showErrorDialog();
        }
    }

    protected void doAfterSave()
    {

    }

    protected void clearDialog(boolean clearMessages)
    {
        this.dialog.clearDialog();
        if (clearMessages)
        {
            MessageUtils.clearMessageList();
        }
    }

    protected void fillDialog()
    {
        this.clearDialog(true);
        this.dialog.fillDialog(this.selectedDo);
    }

    public String getTableComponentRef()
    {
        return this.tableComponentRef;
    }

    public void setTableComponentRef(String tableComponentRef)
    {
        this.tableComponentRef = tableComponentRef;
    }

    public void updateColumns()
    {
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(this.getTableComponentRef());
        if (table != null && table instanceof DataTable dataTable)
        {
            dataTable.setFirst(0);
            dataTable.resetValue();
        }
    }

    public LinkedList<DO> getDos()
    {
        return dos;
    }

    public void setDos(LinkedList<DO> dos)
    {
        this.dos = dos;
    }

    public List<DO> getFilteredDos()
    {
        return filteredDos;
    }

    public void setFilteredDos(List<DO> filteredDos)
    {
        this.filteredDos = filteredDos;
    }

    public void setEditSelectedDto(DO selectedDo)
    {
        this.dialog.setMode(CrudMode.EDIT);
        this.selectedDo = selectedDo;
        this.fillDialog();
    }

    public DO getSelectedDo()
    {
        return selectedDo;
    }

    public void setSelectedDo(DO selectedDo)
    {
        this.selectedDo = selectedDo;
    }
}