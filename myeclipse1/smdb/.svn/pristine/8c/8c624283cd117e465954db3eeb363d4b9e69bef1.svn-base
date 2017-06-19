define(function(require,exports,module){
	require("jquery");
	require("template");
	require("pop-self");
	require("common");
	var list = require("javascript/list");
	var nameSpace = "funding/fundingList";
	var flag = true;
	var inData = top.PopLayer.instances[1].inData;
	$("#fundingBatchName").html(inData.fundingBatchName);
	$("#agency").html(inData.agencyName);
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
/*		var showDate = function(data){
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
							src: "funding/toList.action?fundingListId=" + $(this).attr("id")+"&update=1",
							inData:{
								fundingListId: $(this).attr("id")
							}
					}
					popShowFund(args);
					return false;
				});
			});
		}
		*/
		
		exports.init = function(){
			$("#search").attr({
				"action":"funding/fundingList/list.action?agencyId=" + inData.agencyId
			});
			list.pageShow({
				"nameSpace":nameSpace,
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3},
				"dealWith":function(data){
					if(flag){
						$("#totalFee").html(data.totalFee + " 万元");
						$("#fundingBatchDate").html(data.fundingBatchDate.substring(0,10));
						flag = false;
					} 
					$(".showdetail").each(function(){
						$(this).click(function(){
							var args = {
									src: "funding/funding/toList.action?fundingListId=" + $(this).attr("id")+"&update=1" + "&agencyId=" + inData.agencyId,
									inData:{
										fundingListId: $(this).attr("id"),
										agencyId: inData.agencyId,
										fundingBatchId:inData.fundingBatchId
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
						var url = nameSpace + "/simpleSearch.action?" + "agencyId=" + inData.agencyId;
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
						$("#search").attr("action", nameSpace + "/sort.action?agencyId=" + inData.agencyId);
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
});