package csdc.service;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import csdc.bean.Role;
import csdc.bean.Role_Right;


public interface IRoleService extends IBaseService {
	public String modifyRole(Role oldRole, Role newRole, String[] rightNodeValue);
	public boolean checkRolename(String name);
	public String addRole(Role role, String[] rightNodeValue);
	public Document createRightXML(String roleId, List<String> str);
}
