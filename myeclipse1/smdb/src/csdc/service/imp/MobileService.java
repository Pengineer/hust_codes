package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.bean.Address;
import csdc.bean.Agency;
import csdc.bean.Person;
import csdc.service.IMobileService;
import csdc.tool.bean.LoginInfo;
import csdc.tool.bean.Mobile;

/**
 * mobile基本接口实现类
 * @author suwb
 *
 */
public class MobileService extends BaseService implements IMobileService {
	
	public Map getSimpleSearchHql(LoginInfo loginer, String keyword, Integer type){
		Map jsonMap = new HashMap();
		return jsonMap;
	};
	
	public Map getAdvSearchHql(LoginInfo loginer, Mobile mobile, Integer type){
		Map jsonMap = new HashMap();
		return jsonMap;
	}

	public <T> List getAddress(T t) throws Exception {
		List addressList = new ArrayList();
		String addressIds = (String)t.getClass().getMethod("getAddressIds").invoke(t) ==null?"":(String)t.getClass().getMethod("getAddressIds").invoke(t);
		if(!addressIds.isEmpty()){
			List<Address> list = dao.query("select address from Address address where address.ids = ? order by address.sn", addressIds);
			for(Address address:list){
				List<String> sList = new ArrayList<String>();
				sList.add(address.getId());
				sList.add(address.getAddress());
				sList.add(address.getPostCode());
				addressList.add(sList);
			}
		}
		return addressList;
	}

	public List getHomeAddress(Person person) {
		List addressList = new ArrayList();
		System.out.println(person.getHomeAddressIds());
		if(person.getHomeAddressIds()!=null){
			List<Address> list = dao.query("select address from Address address where address.ids = ? order by address.sn", person.getHomeAddressIds());
			for(Address address:list){
				List<String> sList = new ArrayList<String>();
				sList.add(address.getId());
				sList.add(address.getAddress());
				sList.add(address.getPostCode());
				addressList.add(sList);
			}
		}
		return addressList;
	}

	public List getSAddress(Agency agency) {
		List addressList = new ArrayList();
		if(agency.getSaddressIds()!=null){
			List<Address> list = dao.query("select address from Address address where address.ids = ? order by address.sn", agency.getSaddressIds());
			for(Address address:list){
				List<String> sList = new ArrayList<String>();
				sList.add(address.getId());
				sList.add(address.getAddress());
				sList.add(address.getPostCode());
				addressList.add(sList);
			}
		}
		return addressList;
	};

}
