package csdc.service;

import java.util.List;

import csdc.bean.Notice;

public interface INoticeService extends IBaseService {

	/**
	 * 添加通知
	 * @param notice待添加的通知对象
	 * @return noticeId对象ID
	 */
	@SuppressWarnings("unchecked")
	public String addNotice(List recieverType, Notice notice);

	/**
	 * 修改通知
	 * @param orgNoticeId原始通知ID
	 * @param notice待修改的通知对象
	 */
	public void modifyNotice(String orgNoticeId, Notice notice);

	/**
	 * 删除通知
	 * @param noticeIds待删除的通知ID集合
	 */
	public void deleteNotice(List<String> noticeIds);

	/**
	 * 查看通知
	 * @param noticeId待查看的通知ID
	 * @return notice通知对象
	 */
	public Notice viewNotice(String noticeId);
	
	/**
	 * 将通知附件上传到DMSS
	 * @param notice
	 * @return 返回dfsId
	 * @throws Exception
	 */
	public String uploadToDmss(Notice notice) throws Exception;
	
}