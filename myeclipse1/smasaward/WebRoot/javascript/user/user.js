// ========================================================================
// 文件名：user.js
//
// 文件说明：
//     本文件主要实现用户管理模块中页面的输入验证。
//
// 历史记录：
// 2010-01-28  龚凡          创建文件，完成基本功能。
// ========================================================================

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

function modifySelect(ob, ff) {
	var cnt = count(ob);
	if (cnt == 0) {
		alert("请选择要修改的用户！");
	} else if (cnt != 1) {
		alert("一次只能选择一个用户进行修改！");
	} else {
		document.forms[ff].action = "user/loadUser.action";
		document.forms[ff].submit();
	}
}

$(document).ready(function(){
	$("#retrieveCode").click(function(){
		if (confirm('您确定要给本账号生成密码重置码吗？生成重置码后，该账号用户可以通过指定的链接重置密码。')) {
			$.ajax({
				url: "user/retrieveCode.action",
				type: "post",
				data: "username = " + $("#username").html().trim(),
				dataType: "json",
				success: function(result) {
					if (result == null || result.errorInfo == null || result.errorInfo == "") {
						setTimeout('alert("密码重置成功");', 200);
					} else {
						alert(result.errorInfo);
					}
				}
			});
		}
		return false;
	});
	
	$("#modifyPassword").click(function(){
		popPasswordModify({
			title : "修改密码",
			src : "user/toModifyPassword.action?username=" + $("#username").html().trim(),
			callBack : function(result){
				if (result == null || result.error == null) {
					alert('修改密码成功');
				} else {
					alert(result.error);
				}
			}
		});
		return false;
	});
})