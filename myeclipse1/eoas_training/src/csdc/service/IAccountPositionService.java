package csdc.service;



import java.util.List;

import csdc.bean.Account;
import csdc.bean.AccountPosition;

public interface IAccountPositionService extends IBaseService {
	public int add(AccountPosition accountPosition);
	public void modify(AccountPosition accountPosition);
	public List<AccountPosition> listMyCollect(String id);
	public List<AccountPosition> listAllCollect();
	public AccountPosition selectAccountPositionByAccountAndPosition(String accountId, String positionId);
}