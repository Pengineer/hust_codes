var to = function(url){//跳转
	window.location.href = basePath + url;
};

var deleteSingle = function(url) {// 删除单个项目
	if (confirm("您确定要删除此记录吗？")) {
		$.ajax({
			url: url,
			type: "post",
			data: "entityIds=" + entityId,
			dataType: "json",
			success: function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					var url = "expert/toList.action?entityId=" + entityId + "&update=1";
					url += "&expertType=" + $("#expertType").val();
					url += "&isReviewer=" + $("#isReviewer").val();
					to(url);
					return false;
				} else {
					alert(result.errorInfo);
				}
			}
		});
	}
};

var viewExpert = function(url) {// 加载查看数据
	if (parent != null) {
		parent.loading_flag = true;
		setTimeout("parent.showLoading();", parent.loading_lag_time);
	}
	$.ajax({
		url: url,
		type: "post",
		data: "entityId=" + entityId + "&listType=" + listType,
		dataType: "json",
		success: showDetails
	});
};

var showDetails = function(result) {// 显示查看内容
	if (result.errorInfo == null || result.errorInfo == "") {
		entityId = result.expert.id;//更新entityId
		if(result.expert.importedDate != null){
			result.expert.importedDate = result.expert.importedDate.replace('T',' ');//修改更新时间格式			
		}
		//$("#updateTimeTd").innerHTML = result.expert.importedDate;
		$("#entityId").attr("value", result.expert.id);
		$("#entityIds").attr("value", result.expert.id);
		$("#levelOneDiscipline").attr("value", result.levelOneDiscipline);
		$("#view_container").hide();
		$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
		$("#view_container").show();
	} else {
		alert(result.errorInfo);
	}
	if (parent != null) {
		parent.loading_flag = false;
		parent.hideLoading();
	}
};

var init = function(){
	window.entityId = $("#entityId").val();
	window.listType = $("#listType").val();
	
	//查看
	viewExpert("expert/view.action");
	
	//添加
	$("#view_add").bind("click", function() {
		to("expert/toAdd.action");
		return false;
	});
	
	//修改
	$("#view_mod").bind("click", function() {
		to("expert/toModify.action?entityId=" + entityId);
		return false;
	});
	
	//删除
	$("#view_del").bind("click", function() {
		deleteSingle("expert/delete.action?entityId=" + entityId);
		return false;
	});
	
	//上一条
	$("#view_prev").live("click", function() {
		if(listType == 2){//统计查看专家列表
			viewExpert("statistic/review/expert/prev.action");
		}else{
			viewExpert("expert/prev.action");
		}
		return false;
	});
	
	//下一条
	$("#view_next").bind("click", function() {
		if(listType == 2){//统计查看专家列表
			viewExpert("statistic/review/expert/next.action");
		}else{
			viewExpert("expert/next.action");
		}
		return false;
	});
	
	//返回列表
	$("#view_back").bind("click", function() {
		if(listType == 1){//一般项目查看专家列表
			var url = "project/general/toList.action?entityId=" + $("#projectId").val() + "&update=1&listType=1&isReviewable=1&businessType=1";
			to(url);
		}else if(listType == 2){//统计查看专家列表
			var listTypeStatic = $("#listTypeStatic").val();
			var url = "statistic/review/expert/toList.action?entityId=" + entityId + "&update=" + $("#update").val() + "&listType=" + listTypeStatic;
			to(url);
		}else if(listType == 3){//基地项目查看专家列表
			var url = "project/instp/toList.action?entityId=" + $("#projectId").val() + "&update=1&listType=1&isReviewable=1&businessType=1";
			to(url);
		}else{
			var url = "expert/toList.action?entityId=" + entityId + "&update=" + $("#update").val();
			url += "&expertType=" + $("#expertType").val();
			url += "&isReviewer=" + $("#isReviewer").val();
			to(url);
		}
		return false;
	});
	
	/**
	 * 退评选中的一般项目
	 */
	$("#notReviewGeneral").live("click", function(){
		notReviewProjects(1, "generalIds")
	});
	
	/**
	 * 退评选中的基地项目
	 */
	$("#notReviewInstp").live("click", function(){
		notReviewProjects(3, "instpIds")
	});
	
	/**
	 * 一般项目批量替换专家
	 */
	$("#general_replace_expert").live("click", function(){
		var preYear=$(window.parent.document).find("#year[name=year]").val();
		var curYear=$(window.parent.document).find("#year>option[selected=selected]").val();
		if(preYear != curYear){
			alert("不允许对当年度项目做此操作");
		}else {
			replaceExperts(1, "generalIds");
		}
	});
	
	/**
	 * 基地项目批量替换专家
	 */
	$("#institute_replace_expert").live("click", function(){
		var preYear=$(window.parent.document).find("#year[name=year]").val();
		var curYear=$(window.parent.document).find("#year>option[selected=selected]").val();
		if(preYear != curYear){
			alert("不允许对当年度项目做此操作");
		}else {
			replaceExperts(3, "instpIds");
		}
	});
	
	/**
	 * 一般项目指定专家移交
	 */
	$("#general_transfer").live("click", function(){
		var preYear=$(window.parent.document).find("#year[name=year]").val();
		var curYear=$(window.parent.document).find("#year>option[selected=selected]").val();
		if(preYear != curYear){
			alert("不允许对当年度项目做此操作");
		}else {
			transferMatch(1, "generalIds");
		}
	});
	
	/**
	 * 基地项目指定专家移交
	 */
	$("#institute_transfer").live("click", function(){
		var preYear=$(window.parent.document).find("#year[name=year]").val();
		var curYear=$(window.parent.document).find("#year>option[selected=selected]").val();
		if(preYear != curYear){
			alert("不允许对当年度项目做此操作");
		}else {
			transferMatch(3, "instpIds");
		}
	});
	
	$(".toggleKey").live('click', function(){
		var that = this;
		toggleKey(that);
		return false;
	});
	
};

