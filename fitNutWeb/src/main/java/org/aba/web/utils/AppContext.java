package org.aba.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service("contextApplicationContextProvider")
@Lazy(false)
public class AppContext implements ApplicationContextAware
{
	private static ApplicationContext applicationContext;
	
	public AppContext()
	{
	}
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException
	{
		applicationContext = ctx;
	}
	
	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
}