/**
 * 点击图片展开或隐藏。
 * 在dom加载之后统一给$(".image")对象绑定点击事件。
 */
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

/**
 * 将class为imageClass的图片状态（显示或隐藏）保存至cookie，以便下一次载入时初始化。
 * @param imageClass
 */
function image(imageClass){
	var length = $("img[class="+imageClass+"][src*='image/open.gif']").length;
	var imageStatus="";
	if (length > 0) {
		$("img[class="+imageClass+"][src*='image/open.gif']").each(function(index){
			if($(this).css("display")=="none"){
				imageStatus += "hide";
			}else{
				imageStatus += "show";
			}
		});
	var key =imageClass+"_"+$(".link_bar").eq(0).attr("id");
	$.cookie(key, imageStatus);
	}
}

/**
 * 初始化显示或隐藏
 * @param imageClass
 * @param key
 */
function initImage(imageClass,key){
	if ($.cookie && $.cookie(imageClass + "_" + key) != null) {
		var imageStatus = $.cookie(imageClass + "_" + key).split(";");
		$("img[class="+imageClass+"][src*='image/open.gif']").each(function(index){
			if (imageStatus[index] == "show") {
				$(this).show();
				$(this).next().hide();
				$(this).parent().parent().next().hide();
			}
		});
	}
}

/**
 * 本方法主要实现列表复选框的全选与不选
 */
function checkAll(e, // 复选框选中状态
ob) // 复选框名称
{
	// 获取复选框对象数组
	var sel_box = document.getElementsByName(ob);
	// 设置复选框状态
	for (var i = 0; i < sel_box.length; i += 1) {
		sel_box[i].checked = e;
	}
}
/**
 * 判断是否为全选
 * @param {Object} isChecked	是否选中
 * @param {Object} objBoxName	多选框名称
 * @param {Object} objAllId		全选框id
 */
function checkAllOrNot(isChecked, objBoxName, objAllId){
	if (isChecked) {// 当有项被选中时，判断当前是否已全选了
		var checkbox_length = $("input[name=" + objBoxName + "]").length;
		var cnt = count(objBoxName);// 当前已选中的个数
		if (cnt == checkbox_length) {
			$("#" + objAllId).attr("checked", true);
		}else{
			$("#" + objAllId).attr("checked", false);
		}
	} else {// 当有项撤销选中时，判断头是否处于非全选状态
		$("#" + objAllId).attr("checked", false);
	}
}

/**
 * 实现列表复选框选中个数的统计
 */
function count(ob) // 复选框名称
{
	// 获取复选框对象数组
	var sel_box = document.getElementsByName(ob);
	// 选中个数
	var cnt = 0;
	// 统计
	for (var i = 0; i < sel_box.length; i += 1) {
		if (sel_box[i].checked) {
			cnt += 1;
		}
	}
	return cnt;
}

/**
 * validFCK FCk输入框的非空和字数验证
 * @param {Object} EditorName 输入框名称
 * @param {Object} message 为空时的错误信息提示，若为空则提示默认信息。
 * @return {TypeName} 
 */
function ValidFCK(EditorName, message){
	var cont = FCKeditorAPI.GetInstance(EditorName);
	var str = cont.GetXHTML();
	var trimcont = str.replace(/<[^>]*>|<\/[^>]*>/gm, "");// 去标签
	//将FCK编辑器中的html元素trim再计算长度
	if (trimcont === "") {
		if(message == null || message == "") {
			alert("此处不得为空 ！");
		} else {
			alert(message);
		}
		return false;
	} else if (trimcont.length > 200000) {
		alert("请勿超过10000个字符 ！");
		return false;
	}
}

/**
 * 获得浏览器类型及版本
 */
function getBrowserVersion(){
	var browser = {};
	var userAgent = navigator.userAgent.toLowerCase();
	var s;
	(s = userAgent.match(/msie ([\d.]+)/)) ? browser.ie = s[1] : (s = userAgent.match(/firefox\/([\d.]+)/)) ? browser.firefox = s[1] : (s = userAgent.match(/chrome\/([\d.]+)/)) ? browser.chrome = s[1] : (s = userAgent.match(/opera.([\d.]+)/)) ? browser.opera = s[1] : (s = userAgent.match(/version\/([\d.]+).*safari/)) ? browser.safari = s[1] : 0;
	var version = "";
	if (browser.ie) {
		version = "msie " + browser.ie;
	} else if (browser.firefox) {
		version = "firefox " + browser.firefox;
	} else if (browser.chrome) {
		version = "chrome " + browser.chrome;
	} else if (browser.opera) {
		version = "opera " + browser.opera;
	} else if (browser.safari) {
		version = "safari " + browser.safari;
	} else {
		version = "未知浏览器";
	}
	return version;
}