$(document).ready(function() {
	init();
});

/**
 * 退评选中的项目
 */
function notReviewProjects(listType, projectId) {
	var pojids = document.getElementsByName(projectId);//获取选中的项目ids
	var cnt = 0;
	var ids = '';
	for(var i = 0; i < pojids.length; i++) {
		if(pojids[i].checked) {
			cnt++;
			ids = ids + pojids[i].value + '; ';
		}
	}
	if(cnt == 0) {
		alert('请至少选择一个需要退评的项目！');
		return false;
	}
	if(cnt > 0 && ids.length > 1) {
		ids = ids.substr(0, ids.length - 1);
	}
	var postUrl = '';
	if(listType == 1){
		postUrl = "project/general/deleteExpertProjects.action";
	}else if(listType == 3){
		postUrl = "project/instp/deleteExpertProjects.action";
	}
	
	if(cnt > 0 && ids != '' && confirm('您确定针对此专家退评所选中的[' + cnt + ']个项目吗？')) {
		$.ajax({
			url: postUrl,
			type: "post",
			data : "expertId=" + $('#entityId').val() + "&projectId=" + ids,
			dataType: "json",
			success: function(result){
				if (result == null || result.errorInfo == null || result.errorInfo == "") {
					window.location.href = 'expert/toView.action?entityId=' + $('#entityId').val() + '&listType=' + listType;
				}else{
					alert(result.errorInfo);
				}
			}
		});
	}
}

/**
 * 批量替换专家
 * @param listType	项目类型：1一般项目；3基地项目
 * @param projectId	项目列表复选框
 * @returns {Boolean}
 */
function replaceExperts(listType, projectId){
	var pojids = document.getElementsByName(projectId);//获取选中的项目ids
	var cnt = 0;
	var ids = '';
	for(var i = 0; i < pojids.length; i++) {
		if(pojids[i].checked) {
			cnt++;
			ids = ids + pojids[i].value + '; ';
		}
	}
	if(cnt == 0) {
		alert('请选择需要替换专家的项目！');
		return false;
	}
	if(cnt > 0 && ids.length > 1) {//去掉末尾逗号
		ids = ids.substr(0, ids.length - 1);
	}
	
	if ($("#expertListFrame").length > 0){
		$("#expertListFrame").prev().remove();
		$("#expertListFrame").next().remove();
		$("#expertListFrame").remove();
	}
	
	if ($("#expertTreeFrame").length == 0) {
		var iframeContent = '';
		var postUrl = '';
		if(listType == 1){
			iframeContent = "<p style='margin-top:20px;margin-left:10px;'>请指定一个或多个替换专家，根据专家匹配规则，接手当前专家评审的项目。<br>若不指定替换专家，则所有参评专家参与匹配，并接手当前专家评审的项目。<br>对于替换专家无法接手的项目，仍保留由当前专家评审。</p>" +
				"<iframe id='expertTreeFrame' src='project/general/showExpertTree.action?discipline=" + $("#levelOneDiscipline").val() + "' scrolling='auto' frameborder='no' border='0' width='100%'></iframe>" +
				"<div style='margin-bottom:20px;text-align:center'><input type='button' id='startReplace' value='开始替换' class='btn' /><span id='waiting' style='display:none'>替换中，请稍后……</span></div>";
			postUrl = "project/general/batchReplaceExpert.action?projectId=";
		}else if(listType == 3){
			iframeContent = "<p style='margin-top:20px;margin-left:10px;'>请指定一个或多个替换专家，根据专家匹配规则，接手当前专家评审的项目。<br>若不指定替换专家，则所有参评专家参与匹配，并接手当前专家评审的项目。<br>对于替换专家无法接手的项目，仍保留由当前专家评审。</p>" +
				"<iframe id='expertTreeFrame' src='project/instp/showExpertTree.action?discipline=" + $("#levelOneDiscipline").val() + "' scrolling='auto' frameborder='no' border='0' width='100%'></iframe>" +
				"<div style='margin-bottom:20px;text-align:center'><input type='button' id='startReplace' value='开始替换' class='btn' /><span id='waiting' style='display:none'>替换中，请稍后……</span></div>";
			postUrl = "project/instp/batchReplaceExpert.action?projectId=";
		}
		
		$("body", this.document).append(iframeContent);
		
		$("#startReplace").click(function() {
			if(cnt > 0 && ids != '' && confirm('您确定针对所选中的[' + cnt + ']个项目替换专家吗？')) {
				var iframeObj = document.getElementById('expertTreeFrame').contentDocument;
				var selectedExpertIds = iframeObj.getElementById('selectedExpertIds').value;
				if(selectedExpertIds == null || selectedExpertIds == ''){
					if(!confirm('您未指定任何专家，所有参评专家均将参与匹配？')){
						return false;
					}
				}
				$(this).remove();
				$("#waiting").show();
				$.ajax({
					type : "POST",
					url : postUrl + ids,	//ids为选择的项目ids
					data : {selectExp: $("[name=entityId]").val(), selectedExpertIds: selectedExpertIds},//selectExp为当前专家id，selectedExpertIds为勾选的专家ids
					success : function(){
						location.reload();
					},
					error : function(){
						alert("匹配出错!");
						location.reload();
					}
				});
			}
		});
	}
//	$("#startReplace").get(0).scrollIntoView();
}

