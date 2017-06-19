package csdc.bean;
import csdc.tool.tableKit.ITableKit;

/**
 * 表信息类
 * @author 徐涵
 *
 */
public class TableInfo {
	public String id;
	public ITableKit kit;
	public String name;

	public TableInfo (String id, String kit, String name) throws Exception{
		System.out.println("TableInfo 初始化");

		this.id = id;
		this.kit = (ITableKit) Class.forName(kit).newInstance();
		System.out.println("kit 初始化");
		this.name = name;
		System.out.println("TableInfo 初始化完毕");
	}
}