/**
 * 用于转换时间格式
 * 例如：
 * alert(new Date().format("yyyy-MM-dd"));
 * alert(new Date("january 12 2008 11:12:30").format("yyyy-MM-dd hh:mm:ss"));
 * @param {Object} format
 */
Date.prototype.format = function(format){
	var o = {
		"M+": this.getMonth() + 1, //month
		"d+": this.getDate(), //day
		"h+": this.getHours(), //hour
		"m+": this.getMinutes(), //minute
		"s+": this.getSeconds(), //second
		"q+": Math.floor((this.getMonth() + 3) / 3), //quarter
		"S": this.getMilliseconds() //millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

/**
 * 为字符串增加trim函数
 */
if(typeof String.prototype.trim !== 'function') {
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, '');
	};
}

/**
 * 数字变汉字
 * @param num
 * @return re
 */
function Num2Chinese(num) {
	if (!/^\d*(\.\d*)?$/.test(num)) {
		return false;
	}
	var AA = new Array
	("零", "一", "二", "三", "四", "五", "六", "七", "八", "九");
	var BB = new Array("", "十", "百", "千", "万", "亿", "点", "");
	var a = ("" + num).replace(/(^0*)/g, "").split("."),
	k = 0, re = "";
	for ( var i = a[0].length - 1; i >= 0; i--) {
		switch (k) {
		case 0:
			re = BB[7] + re;
			break;
		case 4:
			if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$").test(a[0]))
				re = BB[4] + re;
			break;
		case 8:
			re = BB[5] + re;
			BB[7] = BB[5];
			k = 0;
			break;
		}
		if (k % 4 == 2 && a[0].charAt(i) == "0" && a[0].charAt(i + 2) != "0")
			re = AA[0] + re;
		if (a[0].charAt(i) != 0)
			re = AA[a[0].charAt(i)]+ BB[k % 4] + re;
		k++;
	}
	if (a.length > 1) {
		re += BB[6];
		for ( var i = 0; i < a[1].length; i++)
			re += AA[a[1].charAt(i)];
	}
	return re;
}

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
	return ($("#main").width() - $("#loading").width()) / 2 + "px";
}

/**
 * 显示加载图片
 */
function showLoading() {
	if (loading_flag) {
		var obj = $("#loading");
		obj.css("z-index", "100");
		obj.css("left", centerLoading());
		obj.css("top", "56px");
	}
}

/**
 * 隐藏加载图片
 */
function hideLoading() {
	$("#loading").css("z-index", "-1");
}

/**
 * 根据标签和title获取元素数组
 * @param {Object} tag
 * @param {Object} title
 * @return {TypeName} 
 */
var getElementsByTagTitle = function(tag, title){
	returns = new Array();
	var e = document.getElementsByTagName(tag);
	for(var i = 0; i < e.length; i++){
		if(e[i].getAttribute("title") == title){
			returns[returns.length] = e[i];
		}
	}
	return returns;
}

/**
 * 导出数据
 */
function exportData(url, startTime, endTime) {
	if(!startTime || !endTime) {
		alert("起止时间不能为空！");
	}
	$.ajax({
		url: "validate/validateExport.action",
		type: "post",
		data: {"startTime" : startTime, "endTime" : endTime},
		dataType: "json",
		success: function(result){
			if (result.exportForbidden) {
				alert("早期数据已自动归档，暂能导出" + result.exportYear + "年1月1号至今的最新数据！");
				return false;
			} else if(result.exportNotValid) {
				if(confirm("早期数据已自动归档，暂能导出" + result.exportYear + "年1月1号至今的最新数据。是否继续？")) {
					url += "&startDate=" + result.exportStartTime + "&endDate=" + endTime; 
					location.href = url;
				}
			} else {
				url += "&startDate=" + startTime + "&endDate=" + endTime; 
				location.href = url;
			}
		}
	});
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
}

//$(document).ready(function() {
	// 避免异步请求页面，加载图标还在时，载入新页面，出现加载图片无法清除的问题。
	// 页面载入时先执行一遍清除加载图片
//	if (parent != null) {
//		parent.hideLoading();
//	}
//	
//	$(".image").live("click",function(){
//		openOrNotImage($(this));
//	});
	
//})

