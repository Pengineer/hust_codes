<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="css/common.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<link href="image/logo/logo.ico" rel="icon"  type="image/x-icon">
<link href="image/logo/logo.ico" rel="shortcut icon"  type="image/x-icon">
<link href="tool/jquery.datepick/flora.datepick.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var basePath = "<%=basePath%>";
var systemVersion = "<%=application.getAttribute("systemVersion")%>";
</script>
<script type="text/javascript" src="sea.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<script type="text/javascript" src="sea_config.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<script type="text/javascript">
seajs.use('', function() {
	$(function() {
		// 避免异步请求页面，加载图标还在时，载入新页面，出现加载图片无法清除的问题。
		// 页面载入时先执行一遍清除加载图片
		if (parent != null) {
			parent.hideLoading();
		}
		
		$(".image").live("click",function(){
			openOrNotImage($(this));
		});
		
	});
});
</script>
<!--[if IE 6]>
<script src="javascript/DD_belatedPNG_0.0.8a-min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<script type="text/javascript">
	DD_belatedPNG.fix('.top_menu ul li img, .sub_title img, .sub_title_center, .top_logo img, .footer_l_txt');
</script>
<![endif]-->