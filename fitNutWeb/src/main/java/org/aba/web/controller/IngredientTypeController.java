package org.aba.web.controller;

import org.aba.web.controller.searchForm.IngredientTypeSearchForm;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("ingredientTypeController")
@Scope("view")
public class IngredientTypeController extends AbstractBaseController<IngredientTypeSearchForm>
        implements Serializable
{

}
