package csdc.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


import csdc.bean.Notice;
import csdc.service.INoticeService;
import csdc.tool.FileTool;

@Transactional
public class NoticeService extends BaseService implements INoticeService {

	/**
	 * 添加通知
	 * @param recieverType 接收邮件对象
	 * @param notice待添加的通知对象
	 * @return noticeId对象ID
	 */
	@SuppressWarnings("unchecked")
	public String addNotice(List recieverType, Notice notice) {
		String receiverType = ""; // （由0和1组成的15位字符串）部级机构、部级管理人员、省级机构、省级管理人员、部属高校、
							 //	部属高校管理人员、地方高校、地方高校管理人员、院系、院系管理人员、
							 //	研究机构、研究机构管理人员、教育系统外部专家、教师、学生
		notice.setCreateDate(new Date());
		notice.setHtmlFile(null);
		if(recieverType == null){
			notice.setReceiverType("000000000000000");
			notice.setSendEmail(0);
		} else {
			for(int i = 0; i < 15; i++){
				if(recieverType.contains(i)){
					receiverType = receiverType.concat("1");
				} else {
					receiverType = receiverType.concat("0");
				}
			}
			notice.setReceiverType(receiverType);
			notice.setSendEmail(1);
		}
		String noticeId = dao.add(notice);
		return noticeId;
	}

	/**
	 * 修改通知
	 * @param orgNoticeId原始通知ID
	 * @param notice待修改的通知对象
	 */
	public void modifyNotice(String orgNoticeId, Notice notice) {
		Notice orgNotice = (Notice) dao.query(Notice.class, orgNoticeId);
		
		// 设置更新值
		orgNotice.setTitle(notice.getTitle());
		orgNotice.setContent(notice.getContent());
		orgNotice.setIsOpen(notice.getIsOpen());
		orgNotice.setIsPop(notice.getIsPop());
		orgNotice.setSource(notice.getSource());
		orgNotice.setType(notice.getType());
		orgNotice.setValidDays(notice.getValidDays());
		
		dao.modify(orgNotice);
	}

	/**
	 * 删除通知
	 * @param noticeIds待删除的通知ID集合
	 */
	public void deleteNotice(List<String> noticeIds) {
		if (noticeIds != null && !noticeIds.isEmpty()) {
			Notice notice;
			String[] fileArray;
			for (String noticeId : noticeIds) {
				if (noticeIds != null && !noticeIds.isEmpty()) {
					notice = (Notice) dao.query(Notice.class, noticeId);
					if (notice.getAttachment() != null) {
						fileArray = notice.getAttachment().split("; ");
						if (fileArray != null && fileArray.length != 0) {
							for (String fileName : fileArray) {
								FileTool.fileDelete(fileName);// 删除附件
							}
						}
					}
					dao.delete(notice);// 删除新闻
				}
			}
		}
	}

	/**
	 * 查看通知
	 * @param noticeId待查看的通知ID
	 * @return notice通知对象
	 */
	@SuppressWarnings("unchecked")
	public Notice viewNotice(String noticeId) {
		Map map = new HashMap();
		map.put("noticeId", noticeId);
		List<Notice> noticeList = dao.query("select n from Notice n left join fetch n.type sys left join fetch n.account a where n.id = :noticeId", map);
		
		return (noticeList.isEmpty() ? null : noticeList.get(0));
	}
}