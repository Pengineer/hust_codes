define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function(){
		$("#form_account").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"account.belongId":{required:true},
				"passport.name":{required:true, rangelength:[3,40], userName:true},
				"account.status":{required:true},
				"account.expireDate":{required:true, dateISO:true},
				accountName:{maxlength:40},
				belongUnitName:{maxlength:40},
				createDate1:{dateSMDB:true},
				createDate2:{dateSMDB:true},
				expireDate1:{dateSMDB:true},
				expireDate2:{dateSMDB:true},
				status:{},
				newPassword:{required:true, passWord:true},
				rePassword:{required:true, passWord:true, equalTo:"#form_account_newPassword"}
				
				},
			// 定义验证信息的显示位置
			errorPlacement: function(error, element) { 
				var fieldName = element.attr("name");
				if(fieldName.substring(0, 9) == "allowedIp"){
					var arr = $("#allowedElement").html().split("</span>");
					var flag = 1; // 判断是否将错误信息放到后面td的标示位，flag为1则放入allowedTable后面
					if(arr.length == 1){flag = 1;}// 错误信息区域无错误信息
					else {for(i = 0; i < arr.length - 1; i++){if(arr[i].indexOf("none") == -1){flag = 0; break;}}}// 错误信息区域的错误是隐藏的
					if(flag){ //
						element = $("#allowedTable");
					} else {
						element = $("#errorField");
						}
				} else if(fieldName.substring(0, 9) == "refusedIp"){
					var arr = $("#refusedElement").html().split("</span>");
					var flag = 1;
					if(arr.length == 1){flag = 1;}// 错误信息区域无错误信息
					else {for(i = 0; i < arr.length - 1; i++){if(arr[i].indexOf("none") == -1){flag = 0; break;}}}// 错误信息区域的错误是隐藏的
					if(flag){ //
						element = $("#refusedTable");
					} else {
						element = $("#errorField");
						}
				}
				error.appendTo( element.parent("td").next("td") );
			}
		});
	};
})
