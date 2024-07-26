package org.aba.web.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component("messageHandler")
@Scope("session")
public class MessageHandler implements PhaseListener
{
	private static final long serialVersionUID = 3968185442761810545L;
	private static final String sessionToken = "MULTI_PAGE_MESSAGES_SUPPORT";
	
	public MessageHandler()
	{
	}
	
	public PhaseId getPhaseId()
	{
		return PhaseId.ANY_PHASE;
	}
	
	public void beforePhase(PhaseEvent event)
	{
		if (event.getPhaseId() == PhaseId.RENDER_RESPONSE)
		{
			this.restoreMessages(event.getFacesContext());
		}
	}
	
	public void afterPhase(PhaseEvent event)
	{
		if (event.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES
				|| event.getPhaseId() == PhaseId.PROCESS_VALIDATIONS
				|| event.getPhaseId() == PhaseId.INVOKE_APPLICATION)
		{
			this.saveMessages(event.getFacesContext());
		}
	}
	
	private int saveMessages(FacesContext facesContext)
	{
		List<FacesMessage> messages = new ArrayList();
		Iterator<FacesMessage> i = facesContext.getMessages((String) null);
		
		while (i.hasNext())
		{
			messages.add(i.next());
			i.remove();
		}
		
		if (messages.isEmpty())
		{
			return 0;
		}
		else
		{
			Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
			List<FacesMessage> existingMessages = (List) sessionMap.get("MULTI_PAGE_MESSAGES_SUPPORT");
			if (existingMessages != null)
			{
				existingMessages.addAll(messages);
			}
			else
			{
				sessionMap.put("MULTI_PAGE_MESSAGES_SUPPORT", messages);
			}
			
			return messages.size();
		}
	}
	
	private int restoreMessages(FacesContext facesContext)
	{
		Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
		List<FacesMessage> messages = (List) sessionMap.get("MULTI_PAGE_MESSAGES_SUPPORT");
		if (messages == null)
		{
			return 0;
		}
		else
		{
			int restoredCount = messages.size();
			Iterator<FacesMessage> i = messages.iterator();
			
			while (i.hasNext())
			{
				facesContext.addMessage((String) null, i.next());
			}
			
			return restoredCount;
		}
	}
	
	public void clearMessages(FacesContext facesContext)
	{
		Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
		sessionMap.remove("MULTI_PAGE_MESSAGES_SUPPORT");
	}
}
