package csdc.action.system.notice;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import csdc.bean.Notice;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.NoticeInfo;

public class Outer extends NoticeAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select n.id, n.title, n.createDate from Notice n where n.isOpen = 1 and ROUND(sysdate - n.createDate) < n.validDays ";
	private static final String[] COLUMN = {
		"n.title",
		"n.createDate desc"
	};// 排序列
	private static final String PAGE_NAME = "noticeOuterPage";// 列表页面名称

	public String[] column() {
		return Outer.COLUMN;
	}
	public String pageName() {
		return Outer.PAGE_NAME;
	}
	
	/**
	 * 查看通知
	 * @return
	 */
	public String view() {
		notice = noticeService.viewNotice(entityId);
		if (notice == null || notice.getIsOpen() != 1) {// 通知不存在或不是外网通知或者通知已过有效期
//			request.setAttribute(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_NOTICE_NULL);
			addActionError("通知不存在或不是外网通知或者通知已过有效期");
			return INPUT;
		}
		
		// 外网通知判断是否在有效期内
		Date currentDate = new Date();// 当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(notice.getCreateDate());// 通知发布时间
		calendar.add(Calendar.DATE, notice.getValidDays());
		Date date = calendar.getTime();// 通知有效期
		if (currentDate.after(date)) {
//			request.setAttribute(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_NOTICE_NULL);
			addActionError("通知已经过期");
			return INPUT;
		}
		
		// 处理附件用于显示
		fileIds = (notice.getAttachmentName() == null ? null : notice.getAttachmentName().split("; "));
		
		return SUCCESS;
	}

	public String download() throws UnsupportedEncodingException {
		notice = (Notice) dao.query(Notice.class, entityId);
		if (notice == null) {
//			request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
			addActionError("通知已经不存在");
			return INPUT;
		} else {
			if (notice.getIsOpen() == 0) {// 如果是内网通知
//				request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
				addActionError(GlobalInfo.ERROR_ATTACH_NULL);
				return INPUT;
			} else {
				if (notice.getAttachmentName() == null || notice.getAttachment() == null) {// 检查是否有附件
//					request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
					addActionError(GlobalInfo.ERROR_ATTACH_NULL);
					return INPUT;
				} else {
					String[] attachName = notice.getAttachmentName().split("; ");// 封转附件名称为数组
					String[] attachPath = notice.getAttachment().split("; ");// 封转附件路径为数组
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
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("keyword", "%" + keyword + "%");
		hql.append(HQL);
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
	public String HQL() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}