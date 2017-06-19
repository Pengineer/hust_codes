package csdc.service.imp;

import java.util.List;

import csdc.bean.AccountPosition;
import csdc.mappers.AccountPositionMapper;
import csdc.service.IAccountPositionService;


public class AccountPositionService extends BaseService implements IAccountPositionService {
	private AccountPositionMapper accountPositionDao;
	
	public int add(AccountPosition accountPosition) {
		return accountPositionDao.insert(accountPosition);
	}
	
	public void modify(AccountPosition accountPosition) {
		accountPositionDao.modify(accountPosition);
	}
	
	public List<AccountPosition> listMyCollect(String id) {
		return accountPositionDao.listMyCollect(id);
	}
	public List<AccountPosition> listAllCollect() {
		return accountPositionDao.listAllCollect();
	}
	public AccountPosition selectAccountPositionByAccountAndPosition(String accountId, String positionId) {
		return accountPositionDao.selectAccountPositionByAccountAndPosition(accountId, positionId);
	}
	public AccountPositionMapper getAccountPositionDao() {
		return accountPositionDao;
	}
	public void setAccountPositionDao(AccountPositionMapper accountPositionDao) {
		this.accountPositionDao = accountPositionDao;
	}
}