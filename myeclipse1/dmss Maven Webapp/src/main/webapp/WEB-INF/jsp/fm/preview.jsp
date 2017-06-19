<%@ page language="java" contentType="text/html; charset=utf-8"
     pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>文档在线预览</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
    <style type="text/css" media="screen">
        html, body	{ height:100%; }
        body { margin:0 auto,0,auto; overflow:auto; }
        #flashContent { display:none; }
    </style>

    <link rel="stylesheet" type="text/css" href="plugins/FlexPaper-2.1.9/css/flexpaper.css" />
    <script type="text/javascript" src="plugins/FlexPaper-2.1.9/js/jquery.min.js"></script>
    <script type="text/javascript" src="plugins/FlexPaper-2.1.9/js/flexpaper.js"></script>
    <script type="text/javascript" src="plugins/FlexPaper-2.1.9/js/flexpaper_handlers.js"></script>
</head>
<body>

<div id="documentViewer" class="flexpaper_viewer" style="width:900px;margin:0 auto"></div>
<script type="text/javascript">


    $('#documentViewer').FlexPaperViewer(
            { config : {

                SWFFile : "<%=basePath%>fm/document/preview/swf/+${id}",

                Scale : 0.6,
                ZoomTransition : 'easeOut',
                ZoomTime : 0.5,
                ZoomInterval : 0.2,
                FitPageOnLoad : true,
                FitWidthOnLoad : true,
                FullScreenAsMaxWindow : false,
                ProgressiveLoading : false,
                MinZoomSize : 0.2,
                MaxZoomSize : 5,
                SearchMatchAll : false,
                InitViewMode : 'Portrait',
                RenderingOrder : 'flash',
                StartAtPage : '',

                ViewModeToolsVisible : true,
                ZoomToolsVisible : true,
                NavToolsVisible : true,
                CursorToolsVisible : true,
                SearchToolsVisible : true,
                WMode : 'window',
                localeChain: 'zh_CN'
            }}
    );
</script>
</body>
</html>