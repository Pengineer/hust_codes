/**
 * 加载查看数据
 * @param {Object} url
 */
var view = function(url) {
//	if (parent != null) {
//		parent.loading_flag = true;
//		setTimeout("parent.showLoading();", parent.loading_lag_time);
//	}
	$.ajax({
		url: url,
		type: "post",
		data: null,
		dataType: "json",
		success: showDetails
	});
};

/**
 * 初始化全局设置
 */
var initGlobalVariables = function(){
	$(".clear").each(function(){
		$(this).attr('value', $.cookie($(this).attr('id')));
	})
}

/**
 * 将初始化的值存入cookie（在开始核算和清空核算时调用）
 */
var saveGolbalVariables = function(){
	$(".clear").each(function(){
		$.cookie($(this).attr('id'), $(this).val(), {expires: 999});
	})
}
/**
 * 提交全局设置前先校验拟立项条件
 */
var checkSettings = function(){
	var flag = true;
	if(checkNull("planRate")){
		alert("一般规划基金项目为空！");
		flag = false;
		return;
	} else if(checkNull("youthRate")){
		alert("一般青年基金项目为空！");
		flag = false;
		return;
	} else if(checkNull("xbPlanCount")){
		alert("西部规划基金项目为空！");
		flag = false;
		return;
	} else if(checkNull("xbYouthCount")){
		alert("西部青年基金项目为空！");
		flag = false;
		return;
	} else if(checkNull("xjPlanCount")){
		alert("新疆规划基金项目为空！");
		flag = false;
		return;
	} else if(checkNull("xjYouthCount")){
		alert("新疆青年基金项目为空！");
		flag = false;
		return;
	} else if(checkNull("xzPlanCount")){
		alert("西藏规划基金项目为空！");
		flag = false;
		return;
	} else if(checkNull("xzYouthCount")){
		alert("西藏青年基金项目为空！");
		flag = false;
		return;
	} else if(checkNull("zcCount")){
		alert("一般自筹项目为空！");
		flag = false;
		return;
	}
	return flag;
}

/**
 * 检查id为id的input是否为空
 * @param {Object} id
 * @return {TypeName} 
 */
var checkNull = function(id){
	if($("#" + id).val() == null || $("#" + id).val() == ""){
		return true;
	} else {
		return false;
	}
}

var clear = function(){
	$(".clear").each(function(){
		$(this).attr('value', '0');
	});
	$("#planFee").attr('value', 9);
	$("#youthFee").attr('value', 7);
}
/**
 * 全局设置 checkByRate
 * 根据青年/规划项目理想比例等8个参数，对项目进行立项核算，返回拟立项全局设置结果，进行显示。
 * @param	url
 * @param	flag 0: 清零 1: 全局设置
 */
var checkByRate = function(url, flag){
	if(flag == 1){
		$.ajax({
			url: url,
			type: "post",
			data: "youthRate=" + $("#youthRate").val() + "&planRate=" + $("#planRate").val() + 
				"&planFee=" + $("#planFee").val() + "&youthFee=" + $("#youthFee").val() + 
				"&xbPlanCount=" + $("#xbPlanCount").val() + "&xbYouthCount=" + $("#xbYouthCount").val() + 
				"&xjPlanCount=" + $("#xjPlanCount").val() + "&xjYouthCount=" + $("#xjYouthCount").val() + 
				"&xzPlanCount=" + $("#xzPlanCount").val() + "&xzYouthCount=" + $("#xzYouthCount").val() + "&zcCount=" + $("#zcCount").val(),
			dataType: "json",
			success: showDetails
		});
	} else if(flag == 0){
		$.ajax({
			url: url,
			type: "post",
			data: "youthRate=0&planRate=0&xbPlanCount=0&xbYouthCount=0&xjPlanCount=0&xjYouthCount=0&xzPlanCount=0&xzYouthCount=0&zcCount=0&planFee=0&youthFee=0",
			dataType: "json",
			success: showDetails
		});
	}
	
}

/**
 * 保存评审结果
 * @param {Object} url
 */
var finish = function(url){
	$.ajax({
		url: url,
		type: "post",
		dataType: "json",
		success: checkResult
	});
} 

/**
 * 弹出层微调项目
 * @param {Object} disType
 * @param {Object} arr = grantedXY X: 0-规划基金项目, 1-青年基金项目; Y: 0-一般项目, 1-西部项目, 2-新疆项目, 3-西藏项目
 */
var granted = function(disType, arr){
	if(arr == "granted00"){
		area = "";
		proType = "规划基金项目";
	} else if(arr == "granted01"){
		area = "西部";
		proType = "规划基金项目";
	} else if(arr == "granted02"){
		area = "新疆维吾尔自治区";
		proType = "规划基金项目";
	} else if(arr == "granted03"){
		area = "西藏自治区";
		proType = "规划基金项目";
	} else if(arr == "granted10"){
		area = "";
		proType = "青年基金项目";
	} else if(arr == "granted11"){
		area = "西部";
		proType = "青年基金项目";
	} else if(arr == "granted12"){
		area = "新疆维吾尔自治区";
		proType = "青年基金项目";
	} else if(arr == "granted13"){
		area = "西藏自治区";
		proType = "青年基金项目";
	}
	var disType = encodeURI(encodeURI(disType));//学科门类
	var area = encodeURI(encodeURI(area));//区域
	var proType = encodeURI(encodeURI(proType));//项目类型
	popProject({
		title : "选择拟立项项目",
		src : "finance/initCheckById.action?disType=" + disType + "&area=" + area + "&proType=" + proType,
		//src : "selfspace/viewInfo.action",
		callBack : function(result){
			showDetails(result);
		}
	});
	return false;
}
/**
 * 显示查看内容
 */
var showDetails = function(result) {
	if (result.errorInfo == null || result.errorInfo == "") {
		Template_tool.populate(result);
	} else {
		alert(result.errorInfo);
	}
//	if (parent != null) {
//		parent.loading_flag = false;
//		parent.hideLoading();
//	}
}
/**
 * 保存结果返回值
 * @param {Object} result
 */
var checkResult = function(result){
	if (result.errorInfo == null || result.errorInfo == "") {
		alert("保存成功！");
	} else {
		alert(result.errorInfo);
	}
}

var init = function() {
	view("finance/init.action");
	
	// 初始化全局设置
	initGlobalVariables();
	
	// 将全局设置的变量存入cookie
	$(".clear").live('change', function(){
		$.cookie($(this).attr('id'), $(this).val(), {expires: 999});
	})
	
	// 显示全局设置的结果
	$("#submit").live('click', function(){
		saveGolbalVariables();
		if(checkSettings()){
			checkByRate("finance/checkByRate.action", 1);
		}
		toggle_view('global_check');
	});
	
	$("#clear").live('click', function(){
		clear();
		saveGolbalVariables();
		checkByRate("finance/checkByRate.action", 0);
	});
	
	// 保存评审结果
	$("#finish").live('click', function(){
		finish("finance/finish.action");
	})
	
	// 弹出层查看项目，初始化列表
	$(":input[class^='granted']").live('click', function(){
		var disType = $(this).parent().parent().children().eq(0).html();// 学科门类
		granted(disType, $(this).prop('class'));
	});
	
	Template_tool.init();
	$("#tabs").tabs();
};

$(document).ready(function(){
	init();
});