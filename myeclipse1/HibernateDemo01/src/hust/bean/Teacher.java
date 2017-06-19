package hust.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

/**
 * 主键生成策略：
 * JPA默认的生成策略只有4种（AUTO，IDENTITY，SEQUENCE，TABLE），hibernate使用@GenericGenerator对其进行了扩展
 * 
 * JPA用法：
 * @GeneratedValue(strategy=GenerationType.AUTO)
 *
 * hibernate用法：
 * @GenericGenerator(name = "teacher_idGenerator", strategy = "uuid") //声明一个hibernate的生成器，可以写在id属性或则类名上。
 * @GenericGenerators(value = { @GenericGenerator(name = "teacher_idGenerator1", strategy = "uuid"), @GenericGenerator(name = "teacher_idGenerator2", strategy = "increment") })//声明多个hibernate生成器，只能写在类名上。
 * @GeneratedValue(generator = "idGenerator")     //使用自声明的生成器
 * 
 * hibernate扩展的ID生成策略：
 * GENERATORS.put("uuid", UUIDHexGenerator.class);  
   GENERATORS.put("hilo", TableHiLoGenerator.class);  
   GENERATORS.put("assigned", Assigned.class);  
   GENERATORS.put("identity", IdentityGenerator.class);  
   GENERATORS.put("select", SelectGenerator.class);  
   GENERATORS.put("sequence", SequenceGenerator.class);  
   GENERATORS.put("seqhilo", SequenceHiLoGenerator.class);  
   GENERATORS.put("increment", IncrementGenerator.class);  
   GENERATORS.put("foreign", ForeignGenerator.class);  
   GENERATORS.put("guid", GUIDGenerator.class);  
   GENERATORS.put("uuid.hex", UUIDHexGenerator.class); //uuid.hex is deprecated  
   GENERATORS.put("sequence-identity", SequenceIdentityGenerator.class); 
         以上十二种策略，加上native，hibernate一共默认支持十三种生成策略。 
 */

@Entity                         //javax.persistence.Entity，要使用JPA标准的annotation，最好不要使用hibernate的annotation
@Table(name="t_teacher")
@GenericGenerator(name="IdGenerator", strategy="increment")
public class Teacher {
	private int id;
	private String name;
	private String title;
	
	@Id
	@Column(name="c_id")//类型不用指定，由hibernate指定完成
//	@GenericGenerator(name="IdGenerator", strategy="uuid")
	@GeneratedValue(generator="IdGenerator")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="c_name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="c_title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
