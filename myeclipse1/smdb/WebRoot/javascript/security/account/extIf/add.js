define(function(require, exports, module) {
	require('pop-init');
	var datepick = require('datepick-init');

	var init = function(){
		$("#accountType").empty(); //账号类型显示
		$("#accountType").append(thisPopLayer.inData.data.typeName);
		$("#belongName").empty(); //账号所属单位或人员名称显示
		$("#belongName").append(thisPopLayer.inData.data.belongName);
	}
	
	exports.init = function() {
		datepick.init();
		init();
		$("#submit").click(function() {// 提交
			$("#accountName").parent("td").next("td").html('');
			var accountName = $("#accountName").val();
			if(accountName.trim() == "" || accountName.trim() == null){
				$("#accountName").parent("td").next("td").append('<span class="error">账号名不能为空</span>');
				return false;
			} else if(!/^[0-9a-zA-Z_]{3,40}$/.test(accountName.trim())){
				$("#accountName").parent("td").next("td").append('<span class="error">账号名为3-40位的数字字母或者下划线</span>');
				return false;
			} else{
				$("#accountName").parent("td").next("td").html('');
			}
			var url ="";
			var type = $("#aType").val();// 枚举类型，前台显示为字符串
			var principal = $("#aPrincipal").val();
			var accountType = new Array("","","ministry","province","university","","department","institute","expert","teacher","student");
			var accountPrincipal = new Array("sub","main");
			
			if(type == "MINISTRY") {
				url = "account/ministry/" + accountPrincipal[principal] + "/extIfAdd.action";
			} else if (type == "PROVINCE") {
				url = "account/province/" + accountPrincipal[principal] + "/extIfAdd.action";
			} else if (type == "MINISTRY_UNIVERSITY" || type == "LOCAL_UNIVERSITY") {
				url = "account/university/" + accountPrincipal[principal] + "/extIfAdd.action";
			} else if (type == "DEPARTMENT") {
				url = "account/department/" + accountPrincipal[principal] + "/extIfAdd.action";
			} else if (type == "INSTITUTE") {
				url = "account/institute/" + accountPrincipal[principal] + "/extIfAdd.action";
			} else if (type == "EXPERT") {
				url ="account/expert/extIfAdd.action";
			} else if (type == "TEACHER") {
				url ="account/teacher/extIfAdd.action";
			} else if (type == "STUDENT") {
				url ="account/student/extIfAdd.action";
			} else{
				alert("账号类型有误，请重新添加");
				return false;
			}
			$.ajax({
				url: url,
				type: "post",
				data: "belongEntityId=" + thisPopLayer.inData.data.belongId + "&passport.name="+$("#accountName").val()+"&account.status="+$("input[name='accountStatus'][@checked]").val()+"&account.expireDate="+$("#expireDate").val()+"&flag="+$("#flag").val(),
				dataType: "json",
				success:function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						thisPopLayer.callBack({
							data : {"id" : result.accountId, "name" : $("#accountName").val()}
						});
						thisPopLayer.destroy();
					}else{
						alert(result.errorInfo);
					}
				}
			});
		});
	}
})
