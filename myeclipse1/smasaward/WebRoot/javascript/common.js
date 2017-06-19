// ========================================================================
// 文件名：common.js
//
// 文件说明：
//     本文件主要实现诸如全选、删除提示等功能
// 
// 历史记录：
// 2010-01-28 龚凡  创建文件。
// 2010-03-04 周中坚  添加函数。
// ========================================================================

// ==============================================================
// 方法名: upload
// 方法描述: 本方法主要实现确认Excel的上传
// 返回值：
// false: 不提交请求
// ==============================================================
$(document).ready(function(){
	$(".image").live("click",function(){
		openOrNotImage($(this));
	})
	//判断用户选择年份是否为默认年份
	$(".isCurYear").live("click", function(event){
		var preYear=$(window.parent.document).find("#year[name=year]").val();
		var curYear=$(window.parent.document).find("#year>option[selected=selected]").val();
		if(preYear!=curYear){
			alert("不允许对当前年度项目做此操作!");
			event.stopImmediatePropagation(); //阻止后面的事件继续发生
			return false; //阻止a标签执行href
		}
	})
})

function openOrNotImage(obj){
	var src = obj.attr('src');
	var index1 = src.indexOf("image/close.gif");
	if(index1 != '-1'){
		obj.hide();
		obj.prev().show();
		obj.parent().parent().next().hide();
	}else{
		obj.hide();
		obj.next().show();
		obj.parent().parent().next().show();
	
	}	
}

function image(imageClass){
	var length = $("img[class="+imageClass+"][src*='image/open.gif']").length;
	var imageStatus="";
	if (length > 0) {
		$("img[class="+imageClass+"][src*='image/open.gif']").each(function(index){
			if($(this).css("display")=="none"){
				imageStatus += "hide;"
			}else{
				imageStatus += "show;"
			}
		});
	var key =imageClass+"_"+$(".link_bar").eq(0).attr("id");
	$.cookie(key, imageStatus);
	}
}

function initImage(imageClass,key){
	if ($.cookie && $.cookie(imageClass + "_" + key) != null) {
		var imageStatus = $.cookie(imageClass + "_" + key).split(";");
		if (imageClass != "varimage") {
			$("img[class="+imageClass+"][src*='image/open.gif']").each(function(index){
				if (imageStatus[index] == "show") {
					$(this).show();
					$(this).next().hide();
					$(this).parent().parent().next().hide();
				}
			})
		}else{//变更页面（特殊情况）
			$("img[class="+imageClass+"][src*='image/open.gif']").each(function(index){
				if (imageStatus[index] == "show") {
					$(this).show();
					$(this).next().hide();
					$(this).parent().parent().next().hide();
					$(this).parent().parent().parent().next().hide();
					$(this).parent().parent().parent().next().next().hide();
					$(this).parent().parent().parent().next().next().next().hide();
				}
			})
		}
	}
}
function restrictTrHeight(cssClass, aName){
	var aTags = document.getElementsByName(aName); // a标签元素个数
	var trHeight = getStyleValue(cssClass, 'height'); // css类中行高，以px为单位
	trHeight = trHeight.substr(0, trHeight.length - 2); // 获取行高的数字
	for (var i = 0; i < aTags.length; i++) {
		aTags[i].innerHTML = aTags[i].title;// 初始化显示内容
		var len = aTags[i].title.length;
		var textHeight = aTags[i].parentNode.offsetHeight; // 文本显示的实际高度
		while (textHeight > trHeight) { // 检测文本显示的实际高度是否大于css类中行高
			len = len - 1;
			aTags[i].innerHTML = aTags[i].title.substr(0, len) + '...';
			textHeight = aTags[i].parentNode.offsetHeight;
		}
	}
}
function getStyleValue(cssClass, param){
	var value;
	for (var j = 0; j < document.styleSheets.length; j++) {
		var tar = document.styleSheets[j];
		var rss = tar.cssRules ? tar.cssRules : tar.rules;
		for (i = 0; i < rss.length; i++) {
			if (rss[i].selectorText.toLowerCase().indexOf(cssClass.toLowerCase()) >= 0) {
				value = rss[i].style[param];
				if (value)
					return value;
			}
		}
	}
	return value;
}
// ==============================================================
// 函数名：addLoadEvent
// 函数描述：定义某些操作完成js事件的重新加载
// 返回值：无
// by Simon Willison 
// ==============================================================
function addLoadEvent(func) {
var oldonload = window.onload;
if (typeof window.onload != 'function') {
	window.onload = func;
} else {
	window.onload = function() {
	if (oldonload) {
		oldonload();
	}
	func();
	}
}
}

