package org.aba.rest.dto;

import org.aba.data.domain.User;

public class UserResponseDto
{
    private User data;

    public UserResponseDto()
    {

    }

    public User getData()
    {
        return data;
    }

    public void setData(User data)
    {
        this.data = data;
    }
}
