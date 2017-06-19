package csdc.mappers;

import java.util.List;

import csdc.bean.Asset;

public interface AssetMapper extends BaseMapper {
	public List<Asset> listAll();
}