package org.csdc.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.csdc.bean.TreeNode;
import org.csdc.model.Account;
import org.csdc.model.Bookmark;
import org.csdc.model.Category;
import org.csdc.model.CategorySecurity;
import org.csdc.model.Document;
import org.csdc.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 分类管理业务类
 * @author jintf
 * @date 2014-6-16
 */
@Service
@Transactional
public class CategoryService extends BaseService {
	private Map categoryMap = new ConcurrentHashMap<String, String>();
	
	/**
	 * 生成分类树
	 * @param request
	 * @return TreeNode，分类树根节点
	 */
	public TreeNode getCategoryTree(HttpServletRequest request) {
		TreeNode root;
		Object[] result = (Object[]) baseDao.queryUnique("select c.id, c.name from Category c where c.name ='root'");
		root = new TreeNode(result[0].toString(), result[1].toString());
		//获取我的工作区目录结构
		Account account = getSessionAccount(request);
		Category mySpace = account.getCategory();
		TreeNode spaceNode = new TreeNode(mySpace.getId(), mySpace.getName());
		getChildNodes(spaceNode);
		root.getChildren().add(spaceNode);
		//获取管理域目录
		List<Category> domains = getMyDomains(account.getName());
		for(Category o:domains){
			TreeNode n = new TreeNode(o.getId(), o.getName());			
			getChildNodes(n);
			root.getChildren().add(n);
		}
		return root;
	}
	
	/**
	 * 查询某个分类节点的子节点
	 * @param node
	 */
	private void getChildNodes(TreeNode node){
		List result = baseDao.query("select c.id, c.name from Category c where c.parent.id =? order by weight asc",node.getId());
		for (Object o : result) {
			Object[] oo = (Object[])o;
			TreeNode n = new TreeNode(oo[0].toString(), oo[1].toString());
			node.getChildren().add(n);
			getChildNodes(n);
		}

	} 
	
	/**
	 * 获取我的管理
	 * @param accountName
	 * @return
	 */
	public List<Category> getMyDomains(String accountName ){
		List<Category> domains = null;
		if("admin".equals(accountName)){
			domains = getAllDomains();
		}else {
			domains = baseDao.query("select distinct c from Account a left join a.roles r,CategorySecurity cs,Category c where  r.id=cs.role.id and cs.category.id=c.id and  a.name=?  and  c.parent.id ='root' and c.name!='我的工作区' order by c.weight asc",accountName);
		}
		return domains;
	}
	
	/**
	 * 获取所有文档域
	 * @return
	 */
	public List<Category> getAllDomains( ){
		List<Category> domains = baseDao.query("select c from Category c where c.parent.id='root' and c.name !='我的工作区' order by c.weight asc");
		return domains;
	}
	
	
	
	/**
	 * 添加一个分类目录
	 * @param name 分类目录名
	 * @param description 分类目录描述
	 * @param parentId 分类目录父节点
	 * @param creator 创建者
	 */
	public String addSubCategory(String name,String description,String parentId,Account creator){
		Integer weight =  (Integer) baseDao.queryUnique("select cast(max(c.weight),integer) from Category c where c.parent.id = ?",parentId);
		if(null == weight){
			weight = -1;
		}
		Category subCategory = new Category();
		subCategory.setName(name);
		subCategory.setDescription(description);
		subCategory.setWeight(weight+1);
		subCategory.setParent(baseDao.query(Category.class,parentId));
		subCategory.setLastModifiedDate(new Date());
		subCategory.setCreator(creator);
		subCategory.setDocCount(0);		
		return (String)baseDao.add(subCategory);
	}
	
	/**
	 * 移动分类目录，连带子目录级联移动
	 * @param srcCategory 源分类目录
	 * @param srcWeight 源分类目录权重
	 * @param destCategory 目的分类目录（父分类目录）
	 * @param destWeight 目的分类目录权重
	 */
	public void moveCategory(Category srcCategory,Category destCategory,Integer destWeight ){
		baseDao.execute("update Category c set c.weight=c.weight-1 where c.weight >? and c.parent.id=? and c.name!='我的工作区'",srcCategory.getWeight(),srcCategory.getParent().getId());
		srcCategory.setParent(destCategory);
		srcCategory.setWeight(destWeight);
		baseDao.execute("update Category c set c.weight=c.weight+1 where c.weight >=? and c.parent.id=? and c.name!='我的工作区'",destWeight,destCategory.getId());
		baseDao.addOrModify(srcCategory);
	}
	
	/**
	 * 级联删除某一目录（包含当前目录）
	 * @param id 目录
	 */
	public void deleteCategory(String id){
		Category category = baseDao.query(Category.class,id);
		baseDao.execute("update Category c set c.weight=c.weight-1 where c.weight >=? and c.parent.id=?",category.getWeight(),category.getParent().getId());		
		//这里要先删除掉外键关系
		baseDao.execute("delete from CategorySecurity cs where cs.category.id=?",id);
		baseDao.delete(category);
	}
	
	/**
	 * 获取分类目录的所有子目录（包含当前目录）
	 * @param id 当前分类目录
	 * @param categories 所获取分类目录的所有子目录（包含当前目录）
	 */
	public void getSubCategories(String id,List<Category> categories){		
		Category category = baseDao.query(Category.class,id);	
		categories.add(category);
		for (Category c:category.getChildren()) {
			getSubCategories(c.getId(), categories);
		}
	}
	
	/**
	 * 重命名分类目录名
	 * @param id
	 * @param name
	 */
	public void renameCategory(String id,String name){
		Category category = baseDao.query(Category.class,id);
		if(!category.getName().equals(name)){
			category.setName(name);
			baseDao.modify(category);
		}
	}
	
