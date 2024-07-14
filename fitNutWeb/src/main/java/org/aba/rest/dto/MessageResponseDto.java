package org.aba.rest.dto;

import org.aba.web.exeption.ServiceException;


/**
 * Dto for responding a simple message
 * 
 * @author mala
 */
public class MessageResponseDto
{
	private String message;
	private ServiceException se;

	public MessageResponseDto()
	{
	}
	
	public MessageResponseDto(String message)
	{
		this.message = message;
	}

	public MessageResponseDto(String message, ServiceException se)
	{
		this(message);
		this.se = se;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public ServiceException getSe()
	{
		return se;
	}

	public void setSe(ServiceException se)
	{
		this.se = se;
	}
}