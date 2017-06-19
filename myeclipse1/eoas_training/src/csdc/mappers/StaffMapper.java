package csdc.mappers;

import java.util.List;

import csdc.bean.Staff;


public interface StaffMapper {

	public void insert(Staff staff);// 插入Staff信息

	public void delete(String id);// 根据ID删除Staff

	public void update(Staff staff);// 根据ID更新person

	public List<Staff> findAll();// 查询所有Staff

	public Staff findById(String id);// 根据ID查询

}
