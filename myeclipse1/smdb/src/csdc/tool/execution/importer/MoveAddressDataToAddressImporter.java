package csdc.tool.execution.importer;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import csdc.bean.Address;
import csdc.bean.Agency;
import csdc.bean.BankAccount;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Person;

/**
 * 将地址数据迁移到新建的Address表
 * @author pengliang
 * 备注：地址数据都用IDS关联
 */
@Component
public class MoveAddressDataToAddressImporter extends Importer {

	@Override
	protected void work() throws Throwable {
		/** agency表  **/
//		moveAgencyAddressInfo();
//		moveAgencySaddressInfo();
//		moveAgencyFaddressInfo();	//无数据
		
		/** department表 **/
//		moveDepartmentAddressInfo();
		
		/** institute表 **/
//		moveInstituteAddressInfo();
		
		/** person表 **/
//		movePersonHomeAddressInfo();
		//利用C_CREATE_TYPE字段，该字段所有值都为0，可以临时借用修改为0-19进行分段处理
		movePersonOfficeAddressInfo("select p from Person p where" +
					"(p.officeAddress is not null or p.officePostcode is not null) and p.createType=?", 19);
	}

	public void moveAgencyAddressInfo() {
		List<Agency> agencys = dao.query("select ag from Agency ag where ag.address is not null or ag.postcode is not null");
		if (agencys != null && agencys.size() != 0) {
			int size = agencys.size();
			int current = 0;
			for (Agency agency : agencys) {
				System.out.println(++current + "/" + size);
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				agency.setAddressIds(uuid);	
				//管理机构有通讯地址的只有两条数据，而且是一对一
				Address address = new Address();
				address.setIds(uuid.toString());
				address.setSn(1);
				address.setIsDefault(1);
				address.setAddress(agency.getAddress());
				address.setPostCode(agency.getPostcode());
				address.setCreateMode(2);
				address.setCreateDate(agency.getImportedDate());
				dao.add(address);
				dao.modify(agency);
			}
		}
	}
	
	public void moveAgencySaddressInfo() {
		List<Agency> agencys = dao.query("select ag from Agency ag where ag.saddress is not null or ag.spostcode is not null");
		if (agencys != null && agencys.size() != 0) {
			int size = agencys.size();
			int current = 0;
			for (Agency agency : agencys) {
				System.out.println(++current + "/" + size + "::" + agency.getId());
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				agency.setSaddressIds(uuid);				
				if (agency.getSaddress() != null && agency.getSpostcode() !=null && agency.getSaddress().contains(";") && agency.getSpostcode().contains(";")) {   //多个地址和多个邮编一一对应
					String[] saddresses = agency.getSaddress().split(";");
					String[] spostcodes = agency.getSpostcode().split(";");
					if (saddresses.length != spostcodes.length) {
						throw new RuntimeException();
					}
					
					for (int i = 0; i < saddresses.length; i++) {
						Address address = new Address();
						address.setIds(uuid.toString());
						address.setSn(i+1);
						if(i == 0){
							address.setIsDefault(1);
						} else {
							address.setIsDefault(0);
						}
						address.setAddress(saddresses[i].replaceAll("\\s+", ""));
						address.setPostCode(spostcodes[i].replaceAll("\\s+", ""));
						address.setCreateMode(2);
						address.setCreateDate(agency.getImportedDate());
						dao.add(address);
					}
				} else if (agency.getSaddress() != null && agency.getSpostcode() !=null && agency.getSaddress().contains(";") && !(agency.getSpostcode().contains(";"))) { //多个地址对应一个邮编
					String[] saddresses = agency.getSaddress().split(";");
					for (int i =0 ; i < saddresses.length ; i++) {   //有几个地址就对应几条记录，但使用相同的邮编
						Address address = new Address();
						address.setIds(uuid.toString());
						address.setSn(i+1);
						if(i == 0){
							address.setIsDefault(1);
						} else {
							address.setIsDefault(0);
						}
						address.setAddress(saddresses[i].replaceAll("\\s+", ""));
						address.setPostCode(agency.getSpostcode().replaceAll("\\s+", ""));
						address.setCreateMode(2);
						address.setCreateDate(agency.getImportedDate());
						dao.add(address);
					}
				} else if(agency.getSaddress() != null && agency.getSpostcode() !=null && !(agency.getSaddress().contains(";")) && agency.getSpostcode().contains(";")) {   //多个邮编对应一个地址
					String[] spostcodes = agency.getSpostcode().split(";");
					for (int i =0 ; i < spostcodes.length ; i++) {
						Address address = new Address();
						address.setIds(uuid.toString());
						address.setSn(i+1);
						if (i == 0) {
							address.setIsDefault(1);
						} else {
							address.setIsDefault(0);
						}
						address.setAddress(agency.getSaddress().replaceAll("\\s+", ""));
						address.setPostCode(spostcodes[i].replaceAll("\\s+", ""));
						address.setCreateMode(2);
						address.setCreateDate(agency.getImportedDate());
						dao.add(address);
					}
				} else { //只有一个/零个地址和一个/零个邮编
					Address address = new Address();
					address.setIds(uuid.toString());
					address.setSn(1);
					address.setIsDefault(1);
					String saddress = agency.getSaddress() == null ? null : agency.getSaddress().replaceAll("\\s+", "");
					String spostcode = agency.getSpostcode() == null ? null : agency.getSpostcode().replaceAll("\\s+", "");
					address.setAddress(saddress);
					address.setPostCode(spostcode);
					address.setCreateMode(2);
					address.setCreateDate(agency.getImportedDate());
					dao.add(address);
				}
				dao.modify(agency);
			}
		}
	}
	
