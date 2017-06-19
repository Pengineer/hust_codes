/*参看W3school
 * 
 * AJAX建立和服务器的连接，接受服务器请求，处理服务器返回的数据（前后台交互）
 * 开发步骤：
 *   1，创建XMLHttpRequest对象；
 *   2，打开服务器的连接； open
 *   3，发送数据:如需将请求发送到服务器，我们使用 XMLHttpRequest对象的 open(method,url,async)和 send()方法;
 *            参数说明： method：请求的类型；GET 或 POST
                     url：文件在服务器上的位置
                     async：true（异步）或 false（同步），XMLHttpRequest对象如果要用于 AJAX 的话async必须设置为 true
                      如果需要像 HTML 表单那样 POST 数据，请使用 setRequestHeader() 来添加 HTTP 头。
         xmlhttp.open("POST","ajax_test.asp",true);
         xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
         xmlhttp.send("fname=Bill&lname=Gates");
         
                      注释：当使用 async=false 时，请不要编写 onreadystatechange函数 -把代码放到 send()语句后面即可
                      xmlhttp.open("GET","test1.txt",false);
                      xmlhttp.send();
                      document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
 *   
 *   4，服务器端的响应：如需获得来自服务器的响应，请使用 XMLHttpRequest对象的 responseText或 responseXML属性。
 *        responseText: 获得字符串形式的响应数据。
          responseXML:  获得 XML形式的响应数据。
          
     补充：onreadystatechange事件：
             当请求被发送到服务器时，我们需要执行一些基于响应的任务。每当readyState改变时，就会触发onreadystatechange事件。
    readyState属性存有XMLHttpRequest的状态信息。下面是 XMLHttpRequest 对象的三个重要的属性：
                                         （1）  onreadystatechange：存储函数（或函数名），每当 readyState 属性改变时，就会调用该函数。
                                         （2）  readyState：存有 XMLHttpRequest 的状态。从 0 到 4 发生变化。
			        0: 请求未初始化
				    1: 服务器连接已建立
				    2: 请求已接收
				    3: 请求处理中
				    4: 请求已完成，且响应已就绪
	                              （3） status：200:"OK"；404:未找到页面。
		     
             在 onreadystatechange事件中，我们会写入当服务器响应已做好被处理的准备时所要执行的任务。
             当 readyState 等于 4 且状态为 200 时，表示响应已就绪；
 * 
 * 	  XMLHttpRequest是XMLHTTP组件的对象，通过这个对象，AJAX可以像桌面应用程序一样只同服务器进行数据层面的交换，
 * 而不用每次都刷新界面，也不用每次将数据处理的工作都交给服务器来做；这样既减轻了服务器负担又加快了响应速度、缩短了用户等待的
 * 时间。 
 *    所有现代浏览器均支持 XMLHttpRequest 对象（IE5 和 IE6 使用 ActiveXObject）。它用于在后台与服务器交换数据
 * 	     所有现代浏览器（IE7+、Firefox、Chrome、Safari 以及 Opera）均内建 XMLHttpRequest 对象。
 * 
 * tip:当run on server时，先是报出端口被占用的错误，然后，我就复制了一份Tomcat，修改了里面的端口8080-8019等，
 *     但是还是报错，然后百度有人说是JDK版本低，但我的是最新的，自我感觉是因为有一个tomcat在运行，导致我修改端口的
 *     Tomcat无法运行，然后我在任务管理器中结束了所有的javaw.exe进程，哈哈，好了，而且我使用8080端口的Tomcat
 *     也不报错了。（tomcat的运行需要JDK，我之前编程序使用tomcat打开过JDK，可能出现重复加载了）
 */

/**
 * 创建XMLHttpRequest对象
 */
function createXMLHttpRequest(){
	var xmlhttp;
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	return xmlhttp;
}

window.onload=function(){
	document.getElementById("ok").onclick=function(){
		//1
		var xmlRes = createXMLHttpRequest();
		
		//4浏览器接收来自服务器的数据（读取xmlRes，发送与接收是独立的--异步）
		xmlRes.onreadystatechange=function(){
			if (xmlRes.readyState==4){
				if(xmlRes.status==200 || xmlRes.status==304){
					alert(xmlRes.responseText);
				}
			}								
		}
		
		//2
		xmlRes.open("get","./TestServlet",true);
		//3 浏览器发送数据给服务器
		xmlRes.send();
	}
}