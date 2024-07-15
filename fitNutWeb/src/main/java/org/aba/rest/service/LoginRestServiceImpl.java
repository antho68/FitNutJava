package org.aba.rest.service;

import org.aba.data.domain.User;
import org.aba.rest.client.HubConnectorWsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service("loginRestServiceImpl")
@Transactional(readOnly = true, isolation = Isolation.DEFAULT)
@Scope("singleton")
public class LoginRestServiceImpl implements LoginRestService
{
    @Autowired
    private HubConnectorWsClient hubConnectorWsClient;

    public User login(String username, String password)
    {
        User userFinded = null;


        return null;
    }
}
