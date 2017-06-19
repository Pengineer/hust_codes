define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('form');
	
	/**
	 * 启用账号
	 */
	var enableAccount = function(nameSpace) {
		var cnt = count("entityIds");// 判断是否有账号被选中
		if (cnt == 0) {
			alert("请选择要启用的账号！");
		} else {
			popAccountEnable({
				src : nameSpace + "/toEnable.action",
				callBack : function(result){
					var date = result.data.date;
					$("#pagenumber").val($("#list_pagenumber").val());// 记录当前页码，以便回到此页面
					if(date != null){
						$("#datepick").val(date);
						$("#list").attr("action", nameSpace + "/enable.action");
						$("#list").submit();
					}
				}
			});
		}
	}
	
	/**
	 * 停用账号
	 */
	var disableAccount = function(nameSpace) {
		var cnt = count("entityIds");// 判断是否有账号被选中
		if (cnt == 0) {
			alert("请选择要停用的账号！");
		} else {
			if (confirm("您确定要停用选中的账号吗？")) {
				$("#list").attr("action", nameSpace + "/disable.action");
				$("#list").submit();
			}
		}
	}
	
	/**
	 * 分配角色
	 */
	var districtAccount = function(nameSpace) {
		var cnt = count("entityIds");// 判断是否有账号被选中
		if (cnt == 0) {
			alert("请选择要进行角色分配的账号！");
		} else {
			var assignRoleIds = "";// 读取待分配账号的ID，并拼成以","分隔的字符串
			$("input[name='entityIds']").each(function() {
				if (this.checked) {
					assignRoleIds += $(this).val() + ",";
				}
			});
			
			/**
			 * 弹出分配角色层，由于校级账号存在两类，为了避免同时给两类校级账号分配角色，
			 * 需判断某次操作的账号为同一类账号。
			 * @param {String} entityId 待分配角色的账号ID集合
			 */
			popAccountDistrict({
				src : nameSpace + "/toAssignRole.action?assignRoleIds=" + assignRoleIds + "&type=1",
				callBack : function(result){
					var role = result.data.role;
					$("#pagenumber").val($("#list_pagenumber").val());// 记录当前页码，以便回到此页面
					$("#roleIds").val(role);
					$("#list").attr("action", nameSpace + "/assignRole.action");
					$("#list").submit();
				}
			});
		}
	}
	/**
	 * 初始化列表页面按钮，主要包括：启用、停用、分配角色，
	 * 删除按钮在list.js中统一绑定。
	 */
	exports.initListButton = function(nameSpace) {
		// 因为列表底部的功能按钮每次都由模板生成，所以需要动态绑定JS事件
		$("#list_enable").live("click", function() {
			enableAccount(nameSpace);
			return false;
		});// 绑定启用功能
		$("#list_disable").live("click", function() {
			disableAccount(nameSpace);
			return false;
		});// 绑定停用功能
		$("#list_district").live("click", function() {
			districtAccount(nameSpace);
			return false;
		});// 绑定分配角色功能
	}
});