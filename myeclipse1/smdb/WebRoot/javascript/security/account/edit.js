define(function(require, exports, module) {
	var datepick = require("datepick-init");
	require('validate');
	require('pop');
	require('pop-self');
	
	var flag = 0;

	// 添加一行
	var addRow = function(tableId, trId) {
		// 获取table dom
		var idTB = document.getElementById(tableId);
		// 获取当前表格行数
		var len = idTB.rows.length;
		// 在当前表格末尾插入一行
		var oTR = idTB.insertRow(len);
		var $oTR = $(oTR);
		$oTR.addClass("tr_valid");
		// 获取行号rowIndex
		var tmpNum = oTR.rowIndex - 1;
		$oTR.html($("#" + trId).html());
		$(":input", $oTR).each(function(key, value){ // 每个ip子段给不同的name, 方便校验
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			flag++;
		});
	};

	// 动态加载ip子段的校验
	var dynamic_valid = function(ip_name) {
		$('input[name^="' + ip_name + '"]:visible').each(function(key, value){ 
			$(this).rules("add", {subIp:true}); 
			});
		$('input[name="' + ip_name + '"]').each(function(key, value){ 
			$(this).rules("remove"); 
			});
		$('input[id="' + ip_name + '"]').rules("remove");
	};
	/**
	 * 
	 */
	var getName = function(type, officerType, belongId) {
		var src;
		if(type == 6) {// officer
			if(officerType == 2){// ministry
				src = "account/ministry/sub/getUserName.action?belongId=" + belongId;
			} else if(officerType == 3) {// province
				src = "account/province/sub/getUserName.action?belongId=" + belongId;
			} else if(officerType == 4 || officerType == 5) {// university
				src = "account/university/sub/getUserName.action?belongId=" + belongId;
			} else if(officerType == 6) {// department
				src = "account/department/sub/getUserName.action?belongId=" + belongId;
			} else if(officerType == 7) {// institute
				src = "account/institute/sub/getUserName.action?belongId=" + belongId;
			} 
		} else if(type == 7) {// expert
			src = "account/expert/getUserName.action?belongId=" + belongId;
		} else if(type == 8) {// teacher
			src = "account/teacher/getUserName.action?belongId=" + belongId;
		} else if(type == 15) {// student
			src = "account/student/getUserName.action?belongId=" + belongId;
		}
		if(src) {
			$.get(src, function(result) {
				if(result.passport != null && result.passport != ""){
					$("input[name='passport.name']").val(result.passport).attr("readonly", true);
					alert("此人已有通行证，系统将自动读取通行证的用户名、密码以及防火墙信息。");
				} else {
					$("input[name='passport.name']").val("").attr("readonly", false);
				}
			})
		}
	}
	exports.init = function() {
		$("#belongEntityName").html($("#belongEntityNameValue").val());// 显示已选择的实体名称
		
		datepick.init();// 初始化日期选择器
		
		// 添加允许ip
		$("#addAllowed").live("click", function(){
			addRow("allowedTable", "allowed");
			dynamic_valid("allowedIp");
		});
		// 添加拒绝ip
		$("#addRefused").live("click", function(){
			addRow("refusedTable", "refused");
			dynamic_valid("refusedIp");
		});
		// 删除一行
		$(".delete_row").live("click", function(){
			$(this).parent().parent().remove();
		});
		// 拼接ip，放入指定位置，等待表单提交
		$("#submit").live("click", function(){
			// 先清空要提交字段的值
			$("#allowedIp").attr("value", "");
			$("#refusedIp").attr("value", "");
			// 把 name = allowedIp/refusedIp 的字符串用./; 隔开组成字符串放入指定隐藏域
			var allowedArr = new Array();
			var refusedArr = new Array();
			var allowedIp = refusedIp = "";
			$('input[name*="allowedIp"]').each(function(){
				if(this.value == ""){this.value="*";}
				allowedArr.push(this.value);
			});
			for(i = 0; i < allowedArr.length / 4 - 1; i++){
				allowedIp += allowedArr[4 * i] + "." + allowedArr[4 * i + 1] + "." + allowedArr[4 * i + 2] + "." + allowedArr[4 * i + 3] + "; ";
			}
			$('input[name*="refusedIp"]').each(function(){
				if(this.value == ""){this.value="*";}
				refusedArr.push(this.value);
			});
			for(i = 0; i < refusedArr.length / 4 - 1; i++){
				refusedIp += refusedArr[4 * i] + "." + refusedArr[4 * i + 1] + "." + refusedArr[4 * i + 2] + "." + refusedArr[4 * i + 3] + "; ";
			}
			allowedIp = allowedIp.substring(0, allowedIp.length - 11);// 去除模板table的ip和ip段最后的"; "
			refusedIp = refusedIp.substring(0, refusedIp.length - 11);
			$("#allowedIp").attr("value", allowedIp);
			$("#refusedIp").attr("value", refusedIp);
		});
		
		if($("#allowedIp").length > 0){
			if($("#allowedIp").val() != ""){ //修改页面动态加载已有ip
				var arr = $("#allowedIp").val().split(/[;\.]/);
				for(i = 0; i < arr.length / 4; i++){
					addRow("allowedTable", "allowed");
					dynamic_valid("allowedIp");
				};
				i = 0;
				$(':input[name*="allowedIp"]').each(function(key, value){ 
					if(key < arr.length){
						$(this).attr("value", arr[i]);
						i++;
					}
				});
			}
			if($("#refusedIp").val() != ""){
				var arr = $("#refusedIp").val().split(/[;\.]/);
				for(i = 0; i < arr.length / 4; i++){
					addRow("refusedTable", "refused");
					dynamic_valid("refusedIp");
				};
				i = 0;
				$(':input[name*="refusedIp"]').each(function(key, value){ 
					if(key < arr.length){
						$(this).attr("value", arr[i]);
						i++;
					}
				});
			}
		}
	};
	
	// type和officerType参见popSelect方法
	exports.selectEntity = function(type, officerType) {
		popSelect({
			type : type,
			label : 1,
			officerType : officerType,
			inData : {"id" : $("#belongEntityId").val(), "name" : $("#belongEntityName").text()},
			callBack : function(result){
				var entityId = result.data.id;
				$("#belongEntityId").val(entityId);
				$("#belongEntityName").empty();
				$("#belongEntityName").append(result.data.name);
				$("#belongEntityNameValue").val(result.data.name);
				$("#belongEntityId").valid();
				getName(type, officerType, entityId);
			}
		});
	};
});
