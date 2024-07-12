package org.aba.jsf.error;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;


/**
 * ExceptionHandlerFactory for fitnut project
 */
@SuppressWarnings({"deprecation"})
public class FitNutExceptionHandlerFactory extends ExceptionHandlerFactory
{
	private ExceptionHandlerFactory delegateFactory;

	public FitNutExceptionHandlerFactory(ExceptionHandlerFactory delegateFactory)
	{
		this.delegateFactory = delegateFactory;
	}

	public ExceptionHandler getExceptionHandler()
	{
		return new FitNutExceptionHandler(delegateFactory.getExceptionHandler());
	}
}