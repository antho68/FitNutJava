package org.aba.rest.exeption;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * Exception containing http status, headers and body
 * @author aba
 */
public class FitNutHttpException extends RuntimeException
{
	private static final long serialVersionUID = -7933395713809436122L;

	private HttpStatus httpStatus;
	private HttpHeaders httpHeaders;
	private String body;

	public FitNutHttpException(String msg)
	{
		super(msg);
	}

	public FitNutHttpException(HttpStatus httpStatus, HttpHeaders httpHeaders, String body, String msg)
	{
		super(msg);
		
		this.httpStatus = httpStatus;
		this.setHttpHeaders(httpHeaders);
		this.body = body;
	}

	public HttpStatus getHttpStatus()
	{
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus)
	{
		this.httpStatus = httpStatus;
	}

	public HttpHeaders getHttpHeaders()
	{
		return httpHeaders;
	}

	public void setHttpHeaders(HttpHeaders httpHeaders)
	{
		this.httpHeaders = httpHeaders;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}
}