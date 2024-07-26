package org.aba.rest.service;

import org.aba.data.domain.User;
import org.aba.rest.dto.UserResponseDto;
import org.aba.rest.client.HubConnectorWsClient;
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

@Service("loginRestServiceImpl")
@Transactional(readOnly = true, isolation = Isolation.DEFAULT)
@Scope("singleton")
public class LoginRestServiceImpl implements LoginRestService
{
    private static String URL = "login";

    @Autowired
    private HubConnectorWsClient hubConnectorWsClient;

    public User login(String username, String password)
    {
        User userFinded = null;

        String composedURL = URL;
        composedURL += "?username=" + username + "";
        composedURL += "&password=" + password + "";
        composedURL += "&app=FitNut";

        UserResponseDto dataToSend = null;
        ResponseEntity<UserResponseDto> response = hubConnectorWsClient.postRequest(composedURL, dataToSend, UserResponseDto.class);
        if (response != null) //204
        {
            userFinded = response.getBody().getData();
        }

        return userFinded;
    }
}
