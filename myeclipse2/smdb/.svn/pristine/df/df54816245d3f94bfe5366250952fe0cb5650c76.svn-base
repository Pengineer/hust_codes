package csdc.action.pop.edit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;

/**
 * 弹层--选择成果形式
 * @author 余潜玉
 */
public class ProductTypeAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	@Autowired
	protected HibernateBaseDao dao;
	@SuppressWarnings("unchecked")
	public List productTypeList;
	private IBaseService baseService;
	int isApplyNoevaluation;//申请结项方式：1申请免鉴定

	@SuppressWarnings("unchecked")
	public String toEdit(){
		productTypeList = dao.query("select s.id, s.name from SystemOption s where s.systemOption.standard = 'productType' and s.systemOption.code is null and s.isAvailable = 1 order by s.code asc");
		//将著作替换为书稿
		if(isApplyNoevaluation == 0){
			int index = -1;
			for(int i=0; i<productTypeList.size(); i++){
				Object[] o = (Object[]) productTypeList.get(i);
				if(o[1].equals("著作")){
					index = i;
				}
			}
			if(index != -1){
				Object[] obj = {"", "书稿"};
				productTypeList.set(index, obj);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public List getProductTypeList() {
		return productTypeList;
	}

	@SuppressWarnings("unchecked")
	public void setProductTypeList(List productTypeList) {
		this.productTypeList = productTypeList;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public int getIsApplyNoevaluation() {
		return isApplyNoevaluation;
	}

	public void setIsApplyNoevaluation(int isApplyNoevaluation) {
		this.isApplyNoevaluation = isApplyNoevaluation;
	}

}