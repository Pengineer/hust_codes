<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="choose_bar">
	<ul>
		<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
		<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
		<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
		{if fundList.status ==0}
			<li id="view_mod_pop"><input class="btn1" type="button" value="修改" /></li>
		{/if}
		{if fundList.status ==0}
			<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
		{/if}
	</ul>
</div>