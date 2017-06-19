<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>json2.jsp</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <script type="text/javascript">
  /*创建如下json格式：        
                  姓名：张三
                  年龄：20
                  电话：{固定电话：1111，移动电话：2222}
                  地址：{
                                        家庭地址：武汉，邮编：430000
                                        公司地址：美国，邮编：000911
          }
  */
  	var person={"name":"张三",
  	            "age":20,
  	            "phone":{"homephone":1111, "telephone":2222}, 	                    
  	             address:[
  	                      {homeadd:"wuhan","emailadd":430000}, 	                                 
  	                      {companyadd:"USA","emailadd":000911}
  	                     ]
  	           }
  	alert(person.address[0].homeadd);
  </script>
  
  <body>
    This is my JSP page.. <br>
  </body>
   
</html>
