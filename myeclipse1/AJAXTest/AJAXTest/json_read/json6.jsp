<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>接收从服务器传来的较复杂json数据（JsonServlet1发送），浏览器（本jsp页面）处理接收的json数据</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
     <input type="button" name="ok" value="提交" id="ok"/>
  </body>
  
  <script type="text/javascript">
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
			var xmlReq = createXMLHttpRequest();
			
			xmlReq.onreadystatechange=function(){
				if (xmlReq.readyState==4){
					if(xmlReq.status==200 || xmlReq.status==304){
						var data = xmlReq.responseText;
						var dataObj = eval("("+data+")");
				
						alert(dataObj[0].pid+"   "+dataObj[0].pname);
					}
				}								
			}
			
			xmlReq.open("post","../JsonServlet1",true);
			xmlReq.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlReq.send();
		}
	}
  </script>
</html>
