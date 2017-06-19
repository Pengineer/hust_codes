$(function(){
	var nameSpace = "user";
	
	pageShow({"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4}});
	
	$(".linkView").live("click", function(){
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		url += (this.type) ? "&userstatus=" + this.type : "";//(项目列表类型先如是判别)
		window.location.href = url;
		return false;
	});
});

// ==============================================================
// 方法名: aprovSelected
// 方法描述: 本方法主要实现批量批准或启用或停用请求的提交
// 返回值：
// true: 提交请求
// false: 不提交请求
// ==============================================================
function aprovSelected(ob, // 复选框ID
			ff, // form表单名称
			flag) // 用户列表类型
{
	// 判断是否有用户被选中
	var cnt = count(ob);
	if (cnt == 0) {
		if (flag == -1) {
			alert("请选择要停用的用户！");
		} else if (flag == 0) {
			alert("请选择要批准并启用的用户！");
		} else {
			alert("请选择要启用的用户！");
		}
		return false;
	} else {
		if (flag == -1){
			if (confirm("您确定要停用选中的用户吗？")) {
				document.forms[ff].action = "user/groupDisableAccount.action";
				document.forms[ff].submit();
				return true;
			}
		} else if (flag == 0) {
			document.forms[ff].action = "user/toGroupOperationP.action";
			document.forms[ff].submit();
			return true;
		} else {
			document.forms[ff].action = "user/toGroupOperationP.action";
			document.forms[ff].submit();
			return true;
		}
	}
}
