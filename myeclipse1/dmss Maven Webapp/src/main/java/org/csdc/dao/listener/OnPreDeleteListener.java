package org.csdc.dao.listener;

import org.csdc.model.Document;
import org.csdc.service.imp.IndexService;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Deprecated
@Component
public class OnPreDeleteListener implements  PreDeleteEventListener{
	
	@Autowired
	private IndexService indexService;
	
	
	@Override
	public boolean onPreDelete(PreDeleteEvent event) {
		if (event.getEntity()  instanceof Document) {
			Document doc = (Document) event.getEntity();
			indexService.deleteIndex(doc.getId());
		}	
		return false;
	}
	
}
