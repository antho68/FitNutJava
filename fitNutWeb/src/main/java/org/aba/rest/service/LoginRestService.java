package org.aba.rest.service;

import org.aba.data.domain.User;

public interface LoginRestService
{
    public User login(String username, String password);
}
