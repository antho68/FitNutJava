package org.aba.rest.service;

import org.aba.data.domain.User;
import org.aba.rest.client.HubConnectorWsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

        try
        {
            ResponseEntity<User> response = hubConnectorWsClient.postRequest(URL, userFinded, User.class);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return null;
    }
}
