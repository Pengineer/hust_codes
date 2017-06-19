package csdc.service.imp;

import java.util.List;

import csdc.bean.PositionResume;
import csdc.mappers.PositionResumeMapper;
import csdc.service.IPositionResumeService;


public class PositionResumeService extends BaseService implements IPositionResumeService {
	private PositionResumeMapper positionResumeDao;
	
	public List selectPositionResumeByPosition(String positionId) {
		return positionResumeDao.selectPositionResumeByPosition(positionId);
	}
	
	
	public List<PositionResume> listAllApply() {
		return positionResumeDao.listAllApply();
	}
	
	public List<PositionResume> listAllCollect() {
		return positionResumeDao.listAllCollect();
	}
	
	public PositionResumeMapper getPositionResumeDao() {
		return positionResumeDao;
	}

	public void setPositionResumeDao(PositionResumeMapper positionResumeDao) {
		this.positionResumeDao = positionResumeDao;
	}
}