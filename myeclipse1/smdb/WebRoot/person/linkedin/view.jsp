<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%> 
<div class="main" id="view_show" style="display:none; height:245px; overflow-y:auto; padding: 20px 0 0 20px;margin:20px 5px 0 10px;">
	<textarea id="view_template" style="display:none;">
		<div class="main_content">
			{for item in chats}
					{if item.person == '<s:property value="#session.loginer.person.id"/>'}
						<div style="width: 370px;">
							<span ><span style="color:red">${item.sendName}：</span>${item.message}</span>
						</div>
					{else}
						<div style="position:relative;left:380px;width: 380px;">
							<span><span style="color:red">${item.sendName}：</span>${item.message}</span>
						</div>
					{/if}
				<br/>
			{/for}
		</div>
	</textarea>
	<div id="view_container" style="display:none; clear:both;"></div>
</div>
