package edu.whut.xml;

/**
 * XML Schema : 用来替换DTD的XML文档约束技术
 * 
 * 相对于DTD的特点：
 * 1，XML Schema符合XML语法结构（用一个XML去约束另一个XML）；
 * 2，DOM、SAX等XML API很容易解析出XML Schema文档中的内容；
 * 3，XML Schema对名词空间支持的非常好；
 * 4，XML Schema比XML DTD支持更多的数据类型，并支持用户自定义新的类型；
 * 5，XML Schema定义约束的能力非常强大，可以对XML实例文档作出细致的语句限制；
 * 6，（缺点）XML Schema不能像XML DTD一样定义实体，，比DTD更复杂，但XML Schema已经是w3c组织的标准，正逐步取代DTD。
 *
 * 关于XML Schema约束：
 * 1，XML Schema文件自身就是一个XML文件，但它的扩展名通常为.xsd；
 * 2，一个XML Schema文档通常称之为模式文档（约束文档），遵循这个文档书写的XML文件称之为实例文档；
 * 3，和XML文件一样，一个XML Schema文档也必须有一个根节点，但这个根节点的名称为schema；
 * 4，编写了XML Schema约束文档之后，通常需要把这个文件中声明的元素绑定到一个URL地址上，在XML Schema
 *    技术中有一个专业术语来描述这个过程，即把XML Schema文档声明的元素绑定到一个名称空间上，以后XML
 *    文件就可以通过这个URL（即名称空间）来告诉解析引擎，XML文档中编写的元素来自哪里，被谁约束。
 *    
 * xmlns就是声明一个名称空间。xmlns:itcast="http://www.itcast.com" 声明了一个名称为itcast的名称空间
 * 
 * 例如：
 * <?xml version="1.0" encoding="UTF-8"?>
 * <itcast:书架  xmlns:itcast="http://www.itcast.com"         用xmlns声明一个名称空间，该名称空间和约束文档里面的targetnamespace属性定义的名称空间（用于将约束文档中的标签绑定到该URI上）一样，指明书架标签是来自该名称空间
 *             xmlns:xsi="http://www.w3.org/2001/XMLSchema-Instance"   用xmlns在声明一个总名称空间，并绑定到前缀xsi上，该名称空间对应的约束文档不用指明
 *               xsi:schemaLocation="http://www.itcast.com  edu/whut/xml/book.xsd">  由于名称空间本身就是一个比较虚的概念，因此第一行并不能指明约束文档的具体位置，所以要用schemaLocation属性来指明名称空间对应的约束文档的具体位置，第一个参数是名称空间，第二个参数是约束文档的路径，schemaLocation属性来自xsi指定的名称空间，即总名称空间。也就是利用总名称空间的属性schemaLocation来指定约束文档的位置。
 *     <itcast:书>
 *         <itcast:书名>...</itcast:书名>
 *         <itcast:作者>...</itcast:作者>
 *         <itcast:售价>...</itcast:售价>
 *     </itcast:书>
 * </itcast:书架>
 * 
 * 名称空间：
 *    在XML schema中，每隔约束文档都被赋予了一个唯一的名称空间，名称空间用一个URI（uniform resource identifier 统一资源标识符）来表示。
 * 在XML实例文件中书写标签时，可以通过名称空间声明（xmlns），来指明该标签来自哪里。
 * 注意：名称空间的名字容易让人产生错觉，尽管以http://开头，但是它不是URL，并不指向一个包含模式定义的文件。事实上，这个URI根本没有指定任何
 * 文件，只是一个分配的名字。   
 */

public class Demo6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
