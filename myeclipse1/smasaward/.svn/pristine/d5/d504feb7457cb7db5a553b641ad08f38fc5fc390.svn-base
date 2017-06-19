/*
 * 处理页面跳转
*/
var to = function(url){//跳转
	window.location.href = basePath + url;
};

/*
 * 删除单个项目
*/
var deleteSingle = function(url) {// 删除单个项目
	if (confirm("您确定要删除此记录吗？")) {
		$.ajax({
			url: url,
			type: "post",
			data: "entityIds=" + entityId,
			dataType: "json",
			success: function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					to("project/general/toList.action?update=1&isReviewable=" + isReviewable + "&listType=" + listType + "&businessType=" + businessType);
					return false;
				} else {
					alert(result.errorInfo);
				}
			}
		});
	}
};

/*
* 初始化tab标签（头部基本信息和负责人信息）和（内容部分的业务标签）
*/
var initTabs = function(index){
	$("#tabs_top").tabs();
	$("#tabs_business").tabs({
		selected: index-1
	});
};


/*
 * 发送view.action请求，加载数据
*/
var viewGeneral = function(url) {// 加载查看数据
	var data = "";
	if (parent != null) {
		parent.loading_flag = true;
		setTimeout("parent.showLoading();", parent.loading_lag_time);
	}
	if(businessType == 1){
		data = "entityId=" + entityId + "&listType=" + listType + "&businessType=" + businessType;
	}else{
		data = "entityId=" + entityId + "&businessType=" + businessType;
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
 * 根据项目的不同类型进入查看项目详情
 * @param viewType 标识查看的类型，如view\pre\next。
*/
var viewDetails = function(viewType) {
	var viewUrl;//查看的具体url
	if(businessType == null || businessType=="" || businessType == undefined){
		businessType = 1;
	}
	//查看详情
	if(businessType == 1){
		if(listType == 1){//从参评，退评项目列表进入
			viewUrl = "project/general/";
		}else if(listType == 2){//从项目公示列表进入
			viewUrl = "project/general/publicity/";
		}else if(listType == 3){//从项目立项列表进入
			viewUrl = "project/general/granted/";
		}else if(listType == 4){//从项目查重列表进入
			viewUrl = "project/general/firstAudit/";
		}else if(listType == 5){//从专项任务列表进入
			viewUrl = "project/general/special/";
		}
	}else if(businessType == 2){//从项目立项列表进入
		viewUrl = "project/general/granted/";
	}else if(businessType == 3){//从项目中检列表进入
		viewUrl = "project/general/midinspection/";
	}else if(businessType == 4){//从项目变更列表进入
		viewUrl = "project/general/variation/";
	}else if(businessType == 5){//从项目结项列表进入
		viewUrl = "project/general/endinspection/";
	}
	viewGeneral(viewUrl + viewType + ".action");
}

/*
 * 回调函数，处理请求回来的数据
*/
var showDetails = function(result) {// 显示查看内容
	if (result.errorInfo == null || result.errorInfo == "") {
		entityId = result.project.id;//更新entityId
		$("#entityId").attr("value", result.project.id);
		$("#entityIds").attr("value", result.project.id);
		
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

var processResult = function(result){
	
	//处理项目基本信息模板及数据
	$("#view_basicInfo").hide();
	$("#view_basicInfo").html(TrimPath.processDOMTemplate("view_basicInfo_template", result));
	$("#view_basicInfo").show();
	
	/*//处理负责人信息模板及数据
	$("#view_directorInfo").hide();
	$("#view_directorInfo").html(TrimPath.processDOMTemplate("view_directorInfo_template", result));
	$("#view_directorInfo").show();*/
	
	//处理申报信息模板及数据
	$("#view_application").hide();
	$("#view_application").html(TrimPath.processDOMTemplate("view_application_template", result));
	$("#view_application").show();
	
	if (!(result.project.finalAuditResult == 2)) {//未立项
		$(".un_granted").each(function(){
			$(this).hide();
		});
		$("#view_granted").html("");
		$("#view_midinspection").html("");
		$("#view_midinspection").html("");
		$("#view_variation").html("");
	}else{
		//非管理员权限临时解决方案  @吕佳
		var userName = $("#userName",parent.document).val();
		if(userName == "admin"){
			//处理立项信息模板及数据
			$("#view_granted").hide();
			$("#view_granted").html(TrimPath.processDOMTemplate("view_granted_template", result));
			$("#view_granted").show();
			
			//处理中检信息模板及数据
			$("#view_midinspection").hide();
			$("#view_midinspection").html(TrimPath.processDOMTemplate("view_midinspection_template", result));
			$("#view_midinspection").show();
			
			//处理变更信息模板及数据
			$("#view_variation").hide();
			$("#view_variation").html(TrimPath.processDOMTemplate("view_variation_template", result));
			$("#view_variation").show();
		
			//处理结项信息模板及数据
			$("#view_endinspection").hide();
			$("#view_endinspection").html(TrimPath.processDOMTemplate("view_endinspection_template", result));
			$("#view_endinspection").show();
			
			/*
			 * 解决中检、变更、结项多次的排序序号问题
			*/
			$(".number").each(function(){
				if(!isNaN(parseInt($(this).html()))){
					$(this).html( parseInt($(this).attr("name")) - parseInt($(this).html()));
				}
			});
		}
	}
	
	$("#tabs_business").show();
};

/*
 * 页面初始化，包括初始化标签页，发送请求等
*/
var init = function(){
	
	//取出页面隐藏ID
	window.entityId = $("#entityId").val();
	window.listType = $("#listType").val();
	window.businessType = $("#businessType").val();
	window.isReviewable = $("#isReviewable").val();
	
	viewDetails("view");
	
	

	//调用函数，初始化标签
	initTabs(businessType);
	
	//添加
	$("#view_add").bind("click", function() {
		to("project/general/toAdd.action");
		return false;
	});
	
	//修改
	$("#view_mod").bind("click", function() {
		if(listType == 1){//一般项目
			to("project/general/toModify.action?entityId=" + entityId + "&listType=" + listType + "&businessType=" + $("#businessType").val());
		}
		return false;
	});
	
	//删除
	$("#view_del").bind("click", function() {
		if(listType == 1){//一般项目
			deleteSingle("project/general/delete.action?entityId=" + entityId + "&businessType=" + $("#businessType").val());
		}
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
		var url = "project/general/";
		if(businessType == 1){
			if(listType == 1){
				url += "";
			}
			if(listType == 2){
				url += "publicity/";
			}
			if(listType == 3){
				url += "granted/";
			}
			if(listType == 4){
				url += "firstAudit/";
			}
			if(listType == 5){
				url += "special/";
			}
		}else if(businessType == 2){
			url += "granted/";
		}else if(businessType == 3){
			url += "midinspection/";
		}else if(businessType == 4){
			url += "variation/";
		}else if(businessType == 5){
			url += "endinspection/";
		}
		url += "toList.action?entityId=" + entityId + "&update=" + $("#update").val();
		url += "&listType=" + listType + "&businessType=" + businessType;
		
		//如果是申报业务，则需要传递isReviewable参数
		if(businessType == 1){
			url += "&isReviewable=" + isReviewable;
		}
		
		to(url);
		return false;
	});
	
	
	/*
	 * 项目详情申报审核弹出层（成功后，回调函数执行重新发送view.action请求并灌入详情页面数据）
	*/
	$(".appAudit").live("click",function(){
		popGeneralAudit({
			title : "添加申报评审审核意见",
			src : "project/general/review/popAudit.action?entityId=" + $("#entityId").val(),
			inData: $("#entityId").val(),
			callBack : function(result){
				viewGeneral("project/general/view.action");
				//window.location.href = "expert/toList.action?update=1&expertType=0&isReviewer=1";//跳转到内部参评专家
			}
		});
	});
	
	/*
	 * 项目详情中检审核弹出层（成功后，回调函数执行重新发送view.action请求并灌入详情页面数据）
	*/
	$(".midAudit").live("click",function(){
		var self = this;
		popGeneralAudit({
			title : "添加中检审核意见",
			src : "project/general/midinspection/popAudit.action",
			inData: {"projectid":$(self).attr("projectid"),"midId":$(self).attr("midId")},
			callBack : function(result){
				viewGeneral("project/general/view.action");
				//window.location.href = "expert/toList.action?update=1&expertType=0&isReviewer=1";//跳转到内部参评专家
			}
		});
	});
	
	/*
	 * 项目详情中检添加录入弹出层（项目中检添加录入弹出层）
	*/
	$(".midAddResult").live("click",function(){
		var self = this;
		popGeneralAudit({
			title : "录入中检结果信息",
			src : "project/general/midinspection/toAddResult.action",
			inData: {"projectid":$(self).attr("graId")},
			callBack : function(result){
				viewGeneral("project/general/view.action");
				//window.location.href = "expert/toList.action?update=1&expertType=0&isReviewer=1";//跳转到内部参评专家
			}
		});
	});
	
	
	/*
	 * 项目详情变更审核弹出层（成功后，回调函数执行重新发送view.action请求并灌入详情页面数据）
	*/
	$(".varAudit").live("click",function(){
		var self = this;
		popGeneralAudit({
			title : "添加变更审核意见",
			src : "project/general/variation/popAudit.action?" + "varId=" + $(self).attr("varId"),
			inData: {"projectid":$(self).attr("appId"),"varId":$(self).attr("varId"),"varItems":$(self).attr("varItems")},
			callBack : function(result){
				viewGeneral("project/general/view.action");
				//window.location.href = "expert/toList.action?update=1&expertType=0&isReviewer=1";//跳转到内部参评专家
			}
		});
	});
	
	/*
	 * 项目详情结项教育部级审核弹出层（成功后，回调函数执行重新发送view.action请求并灌入详情页面数据）
	*/
	$(".endAudit").live("click",function(){
		var self = this;
		popGeneralAudit({
			title : "添加结项审核意见",
			src : "project/general/endinspection/popAudit.action?" + "endId=" + $(self).attr("endId") + "&projectid=" + $(self).attr("appId"),
			inData: {"projectid":$(self).attr("appId"),"endId":$(self).attr("endId"),"isAppExce":$(self).attr("isAppExce"),"isAppNoeval":$(self).attr("isAppNoeval")},
			callBack : function(result){
				viewGeneral("project/general/view.action");
				//window.location.href = "expert/toList.action?update=1&expertType=0&isReviewer=1";//跳转到内部参评专家
			}
		});
	});
	
	/*
	 * 项目详情结项最终审核结果弹出层（成功后，回调函数执行重新发送view.action请求并灌入详情页面数据）
	*/
	$(".endFinalAudit").live("click",function(){
		var self = this;
		popGeneralAudit({
			title : "添加最终结项审核意见",
			src : "project/general/endinspection/popAuditReview.action?" + "endId=" + $(self).attr("endId") + "&projectid=" + $(self).attr("appId"),
			inData: {"projectid":$(self).attr("appId"),"endId":$(self).attr("endId")},
			callBack : function(result){
				viewGeneral("project/general/view.action");
				//window.location.href = "expert/toList.action?update=1&expertType=0&isReviewer=1";//跳转到内部参评专家
			}
		});
	});	
	
	/*
	 * 项目详情结项添加录入
	*/
	$(".endAddFinalResult").live("click",function(){
		var self = this;
		popGeneralAudit({
			title : "录入结项结果信息",
			src : "project/general/endinspection/toAddResult.action?" + "&projectid=" + $(self).attr("appId"),//因为后台暂时未完成对应部分，暂时用中检跳转
			inData: {"projectid":$(self).attr("appId")},
			callBack : function(result){
				viewGeneral("project/general/view.action");
				//window.location.href = "expert/toList.action?update=1&expertType=0&isReviewer=1";//跳转到内部参评专家
			}
		});
	});
	
	/*
	 * 项目详情变更添加录入
	*/
	$(".varAddResult").live("click",function(){
		var self = this;
		popGeneralAudit({
			title : "录入变更结果信息",
			src : "project/general/variation/toAddResult.action?projectid=" + $(self).attr("appId"),
			inData: {"projectid":$(self).attr("appId")},
			callBack : function(result){
				viewGeneral("project/general/view.action");
				//window.location.href = "expert/toList.action?update=1&expertType=0&isReviewer=1";//跳转到内部参评专家
			}
		});
	});
	
	

};



/*
 * 页面加载后的入口
*/
$(document).ready(function() {
	init();
});