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

@Component("ingredientTypeController")
@Scope("view")
public class IngredientTypeController extends AbstractSearchEditDialogController<IngredientFamilyDialog
        , IngredientTypeSearchForm, IngredientFamilyForm, IngredientFamily> implements Serializable
{
    @Autowired
    private IngredientFamilyRestService ingredientFamilyRestService;
    @Autowired
    private SessionBean sessionBean;

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
        setDos(ingredientFamilyRestService.getIngredientFamiliesByTokenAndDate(
                sessionBean.getActualToken(), null));

    }


}