// ==============================================================
// 方法名: checkAll
// 方法描述: 本方法主要实现列表复选框的全选与不选
// 返回值：无
// ==============================================================
function checkAll(e, // 复选框选中状态
		ob) // 复选框名称
{
	// 获取复选框对象数组
	var sel_box = document.getElementsByName(ob);
	// 设置复选框状态
	for ( var i = 0; i < sel_box.length; i += 1) {
		sel_box[i].checked = e;
	}
}

// ==============================================================
// 方法名: count
// 方法描述: 本方法主要实现列表复选框选中个数的统计
// 返回值：
// cnt: 复选框被选中的个数
// ==============================================================
function count(ob) // 复选框名称
{
	// 获取复选框对象数组
	var sel_box = document.getElementsByName(ob);
	// 选中个数
	var cnt = 0;
	// 统计
	for ( var i = 0; i < sel_box.length; i += 1) {
		if (sel_box[i].checked) {
			cnt += 1;
		}
	}
	return cnt;
}

// ==============================================================
// 方法名: delSelected
// 方法描述: 本方法主要实现列表的群删功能
// 返回值：
// false: 不提交请求
// ==============================================================
function delSelected(ob, // 复选框名称
		ff) // 表单form的名称
{
	// 先判断是否有复选框被选中
	var cnt = count(ob);
	if (cnt == 0) {
		alert("请选择要删除的项！");
		return false;
	} else {
		if (confirm("您确定要删除选中项吗？")) {
			document.forms[ff].submit();
		}
	}
}

// ==============================================================
// 方法名: delRow
// 方法描述: 本方法主要实现删除列表最后一行
// 返回值：无
// ==============================================================
function delRow(ob) //列表的ID
{
	// 获取table dom
	var idTB = document.getElementById(ob);
	// 获取当前表格行数
	var i = idTB.rows.length;
	if (i <= 1) {
		alert("无可删除项！");
	}
	else {
		idTB.deleteRow(i - 1);
	}
}

// ==============================================================
// 方法名: addNum
// 方法描述: 本方法主要实现列表序号的重写
// 返回值：无
// ==============================================================
function addNum(ob) // 列表的ID
{
	// 获取列表对象
	var idTB = document.getElementById(ob);
	// 开始编号
	for ( var i = 1; i < idTB.rows.length; i+=1) {
		idTB.rows[i].cells[0].innerHTML = i.toString();
	}
}

// ==============================================================
// 方法名: validFCK
// 方法描述: 本方法主要实现FCk输入框的非空和字数验证
// 返回值：
// false: 不提交请求
// ==============================================================
function ValidFCK(EditorName) {
	var cont = FCKeditorAPI.GetInstance(EditorName);
	var str = cont.GetXHTML();
	var trimcont = str.replace(/<[^>]*>|<\/[^>]*>/gm, "");
	//将FCK编辑器中的html元素trim再计算长度
	if (trimcont === "") {
		alert("此处不得为空 ！");
		return false;
	} else if (trimcont.length > 10000) {
		alert("请勿超过10000个字符 ！");
		return false;
	}
}

// ==============================================================
// 方法名: toggle
// 方法描述: 本方法主要实现点一下出现，再点一下隐藏的效果
// 返回值：无
// ==============================================================
function toggle(Id) {
	var Div = document.getElementById(Id);
	if (Div) {
		var display = Div.style.display;
		if (display == 'none') {
			Div.style.display = 'block';
		} else if (display == 'block') {
			Div.style.display = 'none';
		}
	}
}

