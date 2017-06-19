define(function(require, exports, module) {
	var view = require('javascript/view');
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	
	/**
	 * 显示查看内容
	 * @param {Object} result
	 */
	function showDetails(result) {
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr("value", result.fundList.id);
			$("#advSearch_entityId").attr("value", result.fundList.id);
			$("#fundList_entityId").attr("value", result.fundList.id);
//			$("#listForFundList").attr("value", result.listForFundList);
//			$("#accountId").attr("value", result.account.id);
			// 目前查看页面分标签显示初始化模板有两种方法，一是下面的按顺序灌入模板，二是调用template_tool.js
			$("#view_choose_bar").html(TrimPath.processDOMTemplate("view_choose_bar_template", result));
//			$("#view_common").html(TrimPath.processDOMTemplate("view_common_template", result));
			$("#view_base").html(TrimPath.processDOMTemplate("view_base_template", result));
			$("#view_unit_fund").html(TrimPath.processDOMTemplate("view_unit_fund_template", result));
			setOddEvenLine("unit_fund", 0);
			if (typeof(result.unitFundList) == "undefined" || result.unitFundList == 0) {
					setColspan("unit_fund");
				}
			view.inittabs();// 初始化以上标签
			$("#view_content").show();
			$("#view_unit_fund").show();
			// 如果有，则显示提示信息
			var pageInfo = $("#pageInfo").val();
			if (pageInfo != undefined && pageInfo != "") {
				alert(pageInfo);
				$("#pageInfo").val("");
			}
			// 重新加载日志信息
			currentPageNo = 1;
			startPageNumber = 1;
			list.getData();
		} else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	}
	
	exports.init = function(nameSpace) {
		view.show(nameSpace, showDetails);
		view.add(nameSpace);
		view.mod(nameSpace);
		view.del(nameSpace, "您确定要删除此清单吗？");
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		view.back(nameSpace);// 所有查看页面公共部分
		Template_tool.init();
		view.inittabs();
//		initUniView();//按高校显示
		
		$(".modify_fee").live("click", function() {
			var getPara = function(obj){
				return  "?fee=" + obj["fee"] + "&fundId=" + obj["fundId"];
			};
			popModifyFee({
				title : "修改金额",
				src : nameSpace + "/toModifyFee.action?entityId=" + this.id,
				callBack : function(dis){
				$.ajax({
					url: basePath + nameSpace + "/modifyFee.action",
					type: "post",
					data: "fee=" + dis.fee + "&fundId=" + dis.fundId,
					dataType: "json",
					success: function (result) {
							if (result.errorInfo == null || result.errorInfo == "") {
								view.show(nameSpace, showDetails);
							} else {
								alert(result.errorInfo);
							}
						}	
					});
				}
			});
			return false;
		});
		
		var optionssearch = {
			dataType: "json",
			success: function (result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					$("#view_unit_fund").html(TrimPath.processDOMTemplate("view_unit_fund_template", result));
					setOddEvenLine("unit_fund", 0);
					if (typeof(result.unitFundList) == "undefined" || result.unitFundList == 0) {
						setColspan("unit_fund");
					}
					$("#view_unit_fund").show();
				} else {
					alert(result.errorInfo);
				}
			}
		};
		
		$("#button_query").bind("click", function() {// 初级检索
			var keyword4unit = $("#keyword4unit").val().trim();
			var searchType4unit = $("#search_type_4unit").val().trim();
			$("#search4unit").attr("action", nameSpace + "/searchForUnitFundList.action");
			$("#search4unit").ajaxSubmit(optionssearch);
			return false;
		});
		
		$("#view_mod_pop").live("click",function() {
			var getPara = function(obj){
				return  "?listName=" + obj["listName"] + "&rate=" + obj["rate"] + "&note=" + obj["note"] + "&entityId=" + obj["entityId"];
			};
			popModifyFundList({
				title : "修改拨款清单",
				src : nameSpace + "/toModify.action?entityId=" + $("#entityId").val(),
//				callBack : function(result){
//					window.location.href = basePath + nameSpace + "/modify.action" + getPara(result);
//					this.destroy();
//				}
				callBack : function(dis){
					$.ajax({
						url: basePath + nameSpace + "/modify.action",
						type: "post",
						data: "listName=" + dis.listName + "&rate=" + dis.rate + "&note=" + dis.note + "&entityId=" + dis.entityId,
						dataType: "json",
						success: function (result) {
								if (result.errorInfo == null || result.errorInfo == "") {
									view.show(nameSpace, showDetails);
								} else {
									alert(result.errorInfo);
								}
							}	
						});
					}
			});
		});
		
		$("#list_modify").live("click", function() {// 删除按钮
			var cnt = count("entityIds");
			var projectNum = $("#projectNum").val()
			if (cnt == 0) {
				alert("请选择要删除的记录！");
			} else {
				if (confirm("您确定要删除选中的记录吗？")) {
					if($("#checkedIds").length > 0){
						$("#checkedIds").attr("value", "");
					};
					$("#type").attr("value", 1);
					$("#pagenumber").attr("value", $("#list_goto").val());
					$("#list").attr("action", nameSpace + "/fundListDelete.action");
					$("#list").submit();
//					window.location.href = basePath + nameSpace+"/toView.action?entityId="+$("#entityId").val();
					$.ajax({
						url: basePath + nameSpace + "/view.action",
						type: "post",
						data: "entityId="+$("#entityId").val(),
						dataType: "json",
						success: function (result) {
							view.show(nameSpace, showDetails);
						}	
					});
				}
			}
			return false;
		});
		$("#view_audit").live("click", function() {// 审核按钮
				if (confirm("清单拨款后无法修改，您确定要继续吗？")) {
					var url = basePath + nameSpace + "/toAudit.action?entityId=" + $("#entityId").val();
					window.location.href = url;
					return false;
				}
			return false;
		});
		
	}
})

