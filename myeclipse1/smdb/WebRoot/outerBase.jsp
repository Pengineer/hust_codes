<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>" />
<script type="text/javascript">
var basePath = "<%=basePath%>";
var systemVersion = "<%=application.getAttribute("systemVersion")%>";
</script>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="css/common.css?ver=<%=application.getAttribute("systemVersion")%>" rel="stylesheet" type="text/css" />
<link href="css/login.css?ver=<%=application.getAttribute("systemVersion")%>" rel="stylesheet" type="text/css" />
<link href="image/logo/logo.ico"  rel="icon" type="image/x-icon">
<link href="image/logo/logo.ico"  rel="shortcut icon" type="image/x-icon">

<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<script type="text/javascript" src="javascript/lib/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<script type="text/javascript" src="javascript/lib/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<script type="text/javascript" src="tool/poplayer/js/pop.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<script type="text/javascript" src="tool/poplayer/js/pop-self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<!--[if IE 6]>
	<script src="javascript/DD_belatedPNG_0.0.8a-min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript">
		DD_belatedPNG.fix('.login_img img, .logn_ftxt, .login_error_title');
	</script>
<![endif]-->
