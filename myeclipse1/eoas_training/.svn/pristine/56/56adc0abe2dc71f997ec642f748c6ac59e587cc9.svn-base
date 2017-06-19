package csdc.service.imp;

import csdc.bean.Staff;
import csdc.mappers.StaffMapper;
import csdc.service.IStaffService;

public class StaffService implements IStaffService {
	private StaffMapper staffDao;

	public StaffMapper getStaffDao() {
		return staffDao;
	}

	public void setStaffDao(StaffMapper staffDao) {
		this.staffDao = staffDao;
	}

	@Override
	public void add(Staff staff) {
		staffDao.insert(staff);

	}

}
