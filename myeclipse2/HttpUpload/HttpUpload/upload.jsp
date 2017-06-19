<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>文件上传</title>
  </head>
  
  <body>
  <%--文件上传必须要设置enctype属性值为multipart/form-data，而且method必须为post --%>
    <form action="${pageContext.request.contextPath }/UploadServlet2" enctype="multipart/form-data" method="post">
    	上传用户：<input type="text" name="username" /><br>
    	上传文件1：<input type="file" name="file1" /><br>
    	上传文件2：<input type="file" name="file2" /><br>
    		<input type="submit" value="上传"  /><br> 
    </form>
  </body>
</html>