/**
 * 方法名: 获得浏览器版本<br />
 * 方法描述: 获得浏览器类型及版本<br />
 * 返回值：浏览器名及其版本
 */
function getBrowserVersion(){
	var browser = {};
	var userAgent = navigator.userAgent.toLowerCase();
	var s;
	(s = userAgent.match(/msie ([\d.]+)/)) ? browser.ie = s[1] : (s = userAgent.match(/firefox\/([\d.]+)/)) ? browser.firefox = s[1] : (s = userAgent.match(/chrome\/([\d.]+)/)) ? browser.chrome = s[1] : (s = userAgent.match(/opera.([\d.]+)/)) ? browser.opera = s[1] : (s = userAgent.match(/version\/([\d.]+).*safari/)) ? browser.safari = s[1] : 0;
	var version = "";
	if (browser.ie) {
		version = "msie " + browser.ie;
	}
	else
		if (browser.firefox) {
			version = "firefox " + browser.firefox;
		}
		else
			if (browser.chrome) {
				version = "chrome " + browser.chrome;
			}
			else
				if (browser.opera) {
					version = "opera " + browser.opera;
				}
				else
					if (browser.safari) {
						version = "safari " + browser.safari;
					}
					else {
						version = "未知浏览器";
					}
	return version;
}

function displayLeftFrame() {
	var ob = parent.getElementByName("left");
	if (ob == null) {
		alert("无法获取left帧");
	} else {
		if (ob.style.display == "block") {
			ob.style.display = "none";
		} else {
			ob.style.display = "block";
		}
	}
}

/**
 * 用于转换时间格式
 * 例如：
 * alert(new Date().format("yyyy-MM-dd"));
 * alert(new Date("january 12 2008 11:12:30").format("yyyy-MM-dd hh:mm:ss"));
 * @param {Object} format
 */
Date.prototype.format = function(format){
	var weekList = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
	var o = {
		"M+": this.getMonth() + 1, //month
		"d+": this.getDate(), //day
		"h+": this.getHours(), //hour
		"m+": this.getMinutes(), //minute
		"s+": this.getSeconds(), //second
		"q+": Math.floor((this.getMonth() + 3) / 3), //quarter
		"w": weekList[this.getDay()], //weekday
		"S": this.getMilliseconds() //millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

//获取页面实际大小
function getPageSize() {
	var xScroll, yScroll;
	if (window.innerHeight && window.scrollMaxY) {
		xScroll = document.body.scrollWidth;
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight) {
		sScroll = document.body.scrollWidth;
		yScroll = document.body.scrollHeight;
	} else {
		xScroll = document.body.offsetWidth;
		yScroll = document.body.offsetHeight;
	}

	var windowWidth, windowHeight;
	if (self.innerHeight) {
		windowWidth = self.innerWidth;
		windowHeight = self.innerHeight;
	} else if (document.documentElement
			&& document.documentElement.clientHeight) {
		windowWidth = document.documentElement.clientWidth;
		windowHeight = document.documentElement.clientHeight;
	} else if (document.body) {
		windowWidth = document.body.clientWidth;
		windowHeight = document.body.clientHeight;
	}

	var pageWidth, pageHeight
	if (yScroll < windowHeight) {
		pageHeight = windowHeight;
	} else {
		pageHeight = yScroll;
	}
	if (xScroll < windowWidth) {
		pageWidth = windowWidth;
	} else {
		pageWidth = xScroll;
	}
	arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight)
	return arrayPageSize;
}

/**
 * 获取滚动条的高度
 * @return {TypeName} 
 */
function getPageScroll(){
	var yScroll;
	if (parent.pageYOffset) {
		yScroll = parent.pageYOffset;
	} else if (parent.document.documentElement && parent.document.documentElement.scrollTop) {
		yScroll = parent.document.documentElement.scrollTop;
	} else if (parent.document.body) {
		yScroll = document.body.scrollTop;
	}
	var arrayPageScroll = new Array('', yScroll);
	return arrayPageScroll;
}

/**
 * 设置列表奇偶行效果
 * 列表表头需以thead包含
 * 列表内容需以tbody包含
 * @param {Object} id 列表所在容器ID
 * @param {Object} type 区分是页面还是层
 */
function setOddEvenLine(id, type) {
	var odd_css;
	var even_css;
	var hover_css;
	// 主要是样式的区别，常规列表行高要大一些
	if (type == 0) {// 常规列表
		even_css = "table_txt_tr1";
		odd_css = "table_txt_tr2";
		hover_css = "trbg11";
	} else {// 其它列表
		even_css = "table_txt_tr3";
		odd_css = "table_txt_tr4";
		hover_css = "trbg22";
	}
	$("#" + id).find("tbody").eq(0).find("tr:odd").each(function() {
		$(this).attr("class", odd_css);
		$(this).mouseover(function() {
			$(this).attr("class", hover_css);
		});
		$(this).mouseout(function() {
			$(this).attr("class", odd_css);
		});
	});
	$("#" + id).find("tbody").eq(0).find("tr:even").each(function() {
		$(this).attr("class", even_css);
		$(this).mouseover(function() {
			$(this).attr("class", hover_css);
		});
		$(this).mouseout(function() {
			$(this).attr("class", even_css);
		});
	});
};

/**
 * 设置列表无记录跨列效果
 * 列表表头需以thead包含
 * 列表内容需以tbody包含
 * @param {Object} id 需要进行跨列设置的tableID
 */
var setColspan = function(id) {
	var tb = document.getElementById(id);
	var tbclen = tb.rows.item(0).cells.length;
	var tbrlen = tb.rows.length;
	tb.rows.item(tbrlen - 1).cells[0].colSpan = tbclen;
};

var haveRead = [];
/**
 * 初始化一组标签
 * @param {Object} defaultTab 若有此参数，则跳到li a[href=defaultTab]的那个标签，否则跳到最后一次访问的标签
 * @param {Object} cookieMajor 为1则以cookie为主，没有或者为0则以defaultIndex为主
*/
function inittabs(){
	$("#tabs").tabs();
};

/**
 * 为字符串增加trim函数
 */
if(typeof String.prototype.trim !== 'function') {
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, '');
	}
}

