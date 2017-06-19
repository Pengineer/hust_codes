/**
 * 设置参评或退评
 * @param {Object} ob	复选框ID
 * @param {Object} ff	form表单id或名称
 * @param {Object} flag	评审状态 0：退评；1：参评
 * @return {TypeName} 
 */
function setReview(ob, // 复选框ID
			ff, // form表单id或名称
			flag) // 评审状态
{
	var idsList1 = new Array();
	var $obj = $(":input[name='entityIds']");
	for(var i = 0; i < $obj.size(); i++){
		if($obj.eq(i).prop("checked")){
			idsList1.push($obj.eq(i).val());
		}
	}
	var entityIds = "";
	for(var i in idsList1){
		entityIds += ((i == 0) ? "" : "&") + "entityIds[" + i + "]=" + idsList1[i];
	}
	
	// 判断是否有专家被选中
	var cnt = count(ob);
	if (cnt == 0) {
		if (flag == 0) {
			alert("请选择要退出评审的专家！");
		} else if (flag == 1) {
			alert("请选择要参加评审的专家！");
		} else {
			alert("无效的专家参评状态！");
		}
		return false;
	} else {
		if (flag == 0){
			popSetReview({
				title : "填写退评原因",
				src : "expert/toDisableReview.action?" + entityIds,
				callBack : function(result){
					window.location.href = "expert/toList.action?update=1&expertType=" + $("input[name='expertType']").val() + "&isReviewer=1";
				}
			});
		} else if (flag == 1) {
			if (confirm("您确定要让选中的专家参加评审吗？")) {
				document.forms[ff].action = "expert/enableReview.action";
				document.forms[ff].submit();
				return true;
			}
		} else {
			alert("无效的专家参评状态！");
			return false;
		}
	}
}


$(function(){
	var nameSpace = "expert";
	
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4}
	});
	
	$(".linkView").live("click", function(){
		var expertType = $("#expertType").val();
		var isReviewer = $("#isReviewer").val();
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		url += "&expertType=" + expertType;
		url += "&isReviewer=" + isReviewer;
		window.location.href = url;
		return false;
	});
	
	
	/*
	//导出专家数据
	$("#exportExpert").click(function(){
		
		popExportExpert({
		
		});
		if (confirm('您确定要导出当前专家数据吗？')){
			window.location.href = nameSpace + "/exportExpert.action" + "?exportAll=0&countReviewer=1";
		}
		return false;
	});
	*/
	
	
	$.ajax({
		url : "expert/fetchAutoData.action",
		dataType : "json",
		method : "post",
		success : function(result){
			$("#keyword").autocomplete(result.autoData);
		}
	});
	
	$(".toggleKey").live('click', function(){
		var that = this;
		toggleKey(that);
		return false;
	});
	
	/*
	 * 专家的参评和退评
	 * */
	$("#review").live("click", function(){
		setReview('entityIds', 'list', 1); //参评
	});
	$("#unReview").live("click", function(){
		setReview('entityIds', 'list', 0); //退评
	});
});