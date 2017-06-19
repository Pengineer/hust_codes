package csdc.mappers;

import java.util.List;

import csdc.bean.Position;

public interface PositionMapper extends BaseMapper  {
	public List<Position> listAll();
	public List<Position> listByApply();
}