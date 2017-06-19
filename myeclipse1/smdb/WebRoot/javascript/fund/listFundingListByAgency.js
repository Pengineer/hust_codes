define(function(require, exports, module) {
	var list = require('javascript/list');
	var nameSpace = "funding/agencyFunding";
	function popShowFund(args) {
		new top.PopLayer({
			"title": "单位拨款清单",
			"src": args.src,
			"document": top.document,
			"inData": args.inData,
			"callBack": args.callBack
		});
	}
	
	$("#fundingBatchNames").change(function() {
		var fundingBatchId = $("#fundingBatchNames").val().trim();
		$("#search").attr({
			"action":"funding/agencyFunding.action?fundingBatchId=" + fundingBatchId
		});	
		list.getData();
	});//选择批次
	
	$("#update").click(function(){
		parent.loading_flag = true;
		parent.showLoading();
		$.ajax({
			url: nameSpace + "/updateAgencyFee.action?fundingBatchId=" + $("#fundingBatchId").val().trim(),
			type: "post",
			success: function(){
				parent.hideLoading();
				list.getData();
			}
		});
	});// 更新拨款额
	$("#export").click(function(){
		parent.loading_flag = true;
		parent.showLoading();
		window.location.href = basePath + "/funding/agencyFunding/toExport.action?fundingBatchId=" + $("#fundingBatchId").val().trim();
		parent.hideLoading();
	});

	exports.init = function(){
		$("#search").attr({
			"action":"funding/agencyFunding/list.action?fundingBatchId=" + $("#fundingBatchId").val().trim()
		});	// 手动指定请求链接
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn":5,"sortcolumn":6},
			"dealWith": function(){// 表格显示完执行回调函数
				$(".showdetail").each(function(){
					$(this).click(function(){
						var args = {
								src: "funding/fundingList/toPopFundingListByAgency.action",
								inData:{
									fundingBatchId: $("#fundingBatchId").val(),
									fundingBatchName:$("#fundingBatchNames option:selected").html().trim(),
									agencyId:$(this).attr("id"),
									agencyName:$(this).html()
								}
						}
						console.log(args);
						popShowFund(args);
						
						return false;
					});
				});
			}
		});

	}
})