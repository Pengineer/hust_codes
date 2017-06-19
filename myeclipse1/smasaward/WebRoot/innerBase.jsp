<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	//更换页面，载入时先执行一遍清除加载图片
	window.onload = function() {
		if (parent != null) {
			parent.hideLoading();
		}
	};
</script>
<!--[if IE 6]>
<script src="javascript/DD_belatedPNG_0.0.8a-min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<script>
	DD_belatedPNG.fix('.top_menu ul li img, .sub_title img, .sub_title_center, .top_logo img, .footer_l_txt');
</script>
<![endif]-->