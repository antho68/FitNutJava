package org.aba.web.controller;

import org.aba.data.domain.IngredientFamily;
import org.aba.rest.service.IngredientFamilyRestService;
import org.aba.web.SessionBean;
import org.aba.web.controller.dialog.IngredientFamilyDialog;
import org.aba.web.controller.form.IngredientFamilyForm;
import org.aba.web.controller.searchForm.IngredientTypeSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;

@Component("ingredientTypeController")
@Scope("view")
public class IngredientTypeController extends AbstractSearchEditDialogController<IngredientFamilyDialog
        , IngredientTypeSearchForm, IngredientFamilyForm, IngredientFamily> implements Serializable
{
    @Autowired
    private IngredientFamilyRestService ingredientFamilyRestService;
    @Autowired
    private SessionBean sessionBean;
    private IngredientFamily selectedDo;

    public IngredientTypeController()
    {
        setTableComponentRef(":form:ingredientFamilyDataTable");
    }

    @PostConstruct
    public void init()
    {
        search();
    }

    @Override
    public void search()
    {
        setDtos(ingredientFamilyRestService.getIngredientFamiliesByTokenAndDate(
                sessionBean.getActualToken(), null));
    }

    public Collection<IngredientFamily> getDtos()
    {
        return dtos;
    }

    public void setDtos(Collection<IngredientFamily> dtos)
    {
        this.dtos = dtos;
    }

    public Collection<IngredientFamily> getFilteredDtos()
    {
        return filteredDtos;
    }

    public void setFilteredDtos(Collection<IngredientFamily> filteredDtos)
    {
        this.filteredDtos = filteredDtos;
    }

    public IngredientFamily getSelectedDto()
    {
        return selectedDto;
    }

    public void setSelectedDto(IngredientFamily selectedDto)
    {
        this.selectedDto = selectedDto;
    }

    public void setEditSelectedDto(IngredientFamily selectedDto)
    {
        this.dialog.setMode(CrudMode.EDIT);
        this.selectedDto = selectedDto;
        this.fillDialog();
    }
}
