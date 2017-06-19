<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>fileupload.jsp</title>
  </head>
  
  <body>
  
  	<%-- 或则：action="<%=request.getContextPath()%>/test3/upload.action" --%>
     <form action="${pageContext.request.contextPath }/test3/uploadall.action" enctype="multipart/form-data" method="post">
     	文件1:<input type="file" name="image" /><br>  <!-- name值要相同 -->
     	文件2:<input type="file" name="image" /><br>
     	文件3:<input type="file" name="image" /><br>
     	<input type="submit" value="上传" />
     </form>   
  </body>
</html>