/**
 * 指定专家移交
 * @param listType	项目类型：1一般项目；3基地项目
 * @param projectId	项目列表复选框
 * @returns {Boolean}
 */
function transferMatch(listType, projectId){
	var pojids = document.getElementsByName(projectId);//获取选中的项目ids
	var cnt = 0;
	var ids = '';
	for(var i = 0; i < pojids.length; i++) {
		if(pojids[i].checked) {
			cnt++;
			ids = ids + pojids[i].value + '; ';
		}
	}
	if(cnt == 0) {
		alert('请选择需要移交的项目！');
		return false;
	}
	if(cnt > 0 && ids.length > 1) {//去掉末尾逗号
		ids = ids.substr(0, ids.length - 1);
	}
	if ($("#expertTreeFrame").length > 0){
		$("#expertTreeFrame").prev().remove();
		$("#expertTreeFrame").next().remove();
		$("#expertTreeFrame").remove();
	}
	
	if ($("#expertListFrame").length == 0) {
		var iframeContent = '';
		var postUrl = '';
		iframeContent = "<p style='margin-top:20px;margin-left:10px;'>请指定唯一的一个移交专家，无条件接手当前专家评审的所有项目。<br><span style='color:red;'>警告：移交过程将不考虑任何专家匹配规则，您需自行确认所指定的移交专家是否适于接手当前专家评审的所有项目。</span></p>" +
		"<iframe id='expertListFrame' src='expert/select/toList.action?update=1' scrolling='auto' frameborder='no' border='0' width='100%' height='525px'></iframe>" +
		"<div style='margin-bottom:20px;text-align:center'><input type='button' id='startTransfer' value='开始移交' class='btn' /><span id='waiting' style='display:none'>移交中，请稍后……</span></div>";
		if(listType == 1){
			postUrl = "project/general/transferMatch.action?projectId=";
		}else if(listType == 3){
			postUrl = "project/instp/transferMatch.action?projectId=";
		}
		
		$("body", this.document).append(iframeContent);
		
		$("#startTransfer").click(function() {
			if(cnt > 0 && ids != '' && confirm('您确定移交所选中的[' + cnt + ']个项目吗？')) {
				var iframeObj = document.getElementById('expertListFrame').contentDocument;
//				var selectedExpertId = iframeObj.getElementById('selectedExpertId').value;
				var checkedradio = $("input[type='radio'][name='newExpertIds']:checked", iframeObj);
				if(checkedradio.length == 0) {
				alert("请选择专家！"); return;
				} else if(checkedradio.length > 1) {
					alert("请选择单个专家！"); return;
				}
				var selectedExpertId = checkedradio.eq(0).val();
//				alert(postUrl);
				$(this).remove();
				$("#waiting").show();
				$.ajax({
					type : "POST",
					url : postUrl + ids,//ids勾选的项目ids
					data : {entityId: entityId, expertId: selectedExpertId},//entityId为当前专家id，selectedExpertId为指定的新专家id
					success : function(){
						location.reload();
					},
					error : function(){
						alert("移交出错!");
						location.reload();
					}
				});
			}
		});
	}
}