//所有高级检索及导出页面中通过时间控件选择日期的input框，默认文字为“不限”。
var init_hint = function(){
	
	$(".date_hint").live("click", function(){
		if (this.value == "--不限--"){
			this.value = "";
		}
	});
	$(".date_hint").live("blur", function(){
		if (this.value == ""){
			this.value = "--不限--";
		}
	});
	$(".date_hint").each(function(index){
	    var value = this.value;
	    if(null == value || value == ""){
	    	$(this).attr("value", "--不限--");   
	    }
	});
}

/**
 * 设置列表无记录跨列效果（列表及查看页面使用）
 * 列表表头需以thead包含
 * 列表内容需以tbody包含
 * @param {Object} id 需要进行跨列设置的tableID
 */
var setColspan = function(id) {
	var tb = document.getElementById(id);
	var tbclen = tb.rows.item(0).cells.length;
	var tbrlen = tb.rows.length;
	tb.rows.item(tbrlen - 1).cells[0].colSpan = tbclen;
}

/**
 * 设置列表奇偶行效果（列表及查看页面使用）
 * 列表表头需以thead包含
 * 列表内容需以tbody包含
 * @param {Object} id 列表所在容器ID
 * @param {Object} type 区分是页面还是层
 */
var setOddEvenLine = function(id, type) {
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
}

/**
 * 将列表序号从1开始显示
 * @memberOf {TypeName} 
 */
function showListNumber(){
	$(".index").each(function(){
		if(!isNaN(parseInt($(this).html()))){
			$(this).html( parseInt($(this).html())+ 1);
		}
	});
}

/**
 * 将序号改为从1开始（原本是从0开始）
 */
function showNumber(){
	$(".index").each(function(){
		if(!isNaN(parseInt($(this).html()))){
			$(this).html( parseInt($(this).html())+ 1);
		}
	});
}

/** 弹出层相关 **/

/**
 * 设置默认选中
 * 在进入页面或者翻页时调用
 */
defaultSelected = function() {
	thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
	
	var interval = setInterval(function(){
		if($("#list_container").css("display") == "block"){
			if($("input[type='radio']", $("#list_container")).length > 0){//单选
				defaultSelect(thisPopLayer.inData);
				clearInterval(interval);
			} else if($("input[type='checkbox']", $("#list_container")).length > 0){//复选
				defaultSelectGroup(thisPopLayer.inData);
				clearInterval(interval);
			}
		}
	}, 5);
}

/**
 * 	弹出层列表显示时，设置底部显示已选项的名称
 * 	将所选的机构或人员名称截取合适的长度显示在"已选择"后面
 * 	@param name已选项的名称
 */
function showTrimedName(name){
	var trimName = (name.length > 71) ? name.substring(0, 70) + "……" : name;
	if($("#trimedName").attr('name') == "radioPop"){
		$("#trimedName").empty().append(trimName).append('<a id="cancelSelectedRadio" href="javascript:void(0)" title="点击删除" ><img id="image" src="image/del.gif" /></a>');
	} else if ($("#trimedName").attr('name') == "checkboxPop"){
		$("#trimedName").empty().append(trimName).append('<a id="cancelSelectedCheckbox" href="javascript:void(0)" title="点击删除" ><img id="image" src="image/del.gif" /></a>');
	}
}

/**
 * 	弹出层多选时，设置底部显示已选项的名称
 * 	将所选的机构或人员名称截取合适的长度显示在"已选择"后面
 * 	@param name已选项的名称
 */
function showTrimedNameV2(names){
	$("#trimedName").empty();
	if(names.trim().length){
		var namesArray = names.split("; ");
		for(var i = 0 ; i < namesArray.length; i ++) {
			if(i == 0 ) var trimName = (namesArray[i].length > 71) ? namesArray[i].substring(0, 70) + "……" : namesArray[i];
			 else  var trimName = "; " + ( (namesArray[i].length > 71) ? namesArray[i].substring(0, 70) + "……" : namesArray[i] );
			$("<span>").html(trimName).append('<a class = "cancelSelectedItem" href="javascript:void(0)" title="点击删除" ><img id="image" src="image/del.gif" /></a>').appendTo("#trimedName");
		}
	}
}

/**
 *	弹出层单选列表显示时，设置默认选中
 * 	data.id	默认选中id
 * 	data.name 默认选中name
 */
function defaultSelect(data) {
	if(data && data.name) {
		showTrimedName(data.name);// 将默认的名字显示在选择页面上
	}
	if(data && data.id) {
		$("input[type='radio'][value=" + data.id + "]").attr("checked", true);
	}
}

