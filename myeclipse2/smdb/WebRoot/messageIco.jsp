<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<li id="message" style="cursor:pointer; float:right; margin-right:20px; *margin-right:10px; margin-top:0px; *margin-top:-12px;"><img src="image/g01.gif" /><span style="color:#7D7D7D; margin-left:5px;">我要留言</span></li>
<script type="text/javascript">
$(document).ready(function(){
	//外网留言按钮，点击弹出添加页面
	$("#message").live('click', function(){
		addMessage();
		return false;
	})
});
function addMessage(){
	popAddMessage({
		src : "message/outer/toAdd.action",
		callBack : function(result){
			
		}
	});
}
</script>
