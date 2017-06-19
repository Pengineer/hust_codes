//专家跨类信息统计
$(function(){
	var nameSpace = "statistic/review/expertReviewer";
	
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
	});
	
	//查看专家
	$(".linkExpert").live("click", function(){
		var url = basePath + "expert/toView.action?entityId=" + this.id + "&listType=2";	
		window.location.href = url;
		return false;
	});
	
	//查看学校
	$(".linkUniv").live("click", function(){
		var url = basePath + nameSpace + "/toList.action?update=1&universityCode=" + this.id;
		window.location.href = url;
		return false;
	});
	
	//列表类别：是否包含备评0不包含，1包含
	$("input[name='isContainP']").click(function(){
		window.location.href = basePath + nameSpace + "/toList.action?update=1&isContainP=" + $(this).val();
		return false;
	});
	
	//高校参评专家数量列表导出
	$("#export").live("click",function(){
		listType = $("#listType").val();
		var universityCode = $("#universityCode").val();
		if (confirm('您确定要导出当前专家参评项目数量表？')){
			//导出的href。之前的地址如下：window.location.href = nameSpace + "/exportExpert.action" + "?exportAll=0&countReviewer=1";
			window.location.href = nameSpace + "/exportExpertStatistic.action?" + "listType=" + $("#listType").val() + "&universityCode=" + universityCode;
		};
	});
});