/**
 * 弹出层多选列表显示时，设置默认选中
 */
function defaultSelectGroup(entity) {
	var id = entity.data.id;// 读取默认选中的ID
	var name = entity.data.name;// 读取默认选中的NAME
	if(name != null && name != "") {
		name = name.join("; ");
		showTrimedName(name);// 将默认的名字显示在选择页面上
	}
	if (id != null && id != "") {
		var id_length = id.length;
		$("input[type='checkbox']").each(function() {
			for (var i = 0; i < id_length; i++) {
				if (this.value == id[i]) {
					this.checked = true;
					break;
				}
			}
		});
	}
}

/**
 * 叉按钮点击事件，点击移除
 * 移除所有radio或者chackbox的checked属性，清空trimedName区域信息
 * @param type 0: radio; 1: checkbox.
 */
function cancelSelected(type){
	if(type == 0){
		var that = {id: "", alt: "", title: ""};
		that.value = that.alt = that.title = "";
		writeIdAndName(that);//模拟点击了一个空的radio
		$(":input[type='radio']:checked").removeAttr('checked');
		$("#trimedName").empty();
	}
	else if(type == 1){
		thisPopLayer.outData.data.id = thisPopLayer.outData.data.name = null;//将存储的ids和names清空
		$(".checkbox_select:checked", $("#list_container")).each(function(){
			$(this).removeAttr('checked');
		});
		$("#trimedName").empty();
	}
}

/**
 * 弹出层单选列表中，选择时将所选entity的id和name更新
 * 到默认值中，并刷新已选项的显示
 * @param that调用者对象
 */
function writeIdAndName(elem) {
	thisPopLayer.outData = {
		data : {"id" : elem.value, "name" : elem.alt, "sname" : elem.title}
	};
	showTrimedName(elem.alt);
}

/**
 * 弹出层多选列表中，选择时将所选entity的id和name更新
 * 到默认值中，并刷新已选项的显示
 * @param that调用者对象
 */
function writeIdsAndNames(that) {
	var entity = thisPopLayer.outData;// 读取父页面的传值
	var id = entity.data.id;// 读取默认选中的ID
	var name = entity.data.name;// 读取默认选中的NAME
	if (id == undefined || id == null) {
		id = [];
	}
	if (name == undefined || name == null) {
		name = [];
	}
	var tmp_id = that.value;
	var tmp_name = that.alt;
	if (that.checked == true) {// 选中，则将新增项添加的onloadDate中
		id.push(tmp_id);
		name.push(tmp_name);
	} else {// 取消，则从onloadDate中剔除此项
		var id_length = id.length;
		// 遍历数组，查找此项
		for (var i = 0; i < id_length; i++) {
			if (id[i] == tmp_id) {
				id.splice(i, 1);
				name.splice(i, 1);
				break;
			}
		}
	}
	thisPopLayer.outData.data.id = id;
	thisPopLayer.outData.data.name = name;
	name = name.join("; ");
	showTrimedName(name);
}

//人员合并公共js
/**
 * 弹出合并时可供选择的数据
 */
var pos;
/**
 * 适用于一般的文本框和下拉框
 * @param {} selector
 * @param {} data
 * @param {} isTop
 */
function bindBlockData(selector,data,isTop){
	if(isTop == undefined){
		isTop = true;
	}	
	
	var type = 0;//默认为input类型
	if(selector.is("select")){
		type=1;
	}		
	
	if(!isSame(data)){
		if(type==1){
			selector.addClass('select_warn');
		}else{
			selector.addClass('input_warn');
		}
	}
	
	selector.live("click",function(){	
		pos=selector;
		$('.choose_block').remove();
		var blockHtml = '<div class="choose_block" tabindex="1">';
		for(var i=0;i<data.length;i++){
			if(data[i]==null||data[i]==undefined){
				data[i]="";
			}					
			blockHtml += '<div class="radio_row"><input class="normal_radio" type="radio" name="items" />'+'<span>'+data[i]+'</span></div>';
		}
		//blockHtml +='<div class="radio_row"  ><a class="hide_block" target="main" href="javascript:void(0);">隐藏</a></div>';
		blockHtml +="</div>";
		$(this).after(blockHtml);
		var x = $(this).offset().top;
		var y = $(this).offset().left;
		var h = $('.choose_block').height()+$(this).height()+2;
		$(".choose_block").css("position","absolute");
		$(".choose_block").css("top",x);
		$(".choose_block").css("left",y);
		$(".choose_block").css("margin-top",$(this).height());
		$(".choose_block").css("z-index", 2000);
		if(isTop){
			$(".choose_block").css("top",x-h);
		}
		
		//raido选择操作
		if(pos[0].tagName == 'SELECT'){
			$('.normal_radio').die().live('click',function(){
				posVal = $(this).next().text()
				pos.find('option').each(function(){
					if ($(this)[0].text == posVal ){
						$(this).attr("selected",true);
					}
				});
			});
		}else{
			$('.normal_radio').die().live('click',function(){
				pos.val($(this).next().text()); 
			});
		}
	});
	
	$('body').click(function(evt) {
	    if($(evt.target).parents('.choose_block').length==0) {
	        $('.choose_block').hide();
	    }
	});
	
	
}

