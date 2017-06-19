$(function(){
	var nameSpace = "statistic/review/univReviewer";
	
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3}
	});
	
	//查看学校
	$(".linkUniv").live("click", function(){
		var url = basePath + "statistic/review/expertReviewer/toList.action?update=1&universityCode=" + this.id;//这里entityId代表universityCode
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
		if (confirm('您确定要导出当前高校参评专家数量表？')){
			//导出的href。之前的地址如下：window.location.href = nameSpace + "/exportExpert.action" + "?exportAll=0&countReviewer=1";
			window.location.href = nameSpace + "/exportUniversityStatistic.action?" + "listType=" + listType;
		};
	});
});