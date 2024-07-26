package org.aba.web.controller.dialog;

import org.aba.data.domain.IngredientFamily;
import org.aba.web.controller.form.IngredientFamilyForm;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("ingredientFamilyDialog")
@Scope("view")
public class IngredientFamilyDialog extends AbstractCrudDialog<IngredientFamily, IngredientFamilyForm>
{


}
