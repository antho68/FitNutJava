package org.aba.web.controller.form;

import org.aba.data.domain.IngredientFamily;
import org.aba.web.exeption.ServiceException;
import org.aba.web.utils.CrudMode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("ingredientFamilyForm")
@Scope("view")
public class IngredientFamilyForm extends AbstractCrudForm<IngredientFamily>
{
    @PostConstruct
    public void init()
    {
    }

    @Override
    public void clearForm()
    {

    }

    @Override
    public void fillForm(IngredientFamily var1, CrudMode var2)
    {

    }

    @Override
    public void save() throws ServiceException
    {

    }

    @Override
    public void delete()
    {

    }

    @Override
    public boolean validateForm(CrudMode var1)
    {
        return false;
    }


}