/**
 * 下载文件
 * @param validateUrl 后台校验的url
 * @param successUrl 后台校验成功后下载文件的url
 */ 
function downloadFile(validateUrl, successUrl){
	$.ajax({
		url: validateUrl,
		type: "post",
		dataType: "json",
		success: function(result){
			if(result.errorInfo != null && result.errorInfo != ""){
				alert(result.errorInfo);
			}else{
				window.location.href = basePath + successUrl; 
			}
		}
	});
};

/**
 * 为了保证一定延时才出现加载图片，设置一个标志位
 * 执行showLoading且loading_flag=true时，才显示加载图片
 */
var loading_flag = false;
var loading_lag_time = 300;

/**
 * 计算加载图片居中位置
 */
function centerLoading() {
	return ($("#right").width() - $("#loading").width()) / 2 + "px";
}

/**
 * 显示加载图片
 */
function showLoading() {
	if (loading_flag) {
		var obj = $("#loading");
		obj.css("z-index", "100");
		obj.css("left", centerLoading());
		obj.css("top", "89px");
	}
}

/**
 * 隐藏加载图片
 */
function hideLoading() {
	$("#loading").css("z-index", "-1");
}

function toggle_view(nodeId) {
	var currentNode = document.getElementById(nodeId);
	var Div = currentNode.nextElementSibling || currentNode.nextSibling ;
	var lastChild =  currentNode.firstElementChild || currentNode.firstChild;
	if (Div) {
		if (lastChild.className == 'img_dis') {
			Div.style.display = 'none';
			lastChild.className = 'img_undis';
			
		} else if (lastChild.className == 'img_undis') {
			Div.style.display = 'block';
			lastChild.className = 'img_dis';
		}
	}
}

/**
 * 避免异步请求页面，加载图标还在时，载入新页面，出现加载图片无法清除的问题。
 * 页面载入时先执行一遍清除加载图片
 */
$(document).ready(function() {
	if (parent != null) {
		parent.hideLoading();
	}
});
	


