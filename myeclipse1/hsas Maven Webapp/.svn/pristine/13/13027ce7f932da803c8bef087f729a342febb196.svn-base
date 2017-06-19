<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%><%--添加对EL表达式的支持--%>
<%@ taglib prefix="s" uri="/struts-tags"%> <%--添加对struts标签的支持 --%>
<!DOCTYPE html>
<html>
    <head>
        <title>湖北省社会科学优秀成果奖励申报评审系统</title>
        <%@ include file="/jsp/base.jsp"%>
        <link rel="stylesheet" href="lib/uploadify/css/uploadify.css">
        <link rel="stylesheet" href="lib/bootstrap-datepicker-1.4.0-dist/css/bootstrap-datepicker3.css">
    </head>
    <body>
   		<%@ include file="/jsp/nav.jsp"%>
   		<a name="top" id="top"></a>
			<div class="row main-content">
				<ol class="breadcrumb mybreadcrumb">当前位置：
				  <li><a href="#">投稿</a></li>
				  <li class="active">课题投稿</li>
				</ol>
			    <div class="col-xs-10">
				   <div class="survey_add addTable">
				    	<form action="solicitpapers/add.action" method="post" id="topicContribute">
				    	<input type="hidden" name="solicitPapers.type" value="0">
				    		<table width="100%">
				    			<!-- 从前台向后台传数据两种方法1：直接写入name=对象名.属性值；2：加上required，在后台逐个访问标签 -->
				    			<tbody><tr>
				    				<td width="80" class="text-right required-label">论文名称：</td>
				    				<td><input type="text" name="solicitPapers.name" class="form-control input-sm validate[required]" placeholder="请输入名称"></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right required-label">申请人：</td>
				    				<td><input type="text" name="solicitPapers.author" class="form-control input-sm validate[required]" placeholder="请输入申请人"></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right required-label">性别：</td>
				    				<td>
					    				<select  name="solicitPapers.gender" class="form-control input-sm validate[required]" placeholder="请输入性别">
					    					<option selected="selected" value="">请选择</option>
											<option value="男">男</option>
											<option value="女">女</option>
					    				</select>
				    				</td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right">出生年月：</td>
				    				<td><input type="text" name="solicitPapers.birthday" class="form-control input-sm dataPicker validate[custom[date]]" placeholder="请输入出生年月" data-date-format="yyyy-mm-dd"></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right required-label">职称：</td>
				    				<td><input type="text" name="solicitPapers.position" class="form-control input-sm validate[required]" placeholder="请输入职称"></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right required-label">所在单位：</td>
				    				<td><input type="text" name="solicitPapers.unit" class="form-control input-sm validate[required]" placeholder="请输入所在单位"></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right required-label">联系电话：</td>
				    				<td><input type="text" name="solicitPapers.phone" class="form-control input-sm validate[required, custom[phone]]" placeholder="请输入联系电话"></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right">联系地址：</td>
				    				<td><input type="text" name="solicitPapers.address" class="form-control input-sm " placeholder="请输入联系地址"></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right">邮政编码：</td>
				    				<td><input type="text" name="solicitPapers.postcode" class="form-control input-sm validate[custom[chinaZip]] " placeholder="请输入邮政编码"></td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right required-label">电子邮件：</td>
				    				<td><input type="text" name="solicitPapers.email" class="form-control input-sm validate[required, custom[email]]" placeholder="请输入电子邮件"></td>
				    			</tr>
				    			<tr>
				    				<td width="80"  class = "text-right required-label">上传附件：</td>
				    				<td class='position-relative'>
				    					<label class="sr-only" for="inputfile">文件输入</label>
				    					<input type="file" id="solicitPapersUpload"  name="solicitPapers.file" class='validate[required]'>
				    					<input type="text" class='validate[required] pull-right validation-file-input' name="solicitPapers.fileName" value="">
				        			</td>
				    			</tr>
				    			<tr>
				    				<td width="80" class="text-right">简介：</td>
				    				<td><textarea class="form-control input-sm " name="solicitPapers.note" rows="3" placeholder="请输入简介"></textarea></td>
				    			</tr>
				    		</tbody></table>
				    		<div id="optr" class="btn_bar1">
				            	<div class="btn-group">
				            		<input type="submit" value="提交" class="btn btn-default btn-sm" id="submit">
				            	</div>
				            </div>
				   		</form>
				    </div>
			    </div>
			</div>
			<div class="row">
   		<%@ include file="/jsp/footer.jsp"%>
		<script>
		    seajs.use("js/solicitPapers/topic.js", function(contribute) {
		    });
		</script>
	</body>
</html>