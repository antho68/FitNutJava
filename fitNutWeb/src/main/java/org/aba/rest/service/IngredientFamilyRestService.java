package org.aba.rest.service;

import org.aba.data.domain.IngredientFamily;

import java.util.LinkedList;

public interface IngredientFamilyRestService
{
    public LinkedList<IngredientFamily> getIngredientFamiliesByTokenAndDate(String token, String dateFormatted);
}
