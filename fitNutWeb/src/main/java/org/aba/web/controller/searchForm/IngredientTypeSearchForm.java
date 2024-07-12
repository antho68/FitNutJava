package org.aba.web.controller.searchForm;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("ingredientTypeSearchForm")
@Scope("view")
public class IngredientTypeSearchForm extends AbstractSearchForm
{
    private String pk;
    private String name;

    @Override
    public void initSearchFormData()
    {

    }

    @Override
    public void clearForm()
    {
        setPk(null);
        setName(null);
    }

    public String getPk()
    {
        return pk;
    }

    public void setPk(String pk)
    {
        this.pk = pk;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
