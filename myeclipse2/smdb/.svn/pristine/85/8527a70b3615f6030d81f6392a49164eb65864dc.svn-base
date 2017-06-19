package csdc.action.system.news;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import csdc.bean.News;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.NewsInfo;

public class Outer extends NewsAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select n.id, n.title, n.createDate from News n where n.isOpen = 1 ";
	private static final String[] COLUMN = {
		"n.title",
		"n.createDate desc, n.title"
	};// 排序列
	private static final String PAGE_NAME = "newsOuterPage";// 列表页面名称
	
	public String HQL() {
		return HQL;
	}
	public String[] column() {
		return Outer.COLUMN;
	}
	public String pageName() {
		return Outer.PAGE_NAME;
	}
	
	/**
	 * 查看新闻
	 * @return
	 */
	public String view() {
		news = newsService.viewNews(entityId);
		if (news == null || news.getIsOpen() != 1) {// 新闻不存在或不是外网新闻
//			request.setAttribute(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_NEWS_NULL);
			addActionError(NewsInfo.ERROR_NEWS_NULL);
			return INPUT;
		}
		
		// 处理附件用于显示
		fileIds = (news.getAttachmentName() == null ? null : news.getAttachmentName().split("; "));
		
		return SUCCESS;
	}

	public String download() throws UnsupportedEncodingException {
		news = (News) dao.query(News.class, entityId);
		if (news == null) {
//			request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
			addActionError("所选新闻已经不存在");
			return INPUT;
		} else {
			if (news.getIsOpen() == 0) {// 如果是内网新闻
//				request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
				addActionError("内网新闻不能下载附件");
				return INPUT;
			} else {
				if (news.getAttachmentName() == null || news.getAttachment() == null) {// 检查是否有附件
//					request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
					addActionError(GlobalInfo.ERROR_ATTACH_NULL);
					return INPUT;
				} else {
					String[] attachName = news.getAttachmentName().split("; ");// 封转附件名称为数组
					String[] attachPath = news.getAttachment().split("; ");// 封转附件路径为数组
					if (attachName.length < status + 1) {// 检查索引的附件是否存在
//						request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
						addActionError(GlobalInfo.ERROR_ATTACH_NULL);
						return INPUT;
					} else {
						attachmentName = attachName[status];
						attachmentName = new String(attachmentName.getBytes(), "ISO8859-1");
						targetFile = ServletActionContext.getServletContext().getResourceAsStream(attachPath[status]);
						return SUCCESS;
					}
				}
			}
		}
	}

	/**
	 * 初级检索
	 */
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		StringBuffer hql = new StringBuffer(HQL());
		Map map = new HashMap();
		map.put("keyword", "%" + keyword + "%");
		hql.append(" and (LOWER(n.title) like :keyword or LOWER(n.content) like :keyword) ");
		return new Object[]{
			hql.toString(),
			map,
			1,
			null
		};
		//		this.simpleSearch(hql, map, ORDER_BY, 1, 0, PAGE_NAME);
//		return SUCCESS;
	}
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}