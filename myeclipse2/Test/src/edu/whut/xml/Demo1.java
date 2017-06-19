package edu.whut.xml;
/**xml：即可扩展标记语言，用于描述关系型数据，也经常用作软件的配置文件；
 *      1，编写xml文档一般基于一个约束文档，该文档用于规定xml的书写规范，常用的约束技术有
 *          （1）XML DTD(早期技术)    （2）XML Schema(现在主流技术)
 *          
 *      2，编写好xml文档后，就需要对该文档进行解析，常见解析方式有两种：
 *          （1）DOM解析（SUN官方标准）（2）sax解析（实际应用标准）
 *          
 *          常用的解析开发包：Jaxp(SUN)（最差），Jdom，dom4j（最牛，从JDom中脱离）;
 *          
 *          区别：DOM是基于文档解析的，SAX是基于事件解析的。
 *          （1）Dom解析是将所有标签在内存中先生成一个关系树状表，并将之封装成Document对象
 *               ，因此最大的优点是对文档的crud比较方便，但是最大的缺点就是占用内存比较大，甚至内存溢出；              
 *          （2）sax解析是读一行xml语句，就解析一行，因此最大的优点是占用内存少，解析速度快，
 *               缺点时只适合做文档读取，不适合做文档的crud。      
 */
public class Demo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*java虚拟机启动时，会占用系统的内存空间，默认大小是64M，如果程序超过这个大小，就会
		*抛出内存溢出异常，我们可以在运行时点击Run Configurations，在Arguments的VM arguments
		*中输入-Xmx80m,就可已调整虚拟机占用的内存大小为80MB
		*/
		
	//	byte[] capacity1 = new byte[1024*1024*50];//默认可以
		
		byte[] capacity2 = new byte[1024*1024*70];//默认不可以
	}

}
