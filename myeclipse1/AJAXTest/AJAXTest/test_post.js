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
		var xmlRes = createXMLHttpRequest();
		xmlRes.onreadystatechange=function(){
			if (xmlRes.readyState==4){
				if(xmlRes.status==200 || xmlRes.status==304){
					alert(xmlRes.responseText);
				}
			}								
		}		
		xmlRes.open("post","./TestServlet",true);	
		// 如果用 POST 请求向服务器发送数据，需要将 “Content-type” 的首部设置为 “application/x-www-form-urlencoded”.
		// * 它会告知服务器正在发送数据，并且数据已经符合URL编码了。该方法必须在open()之后才能调用。	 
		xmlRes.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xmlRes.send();
	}
}

/*$(document).ready(function(){
	document.getElementById("ok").onclick=function(){
		var xmlRes = createXMLHttpRequest();
		xmlRes.onreadystatechange=function(){
			if (xmlRes.readyState==4){
				if(xmlRes.status==200 || xmlRes.status==304){
					alert(xmlRes.responseText);
				}
			}								
		}		
		xmlRes.open("post","./TestServlet",true);	
		// 如果用 POST 请求向服务器发送数据，需要将 “Content-type” 的首部设置为 “application/x-www-form-urlencoded”.
		// * 它会告知服务器正在发送数据，并且数据已经符合URL编码了。该方法必须在open()之后才能调用。	 
		xmlRes.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xmlRes.send();
	} 
});*/