	public void moveAgencyFaddressInfo() {
		List<Agency> agencys = dao.query("select ag from Agency ag where ag.faddress is not null or ag.fpostcode is not null");
		if (agencys == null || agencys.size() == 0) {
			System.out.println("无可迁移数据");
		}
	}
	
	public void moveDepartmentAddressInfo() {
		List<Department> departments = dao.query("select dp from Department dp where dp.address is not null or dp.postcode is not null");
		if (departments != null && departments.size() != 0) {
			int size = departments.size();
			int current = 0;
			for (Department department : departments) {
				System.out.println(++current + "/" + size + "::" + department.getId());
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				department.setAddressIds(uuid);	
				//管理机构有通讯地址都是一对一
				Address address = new Address();
				address.setIds(uuid.toString());
				address.setSn(1);
				address.setIsDefault(1);
				address.setAddress(department.getAddress());
				address.setPostCode(department.getPostcode());
				address.setCreateMode(2);
				dao.add(address);
				dao.modify(department);
			}
		}
	}
	
	public void moveInstituteAddressInfo() {
		List<Institute> institutes = dao.query("select instp from Institute instp where instp.address is not null or instp.postcode is not null");
		if (institutes != null && institutes.size() != 0) {
			int size = institutes.size();
			int current = 0;
			for (Institute institute : institutes) {
				System.out.println(++current + "/" + size + "::" + institute.getId());
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				institute.setAddressIds(uuid);			
				if (institute.getAddress() != null && institute.getPostcode() != null && institute.getAddress().contains(";") && institute.getPostcode().contains(";")) {   //多个地址和多个邮编一一对应
					String[] addresses = institute.getAddress().split(";");
					String[] postcodes = institute.getPostcode().split(";");
					if (addresses.length != postcodes.length) {
						throw new RuntimeException();
					}
					
					for (int i = 0; i < addresses.length; i++) {
						Address address = new Address();
						address.setIds(uuid.toString());
						address.setSn(i+1);
						if(i == 0){
							address.setIsDefault(1);
						} else {
							address.setIsDefault(0);
						}
						address.setAddress(addresses[i].replaceAll("\\s+", ""));
						address.setPostCode(postcodes[i].replaceAll("\\s+", ""));
						address.setCreateMode(2);
						dao.add(address);
					}
				} else if (institute.getAddress() != null && institute.getPostcode() != null && institute.getAddress().contains(";") && !(institute.getPostcode().contains(";"))) { //多个地址对应一个邮编
					String[] addresses = institute.getAddress().split(";");
					for (int i =0 ; i < addresses.length ; i++) {   //有几个地址就对应几条记录，但使用相同的邮编
						Address address = new Address();
						address.setIds(uuid.toString());
						address.setSn(i+1);
						if(i == 0){
							address.setIsDefault(1);
						} else {
							address.setIsDefault(0);
						}
						address.setAddress(addresses[i].replaceAll("\\s+", ""));
						address.setPostCode(institute.getPostcode().replaceAll("\\s+", ""));
						address.setCreateMode(2);
						dao.add(address);
					}
				} else if (institute.getAddress() != null && institute.getPostcode() != null && !(institute.getAddress().contains(";")) && institute.getPostcode().contains(";")) {   //多个邮编对应一个地址
					String[] postcodes = institute.getPostcode().split(";");
					for (int i =0 ; i < postcodes.length ; i++) {
						Address address = new Address();
						address.setIds(uuid.toString());
						address.setSn(i+1);
						if(i == 0){
							address.setIsDefault(1);
						} else {
							address.setIsDefault(0);
						}
						address.setAddress(institute.getAddress().replaceAll("\\s+", ""));
						address.setPostCode(postcodes[i].replaceAll("\\s+", ""));
						address.setCreateMode(2);
						dao.add(address);
					}
				} else { //只有一个地址和一个邮编
					Address address = new Address();
					address.setIds(uuid.toString());
					address.setSn(1);
					address.setIsDefault(1);
					String instpAddress = institute.getAddress() == null ? null : institute.getAddress().replaceAll("\\s+", "");
					String instpPostcode = institute.getPostcode() == null ? null : institute.getPostcode().replaceAll("\\s+", "");					
					address.setAddress(instpAddress);
					address.setPostCode(instpPostcode);
					address.setCreateMode(2);
					dao.add(address);
				}
				dao.modify(institute);
			}
		}
	}
	
