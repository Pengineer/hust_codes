/*
 * 处理页面跳转
*/
var to = function(url){//跳转
	window.location.href = basePath + url;
};

/*
 * 页面加载后的入口
*/
$(document).ready(function() {
	init();
});

function init() {
	//取出页面隐藏ID
	window.entityId = $("#entityId").val();
	window.listType = $("#listType").val();
	window.isReviewable = $("#isReviewable").val();
	
	viewDetails("view");
	
	//调用函数，初始化标签
	initTabs();
	
	//修改
	/*$("#view_mod").live("click", function() {
		return false;
	});*/
	
	//删除
	$("#view_del").live("click", function() {
		deleteSingle("award/moeSocial/delete.action?entityId=" + entityId);
		return false;
	});
	
	//上一条
	$("#view_prev").live("click", function() {
		viewDetails("prev");
		return false;
	});
	
	//下一条
	$("#view_next").bind("click", function() {
		viewDetails("next");
		return false;
	});
	
	//返回列表
	$("#view_back").bind("click", function() {
		var url = "award/moeSocial/";
		url += "toList.action?entityId=" + entityId + "&update=" + $("#update").val() + "&listType=" + listType + "&isReviewable=" + $("#isReviewable").val();
		to(url);
		return false;
	});
	
};

/*
 * 根据奖励类型不同进入不同详情
 * @param viewType 标识查看的类型
 * 这个函数为了以后扩展才加的，请参考一般项目
 * */
function viewDetails(viewType){
	var viewUrl = "award/moeSocial/";//查看的具体url
	viewAward(viewUrl + viewType + ".action");
};

/*
 * 发送view.action请求，加载数据
*/

function viewAward(url) {
	var data = "entityId=" + entityId + "&listType=" + listType;
	if (parent != null) {
		parent.loading_flag = true;
		setTimeout("parent.showLoading();", parent.loading_lag_time);
	}
	$.ajax({
		url: url,
		type: "post",
		data: data,
		dataType: "json",
		success: showDetails
	});
};

/*
 * 回调函数，处理请求回来的数据
*/
function showDetails(result) {
	if (result.errorInfo == null || result.errorInfo == "") {
		entityId = result.awardApplication.id;//更新entityId
		$("#entityId").attr("value", result.awardApplication.id);
		$("#entityIds").attr("value", result.awardApplication.id);
		
		//调用Trimpath处理函数，灌入数据
		processResult(result);
		
	} else {
		alert(result.errorInfo);
	}
	if (parent != null) {
		parent.loading_flag = false;
		parent.hideLoading();
	}
};

/*
 * 调用Trimpath，给相应模板灌入数据
*/
function processResult(result) {
	//处理项目基本信息模板及数据
	$("#view_basicInfo").hide();
	$("#view_basicInfo").html(TrimPath.processDOMTemplate("view_basicInfo_template", result));
	$("#view_basicInfo").show();
	
	//处理奖励专家信息模板及数据
	$("#view_application").hide();
	$("#view_application").html(TrimPath.processDOMTemplate("view_application_template", result));
	$("#view_application").show();
	
	$("#tabs_award").show();
};

/*
* 初始化tab标签（头部基本信息和负责人信息）和（内容部分的业务标签）
*/
function initTabs() {
	$("#tabs_top").tabs();
	$("#tabs_award").tabs();
};

/*
 * 删除单个项目
*/
function deleteSingle(url) {
	if(confirm("您确定要删除此记录吗？")){
		$.ajax({
			url: url,
			type: "post",
			data: "entityIds=" + entityId,
			dataType: "json",
			success: function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					to("award/moeSocial/toList.action?update=1&isReviewable=" + isReviewable + "&listType=" + listType);
					return false;
				} else {
					alert(result.errorInfo);
				}
			}
		});
	}
};
