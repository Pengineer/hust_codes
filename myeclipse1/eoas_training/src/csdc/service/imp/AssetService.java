package csdc.service.imp;

import java.util.List;

import csdc.bean.Asset;
import csdc.mappers.AssetMapper;
import csdc.service.IAssetService;


public class AssetService implements IAssetService{
	private AssetMapper assetDao;

	public void add(Asset asset) {
		assetDao.insert(asset);
	}
	
	public List<Asset> listAll() {
		return assetDao.listAll();
	}
	
	public AssetMapper getAssetDao() {
		return assetDao;
	}
	public void setAssetDao(AssetMapper assetDao) {
		this.assetDao = assetDao;
	}
}