function bindBlockDutyData(selector,data,isTop){
	if(isTop == undefined){
		isTop = true;
	}
	
	if(!isSame(data)){
		selector.addClass('input_warn');
	}
	selector.live("click",function(){
		pos=selector;
		$('.choose_block').remove();
		var blockHtml = "<div class='choose_block'>";
		for(var i=0;i<data.length;i++){
			if(data[i]==null||data[i]==undefined){
				data[i]="";
			}					
			blockHtml += '<div class="radio_row"><input class="normal_radio" type="radio" name="items" />'+'<span>'+data[i]+'</span></div>';
		}
		//blockHtml +='<div class="radio_row"><a class="hide_block" target="main" href="javascript:void(0);">隐藏</a></div>';
		blockHtml +="</div>";
		$(this).after(blockHtml);
		var x = $(this).offset().top;
		var y = $(this).offset().left;
		$(".choose_block").css("position","absolute");
		$(".choose_block").css("top",x+6);
		$(".choose_block").css("left",y);
		$(".choose_block").css("margin-top","19px");
		$(".choose_block").css("z-index", 2000);
		$(".choose_block").css("text-align", "left");
		if(isTop){
			$(".choose_block").css("top",x-76);
		}
		
		//raido选择操作
		$('.normal_radio').die().live('click',function(){
			pos.val($(this).next().text()); 
		});	
	});
	
	selector.bind('blur',function(){
		$('.choose_block').remove();
	});
}

/**
 * 绑定要合并的单位
 * @param selector
 * @param data
 * @param ids
 */
function bindBlockUnitData(selector,data,ids){			
	if(!isSame(data)){
		selector.addClass('input_warn');
	}
	
	selector.live("click",function(){
		pos=selector;
		$('.choose_block').remove();
		var blockHtml = "<div class='choose_block'>";
		for(var i=0;i<data.length;i++){
			if(data[i]==null||data[i]==undefined){
				data[i]="";
			}					
			blockHtml += '<div class="radio_row"><input class="normal_radio" type="radio" name="items" value="'+ids[i]+'"/>'+'<span>'+data[i]+'</span></div>';
		}
		//blockHtml +='<div class="radio_row"><a class="hide_block" target="main" href="javascript:void(0);">隐藏</a></div>';
		blockHtml +="</div>";
		$(this).after(blockHtml);
		var x = $(this).offset().top;
		var y = $(this).offset().left;
		$(".choose_block").css("position","absolute");
		$(".choose_block").css("top",x+6);
		$(".choose_block").css("left",y);
		$(".choose_block").css("margin-top","19px");
		$(".choose_block").css("z-index", 2000);
		$(".choose_block").css("text-align", "left");

		$(".choose_block").css("top",x-76);

		
		//raido选择操作
		$('.normal_radio').die().live('click',function(){
			pos.html($(this).next().text()); 
			$("#unitId").val($(this).val());
			$("#departmentId").val($(this).val());
			$("#instituteId").val($(this).val());
		});
	});
	
	selector.bind('blur',function(){
		$('.choose_block').remove();
	});
}

/**
 * 判断data数组中元素是否全部相同
 * @param data
 * @returns {Boolean}
 */
function isSame(data){
	var arr = data.slice();
	for(var i=0,len=arr.length;i<len;i++)
	{
		if(!arr[i]||arr[i]==''||arr[i] === undefined)
		{
			arr.splice(i,1);
			len--;
			i--;
		}
	}
	if(arr.length<2){
		return true;
	}
	
	var s=arr[0];
	for(var i=1;i<arr.length;i++){
		if(s!==arr[i]){
			return false;
		}
	}
	return true;
}