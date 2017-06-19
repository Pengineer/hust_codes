package csdc.service;

import csdc.bean.Memo;

public interface IMemoService extends IBaseService {
	/**
	 * 修改备忘
	 * @param orgMemoId原始备忘id
	 * @param memo待修改的备忘对象
	 */
	void modifyMemo(String entityId, Memo memo);
	
}
