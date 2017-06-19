<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>清单查看</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
					seajs.use(['javascript/fundList/post/end/view.js',
								'javascript/projectFund/post/list.js'], function(list,view) {
						list.init();
						view.init();
					});
			</script>
		</head>

		<body>
			<div class="link_bar" >
				当前位置：研究项目经费数据&nbsp;&gt;&nbsp;后期资助项目&nbsp;&gt;&nbsp;结项拨款清单查看
			</div>

			<div class="main">
				<textarea id="view_choose_bar_template" style="display:none;">
					<s:include value="/fundList/viewChooseBar.jsp" />
				</textarea>
				<div id="view_choose_bar" style="clear:both;"></div>
				<div class="main_content">
					<s:include value="/fundList/viewBasic.jsp" />
					<div class="main_content">
						<div id="tabs" class="p_box_bar">
							<ul>
								<li><a href="#forProject">按项目查看</a></li>
								<li><a href="#forUnit">按学校查看</a></li>
							</ul>
						</div>
						<div class="p_box">
							<div id="forProject">
								<s:include value="/fundList/post/end/projectView.jsp" />
							</div>
							<div id="forUnit">
								<s:include value="/fundList/unitView.jsp" />
							</div>
						</div>
					</div>
				</div>
				
			</div>
			
		</body>
	
</html>