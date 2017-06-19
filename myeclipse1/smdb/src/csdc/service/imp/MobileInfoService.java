package csdc.service.imp;

import csdc.bean.Message;
import csdc.bean.News;
import csdc.bean.Notice;
import csdc.service.IMobileInfoService;

public class MobileInfoService extends BaseService implements IMobileInfoService {
	
	public News getNewsById(String entityId){
		return dao.query(News.class, entityId);
	}

	public Notice getNoticeById(String entityId) {
		return dao.query(Notice.class, entityId);
	}

	public Message getMessageById(String entityId) {
		return dao.query(Message.class, entityId);
	}
}
