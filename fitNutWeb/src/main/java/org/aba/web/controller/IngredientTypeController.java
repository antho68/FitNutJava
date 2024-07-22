package org.aba.web.controller;

import org.aba.rest.service.LoginRestService;
import org.aba.web.controller.searchForm.IngredientTypeSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Component("ingredientTypeController")
@Scope("view")
public class IngredientTypeController extends AbstractBaseController<IngredientTypeSearchForm> implements Serializable
{
    @Autowired
    private LoginRestService loginRestService;

    public IngredientTypeController()
    {

    }

    @PostConstruct
    public void init()
    {
        search();
    }

    @Override
    public void search()
    {

    }

    public void testLogin()
    {
        loginRestService.login("myHubApp", "WX_+M-K2HgtFT4B5");
    }
}