	public void movePersonHomeAddressInfo() {
		List<Person> persons = dao.query("select p from Person p where p.homeAddress is not null or p.homePostcode is not null");
		if (persons != null && persons.size() != 0) {
			int size = persons.size();
			int current = 0;
			for (Person person : persons) {
				System.out.println(++current + "/" + size + "::" + person.getId());
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				person.setHomeAddressIds(uuid);	
				//人员家庭地址只有三条数据，而且是一对一
				Address address = new Address();
				address.setIds(uuid.toString());
				address.setSn(1);
				address.setIsDefault(1);
				address.setAddress(person.getHomeAddress());
				address.setPostCode(person.getHomePostcode());
				address.setCreateMode(2);
				dao.add(address);
				dao.modify(person);
			}
		}
	}
	
	public void movePersonOfficeAddressInfo(String sql, int div) {
		List<Person> persons = dao.query(sql, div);
		if (persons != null && persons.size() != 0) {
			int size = persons.size();
			int current = 0;
			for (Person person : persons) {
				System.out.println(div + "-" + (++current) + "/" + size + "::" + person.getId());
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				person.setCreateType(0);
				person.setOfficeAddressIds(uuid);
				if (person.getOfficeAddress() != null && person.getOfficePostcode()!= null && person.getOfficeAddress().contains(";") && person.getOfficePostcode().contains(";")) {   //多个地址和多个邮编一一对应
					String[] addresses = person.getOfficeAddress().split(";");
					String[] postcodes = person.getOfficePostcode().split(";");
					if (addresses.length != postcodes.length && postcodes.length != 1 ) {
						throw new RuntimeException();
					}
					
					for (int i = 0; i < addresses.length; i++) {
						Address address = new Address();
						address.setIds(uuid.toString());
						address.setSn(i+1);
						if(i == 0){
							address.setIsDefault(1);
						} else {
							address.setIsDefault(0);
						}
						address.setAddress(addresses[i].replaceAll("\\s+", ""));
						address.setPostCode(postcodes[i].replaceAll("\\s+", ""));
						address.setCreateMode(2);
						dao.add(address);
					}
				} else if (person.getOfficeAddress() != null && person.getOfficePostcode()!= null && person.getOfficeAddress().contains(";") && !(person.getOfficePostcode().contains(";"))) { //多个地址对应一个/零个邮编
					String[] addresses = person.getOfficeAddress().split(";");
					for (int i =0 ; i < addresses.length ; i++) {   //有几个地址就对应几条记录，但使用相同的邮编
						Address address = new Address();
						address.setIds(uuid.toString());
						address.setSn(i+1);
						if(i == 0){
							address.setIsDefault(1);
						} else {
							address.setIsDefault(0);
						}
						address.setAddress(addresses[i].replaceAll("\\s+", ""));
						address.setPostCode(person.getOfficePostcode().replaceAll("\\s+", ""));
						address.setCreateMode(2);
						dao.add(address);
					}
				} else if (person.getOfficeAddress() != null && person.getOfficePostcode()!= null && !(person.getOfficeAddress().contains(";")) && person.getOfficePostcode().contains(";")) {   //多个邮编对应一个/零个地址（取最后一个邮编）
					String[] postcodes = person.getOfficePostcode().split(";");
					Address address = new Address();
					address.setIds(uuid.toString());
					address.setSn(1);
					address.setIsDefault(1);
					address.setAddress(person.getOfficeAddress().replaceAll("\\s+", ""));
					address.setPostCode(postcodes[postcodes.length - 1].replaceAll("\\s+", ""));
					address.setCreateMode(2);
					dao.add(address);
				} else { //只有一个地址和一个邮编(或则其中有一个为空，另一个唯一)
					Address address = new Address();
					address.setIds(uuid.toString());
					address.setSn(1);
					address.setIsDefault(1);
					String officeAddress = person.getOfficeAddress() == null ? null : person.getOfficeAddress().replaceAll("\\s+", "");
					String officePostcode = person.getOfficePostcode() == null ? null : person.getOfficePostcode().replaceAll("\\s+", "");
					address.setAddress(officeAddress);
					address.setPostCode(officePostcode);
					address.setCreateMode(2);
					dao.add(address);
				}
				dao.modify(person);
			}
		}
		persons = null;
	}
}
