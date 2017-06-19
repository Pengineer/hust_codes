package org.csdc.dao.listener;

import java.util.Set;

import org.csdc.model.Document;
import org.csdc.service.imp.IndexService;
import org.hibernate.event.internal.DefaultDeleteEventListener;
import org.hibernate.event.spi.DeleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 删除事件监听器
 * @author jintf
 * @date 2014-6-15
 */
@Component
public class OnDeleteListener extends DefaultDeleteEventListener{
	
	@Autowired
	private IndexService indexService;
	
	@Override
	public void onDelete(DeleteEvent event){
		if (event.getObject()  instanceof Document) { //文档那个删除时，索引一起删除
			Document doc = (Document) event.getObject();
			try {
				indexService.deleteIndex(doc.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onDelete(event);
	}
	
	@Override
	public void onDelete(DeleteEvent event,Set transientEntities){
		if (event.getObject()  instanceof Document) { //文档那个删除时，索引一起删除
			Document doc = (Document) event.getObject();
			indexService.deleteIndex(doc.getId());
		}
		super.onDelete(event,transientEntities);
	}
	
}
