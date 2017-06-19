/**
 * 文件说明：网站首页图片轮播、版块数据加载
 * 
 * 历史记录：
 * 2014-07-08	wangyi	创建文件。
 * 2014-07-14	xujw 增加对首页四个版块的初始化。
 */


/**
 * 方法说明：初始化图片轮播
 * @param none
 * @return none
 * @author wangyi
 */
var initSlide = function () {
	$("#myCarousel").prepend(template('slideInfo', json_slide));
	$(".carousel-indicators").children().eq(0).addClass("active");
	$(".carousel-inner").children().eq(0).addClass("active");
	$('.carousel').carousel({
		interval: 5000 // in milliseconds
	});
};

/**
 * 方法说明：初始化页面四个板块
 * @param obj(各板块数据对象)、length(每条新闻保留长度)、blockName(板块名称)
 * @return genHtml(返回的Html代码段)
 * @author xujw
 */
var initBlock = function (obj,length,blockName) {
	var genHtml = "<ul class='list-unstyled'>";
	if(obj,length,blockName) {
		$.each(obj,function (i) {
			genHtml += "<li><a href='" + "article/viewPage?id=" + obj[i].id + "&type=" + blockName + "' title='" 
					+ obj[i].title + "'>" + (obj[i].title.length > length ? (obj[i].title.substr(0,length)
					+ "..."):(obj[i].title)) + (obj[i].label ? "&nbsp;<span class='label label-warning label-twinkling'>新 !</span>" : "") + "</li>";
		});
	}
	
	genHtml += "</ul>";
	return genHtml;
};

$(function () {
	initSlide();
	$(".myNews").html(initBlock(json_news,22,"yyxw"));
	$(".myNotice").html(initBlock(json_notice,22,"tzgg"));
	$(".myStatus").html(initBlock(json_status,22,"skdt"));
	$(".myRules").html(initBlock(json_rules,22,"zcfg"));
});