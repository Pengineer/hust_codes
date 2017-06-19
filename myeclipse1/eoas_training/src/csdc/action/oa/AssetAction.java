package csdc.action.oa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;

import sun.awt.image.OffScreenImage;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import csdc.bean.Account;
import csdc.bean.Asset;
import csdc.bean.AssetVariation;
import csdc.bean.Mail;
import csdc.service.IAssetService;
import csdc.service.IBaseService;
import csdc.service.imp.AssetService;


public class AssetAction extends ActionSupport implements Preparable{
	private Asset asset;
	private String assetId;
	private List<AssetVariation> assetVariations;
	private String name, type, spec, assetNumber, usage;
	private Date datetime, begtime;
	private int status;
	private Double price;	//价格
	private List<String> softwareAdd,hardwareAdd,furAdd,otherAdd;//添加与修改资产时：软件类资产名列表，硬件类资产名列表，家具类资产名列表，其他类资产名列表
	private List<String> softwareSta,hardwareSta,furSta,otherSta;//统计资产时：各类资产名列表
	private IBaseService baseService;
	private String nameByAccount;

	private Map jsonMap = new HashMap();
	private Map map = new HashMap();
	
	public void prepare()throws Exception {
		Map map = new HashMap();
		map.put("type", "软件");
		softwareSta = baseService.list(Asset.class, map);
		softwareSta.add("全部");
		map.put("type", "设备");
		hardwareSta = baseService.list(Asset.class, map);
		hardwareSta.add("全部");
		map.put("type", "家具");
		furSta = baseService.list(Asset.class, map);
		furSta.add("全部");
		map.put("type", "其他");
		otherSta = baseService.list(Asset.class, map);
		otherSta.add("全部");
		
		map.put("type", "软件");
		softwareAdd = baseService.list(Asset.class, map);
		softwareAdd.add("自定义");
		map.put("type", "设备");
		hardwareAdd = baseService.list(Asset.class, map);
		hardwareAdd.add("自定义");
		map.put("type", "家具");
		furAdd = baseService.list(Asset.class, map);
		furAdd.add("自定义");
		map.put("type", "其他");
		otherAdd = baseService.list(Asset.class, map);
		otherAdd.add("自定义");
	}

	public String toAdd() {
		return SUCCESS;
	}
	
	public String add() {
		String str = asset.getAssetNumber().trim();
		asset.setAssetNumber(str);
		map.put("assetNumber", asset.getAssetNumber());
		List<String> assetNumbers = baseService.list(Asset.class.getName() + ".listByTypeAndAssetNumber", map);
		if(str != null) {
			int i = assetNumbers.size();
			for(int j=0;j<i;j++) {
				if(str.equals(assetNumbers.get(j))) {
					this.addFieldError("asset.assetNumber", "此资产编号已经存在");
				}
			}
		}
		
		if(this.asset.getName().equals("自定义")) {
			String s = this.nameByAccount.trim();
			if(s.equals("")) {
				this.addFieldError("asset.name", "请填写资产名");
			} else {
				this.asset.setName(s);
			}
		}
		
		if(!(this.getFieldErrors().isEmpty())) {
			return INPUT;
		}
		baseService.add(asset);
		if(assetVariations != null && !assetVariations.isEmpty()) {
			for(AssetVariation assetVariation : assetVariations) {
				assetVariation.setAsset(this.asset);
				baseService.add(assetVariation);
			}
		}
		return SUCCESS;
	}
	
	public String toList() {
		return SUCCESS;
	}
	
	public String list() {
		ArrayList<Asset> assetList = new ArrayList<Asset>();
		List<Object[]> aList = new ArrayList<Object[]>();
		try {
			assetList = (ArrayList<Asset>) baseService.list(Asset.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] item;
		for (Asset a : assetList) {
			item = new String[8];
			item[0] = a.getAssetNumber();
			item[1] = a.getName();
			item[2] = a.getType();
			item[3] = a.getStatus() + "";
			item[4] = a.getSpec();
			item[5] = a.getRsper().getEmail();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (null != a.getBegtime()) {
				item[6] = sdf.format(a.getBegtime());
			}
			item[7] = a.getId();
			aList.add(item);
		}
		jsonMap.put("aaData", aList);
		return SUCCESS;
	}

	public String view() {
		asset = (Asset) baseService.load(Asset.class, assetId);
		
		return SUCCESS;
	}
	
	public String delete() {
		baseService.delete(Asset.class, assetId);
		return SUCCESS;
	}
	
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Date getBegtime() {
		return begtime;
	}

	public void setBegtime(Date begtime) {
		this.begtime = begtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<String> getSoftwareAdd() {
		return softwareAdd;
	}

	public void setSoftwareAdd(List<String> softwareAdd) {
		this.softwareAdd = softwareAdd;
	}

	public List<String> getHardwareAdd() {
		return hardwareAdd;
	}

	public void setHardwareAdd(List<String> hardwareAdd) {
		this.hardwareAdd = hardwareAdd;
	}

	public List<String> getFurAdd() {
		return furAdd;
	}

	public void setFurAdd(List<String> furAdd) {
		this.furAdd = furAdd;
	}

	public List<String> getOtherAdd() {
		return otherAdd;
	}

	public void setOtherAdd(List<String> otherAdd) {
		this.otherAdd = otherAdd;
	}

	public List<String> getSoftwareSta() {
		return softwareSta;
	}

	public void setSoftwareSta(List<String> softwareSta) {
		this.softwareSta = softwareSta;
	}

	public List<String> getHardwareSta() {
		return hardwareSta;
	}

	public void setHardwareSta(List<String> hardwareSta) {
		this.hardwareSta = hardwareSta;
	}

	public List<String> getFurSta() {
		return furSta;
	}

	public void setFurSta(List<String> furSta) {
		this.furSta = furSta;
	}

	public List<String> getOtherSta() {
		return otherSta;
	}

	public void setOtherSta(List<String> otherSta) {
		this.otherSta = otherSta;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public List<AssetVariation> getAssetVariations() {
		return assetVariations;
	}

	public void setAssetVariations(List<AssetVariation> assetVariations) {
		this.assetVariations = assetVariations;
	}

	public String getNameByAccount() {
		return nameByAccount;
	}

	public void setNameByAccount(String nameByAccount) {
		this.nameByAccount = nameByAccount;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

}