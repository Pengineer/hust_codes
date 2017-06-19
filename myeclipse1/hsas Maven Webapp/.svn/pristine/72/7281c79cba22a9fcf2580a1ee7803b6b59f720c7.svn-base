package csdc.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import csdc.model.Right;
import csdc.service.IRightService;


/**
 * 
 * 权限管理接口实现
 * @author huangxw
 *
 */

@Service
@Transactional
public class RightService extends BaseService implements IRightService {

	@Override
	public boolean checkRightName(String rightName) {

		if(rightName == null){
			return true;// 权限名为空，视为存在
		}else {
			Map map = new HashMap();
			map.put("rightName", rightName);
			List list = dao.query("from Right r where r.name = :rightName", map);
			return list.isEmpty()? false:true;
		}
	}

	@Override
	public boolean checkRightCode(String rightCode) {
		
		if(rightCode == null){
			return true;// 权限名为空，视为存在
		}else {
			Map map = new HashMap();
			map.put("rightCode", rightCode);
			List list = dao.query("from Right r where r.code = :rightCode", map);
			return list.isEmpty()? false:true;
		}
	}

	@Override
	public boolean checkRightNode(String rightNode) {
		if(rightNode == null){
			return true;// 权限名为空，视为存在
		}else {
			Map map = new HashMap();
			map.put("rightNode", rightNode);
			List list = dao.query("from Right r where r.nodeVaule = :rightNode", map);
			return list.isEmpty()? false:true;
		}
	}

	@Override
	public String addRight(Right right) {
		
		return	(String) dao.add(right);
	}

	@Override
	public String modifyRight(Right oldRight, Right newRight) {
		
		// 更新权限属性
		oldRight.setName(newRight.getName());
		oldRight.setDescription(newRight.getDescription());
		oldRight.setCode(newRight.getCode());
		dao.modify(oldRight);// 修改权限数据
		return oldRight.getId();
		
	}

	@Override
	public Right viewRight(String rightId) {
		
		return dao.query(Right.class, rightId);
	}

	@Override
	public void deleteRight(List<String> rightIds) {
		
		for(String id :rightIds){
			
			dao.delete(Right.class, id);
		}
		
	}

}
