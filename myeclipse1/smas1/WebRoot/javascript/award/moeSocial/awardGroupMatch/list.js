/*
 * 分组匹配列表实现
 * author：fengzheqi
 * */
$(function(){
	var nameSpace = "award/moeSocial/awardGroupMatch";
	
	//列表初始化
	pageShow({
		"nameSpace":nameSpace,
		"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
		"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
	});
})