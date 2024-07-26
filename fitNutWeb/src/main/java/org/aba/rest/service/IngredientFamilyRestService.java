package org.aba.rest.service;

import org.aba.data.domain.IngredientFamily;

import java.util.Collection;

public interface IngredientFamilyRestService
{
    public Collection<IngredientFamily> getIngredientFamiliesByTokenAndDate(String token, String dateFormatted);
}
