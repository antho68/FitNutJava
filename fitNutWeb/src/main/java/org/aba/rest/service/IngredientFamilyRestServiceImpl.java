package org.aba.rest.service;

import org.aba.data.domain.IngredientFamily;
import org.aba.data.domain.User;
import org.aba.rest.client.HubConnectorWsClient;
import org.aba.rest.dto.IngredientFamilyResponseDto;
import org.aba.rest.dto.UserResponseDto;
import org.aba.rest.exeption.FitNutHttpException;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service("ingredientFamilyRestServiceImpl")
@Transactional(readOnly = true, isolation = Isolation.DEFAULT)
@Scope("singleton")
public class IngredientFamilyRestServiceImpl implements IngredientFamilyRestService
{
    private static String URL = "ingredientFamily";

    @Autowired
    private HubConnectorWsClient hubConnectorWsClient;

    @Override
    public Collection<IngredientFamily> getIngredientFamiliesByTokenAndDate(String token, String dateFormatted)
    {
        Collection<IngredientFamily> ingredientFamilies = new ArrayList<>();

        String composedURL = URL;
        composedURL += "?token=" + token + "";

        if (dateFormatted != null)
        {
            composedURL += "&dateFrom=" + dateFormatted + "";
        }

        IngredientFamilyResponseDto dataToSend = null;
        ResponseEntity<IngredientFamilyResponseDto> response = hubConnectorWsClient.getRequest(composedURL, IngredientFamilyResponseDto.class);

        if (response != null)
        {
            ingredientFamilies = response.getBody().getDatas();
        }

        return ingredientFamilies;
    }
}
