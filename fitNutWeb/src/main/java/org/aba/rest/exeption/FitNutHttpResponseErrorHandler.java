package org.aba.rest.exeption;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Error handler to extract body of response
 * @author aba
 */
public class FitNutHttpResponseErrorHandler implements ResponseErrorHandler
{
	private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

	public boolean hasError(ClientHttpResponse response) throws IOException
	{
		return errorHandler.hasError(response);
	}

	public void handleError(ClientHttpResponse response) throws IOException
	{
		String body = new BufferedReader(new InputStreamReader(response.getBody()))
				.lines().collect(Collectors.joining("\n"));
		
		throw new FitNutHttpException(response.getStatusCode(), response.getHeaders()
				, body, "Response has status code " + response.getStatusCode() + " and body " + body);
	}
}