	/**
	 * 判断当前节点直接子节点中是否已有名字为name的节点
	 * @param name 
	 * @param pid 父节点
	 * @return
	 */
	public boolean isExistSubCategoryName(String name,String pid){
		List result = baseDao.query("select c from Category c where c.name=? and c.parent.id = ?",name,pid);
		if(!result.isEmpty())
			return true;
		return false;
	}
	
	/**
	 * 判断该域名是否已存在
	 * @param name 
	 * @param pid 父节点
	 * @return
	 */
	public boolean isExistDomainName(String name){
		Category root = (Category) baseDao.queryUnique("select c from Category c where c.name = 'root' ");
		List result = baseDao.query("select c from Category c where c.name=? and c.parent.id = ?",name,root.getId());
		if(!result.isEmpty())
			return true;
		return false;
	}
	
	/**
	 * 创建一个域
	 * @param name
	 * @param description
	 * @param creator
	 * @return
	 */
	public String addDomain(String name,String description,Account creator){
		Category root = (Category) baseDao.queryUnique("select c from Category c where c.name = 'root' ");
		Integer weight =  (Integer) baseDao.queryUnique("select cast(max(c.weight),integer) from Category c where c.parent.id = ? ",root.getId());
		if(null == weight)
			weight = -1;
		Category domain = new Category();
		domain.setName(name);
		domain.setDescription(description);
		domain.setWeight(weight+1);
		domain.setParent(root);
		domain.setLastModifiedDate(new Date());
		domain.setCreator(creator);
		domain.setDocCount(0);		
		CategorySecurity cs = new CategorySecurity();
		cs.setCategory(domain);
		Role role = (Role) baseDao.queryUnique("select r from Role r where r.name='管理员'");
		cs.setRole(role);
		String  categoryId = (String)baseDao.add(domain);
		baseDao.add(cs);
		return categoryId;
	}
	
	/**
	 * 返回分类目录下的文档
	 * @param id
	 * @return
	 */
	public List<Document> getDocumentList(String id){
		Category category = baseDao.query(Category.class,id);
		return new ArrayList(category.getDocuments());
	}
	
	/**
	 * 根据分类目录ID返回完整路径
	 * @param categoryId
	 * @return
	 */
	public String getFullPath(String categoryId){
		String path = "";
		Category category = baseDao.query(Category.class,categoryId);
		path = "/"+category.getName();
		while(category.getParent().getParent()!=null){
			category = category.getParent();
			path = "/"+category.getName() + path;
		}
		return path;
	}
	
	/**
	 * 给出路径的分类id(没有就创建)
	 * @param path "/xxx/aaa"
	 * @return 
	 */
	public String getCategoryIdByPath(String path){
		Category category = getDomainRoot();
		String parentId = category.getId();
		String[] items = path.split("/");
		Category c = null;
		for(int i=0;i<items.length;i++){
			c = (Category) baseDao.queryUnique("select c from Category c where c.name =? and c.parent=?",items[i],parentId);
			if(null ==c){
				parentId = addSubCategory(items[i], "", parentId,null);
			}
		}
		return c.getId();
	}

	
	/**
	 * 添加目录书签
	 * @param id
	 * @param request
	 */
	public void addBookmark(String id,HttpServletRequest request){
		Category category = baseDao.query(Category.class,id);		
		Bookmark bookmark = new Bookmark();
		bookmark.setTitle(getFullPath(id));
		bookmark.setType("folder");
		Account account = getSessionAccount(request);
		bookmark.setAccount(account);
		bookmark.setCategory(category);
		bookmark.setLastModifiedDate(new Date());
		bookmark.setDeleted(false);
		baseDao.add(bookmark);
	}
	
	/**
	 * 删除目录书签
	 * @param id 书签ID
	 */
	public void deleteBookmark(String id){
		baseDao.delete(Bookmark.class, id);
	}
	
	/**
	 * 重建分类Map
	 */
	private void buildCategoryMap(){
		List<Category> categories = baseDao.query("select c from Category c where c.parent.id != null ");
		for (Category category :categories) {
			categoryMap.put(category.getId(), getFullPath(category.getId()));
		}
	}
	
	/**
	 * 获取文档分类路径
	 * @param documentId 文档ID
	 * @return
	 */
	public String getCategoryString(String documentId){
		String cat = "";
		Document document = baseDao.query(Document.class,documentId);
		Category category = document.getCategories().iterator().next();
		cat = getFullPath(category.getId())+",";
		if(cat.length()>0){
			return cat.substring(0,cat.length()-1);
		}else {
			return "";
		}
	}
	
	
	
	/**
	 * 获取域的根
	 * @return
	 */
	public Category getDomainRoot(){
		return (Category) baseDao.queryUnique("select c from Category c where c.name = 'root'");
	}
	
	/**
	 * 根据分类的字符串，获取分类所属ID,分类字符串格式"/aa/bb/cc",不存在返回""
	 * @param name 分类路径
	 * @param create 路径不存在时是否创建
	 * @return 当create=false时，路径不存在则返回""，否则必定返回分类路径ID
	 */
	public String getCatgeoryIdByCategoryString(String name,boolean create ){
		if(name.length() >0){
			String[] items = name.substring(1).split("/");
			Category category = getDomainRoot();
			String parentId = category.getId();
			for(String item: items){
				try {
					category = (Category) baseDao.queryUnique("select c from Category c where c.name='"+item+"' and c.parent.id = '"+parentId+"'");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(null == category){
					if(!create){
						return "";
					}else {
						parentId = addSubCategory(item, "", parentId,null);
						category = baseDao.query(Category.class,parentId);
					}
				}else {
					parentId = category.getId();
				}
			}				
			return category.getId();
		}
		return "";
	}
	
}
