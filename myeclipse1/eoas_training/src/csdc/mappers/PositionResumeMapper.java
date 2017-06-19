package csdc.mappers;

import java.util.List;

import csdc.bean.PositionResume;

public interface PositionResumeMapper extends BaseMapper {
	public int insert(Object entity);
	public List<PositionResume> listAllApply();
	public List<PositionResume> listAllCollect();
	public List selectPositionResumeByPosition(String positionResumeId);
	public PositionResume selectPositionResumeByPositionAndResume(String positionId, String resumeId);
}