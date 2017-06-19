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
	document.getElementById("province").onclick=function(){
		var xmlhttp = createXMLHttpRequest();
		
		xmlhttp.onreadystatechange=function(){
			if (xmlhttp.readyState==4){
				if(xmlhttp.status==200 || xmlhttp.status==304){
					var xmldoc = xmlhttp.responseXML;
					var provinceElements = xmldoc.getElementsByTagName("province");
					
					//删除原先的节点
					var selectElement = document.getElementById("province");
					var oldoptions = selectElement.getElementsByTagName("option");
					for(var i=oldoptions.length-1; i>=0; i--){
						selectElement.removeChild(oldoptions[i]);
					}
					
					//增加从服务器传来的节点
					for(var i=0; i<provinceElements.length; i++){
						var optionElement = document.createElement("option");
						var attr = provinceElements[i].getAttribute("name");
						optionElement.setAttribute("city", attr);
						
						var textElement = document.createTextNode(attr);
						optionElement.appendChild(textElement);
						
						document.getElementById("province").appendChild(optionElement);
					}
					
					
				}
			}								
		}
		
		xmlhttp.open("post","../XmlFileServlet",true);
		xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xmlhttp.send(null);
	}
}