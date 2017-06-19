package org.csdc.service.imp;

import java.util.List;
import java.util.Properties;

import org.csdc.model.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 站内信管理业务类（未实现）
 * @author jintf
 * @date 2014-6-16
 */
@Service
@Transactional
public class MessageService extends BaseService  {

	
	public String addMessage(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String modifyMessage(String orgMessageId, Message message,
			String[] modifier) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void deleteMessage(List<String> messageIds) {
		// TODO Auto-generated method stub
		
	}

	
	public List<Message> viewMessage(String messageId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void toggleOpen(String messageId, int status) {
		// TODO Auto-generated method stub
		
	}

	
	public String[] getEntityNameEmail(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Properties getParticipants(String messageId) {
		// TODO Auto-generated method stub
		return null;
	}

}