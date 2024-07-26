package org.aba.rest.service;

import org.aba.data.domain.IngredientFamily;
import org.aba.rest.client.HubConnectorWsClient;
import org.aba.rest.dto.IngredientFamilyResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

@Service("ingredientFamilyRestServiceImpl")
@Transactional(readOnly = true, isolation = Isolation.DEFAULT)
@Scope("singleton")
public class IngredientFamilyRestServiceImpl implements IngredientFamilyRestService
{
    private static String URL = "ingredientFamily";

    @Autowired
    private HubConnectorWsClient hubConnectorWsClient;

    @Override
    public LinkedList<IngredientFamily> getIngredientFamiliesByTokenAndDate(String token, String dateFormatted)
    {
        LinkedList<IngredientFamily> ingredientFamilies = new LinkedList<>();

        String composedURL = URL;
        composedURL += "?token=" + token + "";

        if (dateFormatted != null)
        {
            composedURL += "&dateFrom=" + dateFormatted + "";
        }

        IngredientFamilyResponseDto dataToSend = null;
        ResponseEntity<IngredientFamilyResponseDto> response =
                hubConnectorWsClient.getRequest(composedURL, IngredientFamilyResponseDto.class);

        if (response != null)
        {
            ingredientFamilies = new LinkedList<IngredientFamily>(response.getBody().getDatas());
        }

        return ingredientFamilies;
    }
}
