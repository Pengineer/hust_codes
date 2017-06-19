/*
 * @author: Liujia
 */
define(function(require, exports, module) {
	require("jquery");
	require("template");
	require("pop-self");
	var list = require("javascript/list");
	var nameSpace = "funding/fundingList"
	var fundingBatchid = "";
	var agencyId = "";
/*
 * 定义拨款清单弹层
 */
	function popShowFund(args) {
		new top.PopLayer({
			"title": "拨款清单明细",
			"src": args.src,
			"document": top.document,
			"inData": args.inData,
			"callBack": args.callBack
		});
	}
	
/*
 *  定义显示表格的回调函数
 */
/*	var showDate = function(data){
		$("#batchDate").html(data.batchDate.substring(0,10));
		$("#totalFee").html(data.totalFee + " 万元");
		$("#agencyName").html(data.agencyName);
		var num = 1; 
		for(var item in data.unitFundingList){
			data.unitFundingList[item].push(num);
			num ++;
		}
		var result = TrimPath.processDOMTemplate("batchlist", data);
		$("#showBatchList").html(result);
		setOddEvenLine("showBatchList", 0); // 设置奇偶行效果
		$("#showBatchList").show();
		$(".showdetail").each(function(){
			$(this).click(function(){
				var args = {
						src: "fund/toList.action?fundingListId=" + $(this).attr("id")+"&update=1",
						inData:{
							fundingListId: $(this).attr("id")
						}
				}
				popShowFund(args);
				return false;
			});
		});
	}*/
	
/*
 * 显示选择机构的弹层实现
 */
	$("#selectAgency").click(function(){
		popSelect({
			type : 3,
			inData : {"id" : "", "name" : ""},
			callBack : function(result){
				$("#selectedAgency").attr({
					"value":result.data.id
				});
				$("#warning").hide();
				doWithXX(result.data.name,"selectedAgency","selected");
				$("#" + result.data.name).click(function(){
					$("#agencyId").val("");
				});
				$("#agencyId").val(result.data.id);
				$("#selected").css({
					"display": "inline-block"
				});
				$("#confirm").show();
				
			}
		});
	});
	
/*
 * 下拉框选择批次，更新表格
 */
	$("#fundingBatchNames").change(function() {
		var fundingBatchId = $("#fundingBatchNames").val().trim();
		$("#search").attr({
			"action":"funding/fundingList/list.action?fundingBatchId=" + fundingBatchId
		});	
		list.getData();
	});
	
//	$("#batchNames").change();//手动触发，默认显示最新的数据
	
	$("#toSearch").live("click",function(){
		var fundingBatchId = $("#fundingBatchNames").val().trim();
		var agencyId = $("#agencyId").val().trim();
		if(agencyId){
			$("#search").attr({
				"action":"funding/fundingList/list.action?fundingBatchId=" + fundingBatchId + "&agencyId=" + agencyId
			});	
			list.getData();
			//根据用户选择的机构筛选相应的数据		
		} else {
			$("#warning").html("请选择收款单位！").css({
				"color":"red"
			}).show();//如果用户没有选择机构，则提醒用户
			$("#search").attr({
				"action":"funding/fundingList/list.action?fundingBatchId=" + fundingBatchId + "&agencyId=" + $("#agencyId").val().trim()
			});	
			list.getData();//显示所以信息
		}

	});
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3},
			"dealWith":function(data){
				if(fundingBatchid !== $("#fundingBatchNames").val().trim() || agencyId !== $("#agencyId").val()){
					$("#totalFee").html(data.totalFee + " 万元");
					$("#fundingBatchDate").html(data.fundingBatchDate.substring(0,10));
					fundingBatchid = $("#fundingBatchNames").val().trim();
					agencyId = $("#agencyId").val();
				} 
				$(".showdetail").each(function(){
					$(this).click(function(){
						var args = {
								src: "funding/funding/toList.action?fundingListId=" + $(this).attr("id")+"&update=1" + "&agencyId=" + $("#agencyId").val().trim() + "&fundingBatchId=" + $("#fundingBatchNames").val().trim(),
								inData:{
									fundingListId: $(this).attr("id"),
									agencyId: $("#agencyId").val(),
									fundingBatchId:$("#fundingBatchNames").val().trim()
								}
						}
						popShowFund(args);
						return false;
					});
				});
				
				$("#list_button_query").unbind("click");
				$("#list_button_query").click(function(){
					var keyword = $("#keyword").val().trim();
					$("#keyword").val(keyword);
					var url = nameSpace + "/simpleSearch.action?" + "agencyId=" + $("#agencyId").val();
					$("#search").attr("action", url);
					if($("#checkedIds").length > 0){//检索时要判断是否有checkedIds，如果有要清空
						$("#checkedIds").attr("value", "");
					};
					list.getData();
					return false;
				});
				
				var showSort = function(sortColumn) {// 排序
					$("#list_sortcolumn").attr("value", sortColumn);
					$("#list_pagenumber").attr("value", $("#list_goto").val());
					$("#search").attr("action", nameSpace + "/sort.action?agencyId=" + $("#agencyId").val());
					list.getData();
				};
				var sortColumnId = ["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3"];
				var sortColumnValue = {"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3};
				if (sortColumnId != null && sortColumnValue != null) {
					for (var i = 0; i < sortColumnId.length; i++) {
						$("#" + sortColumnId[i]).die("click");
						$("#" + sortColumnId[i]).live("click", function(){
							showSort(sortColumnValue[this.id]);
							return false;
						});
					}
				}
				
			}
		});
	}
})