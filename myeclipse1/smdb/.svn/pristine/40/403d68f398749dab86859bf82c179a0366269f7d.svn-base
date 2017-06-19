/**
 * 用于登录后首页，实现菜单的展开与收起，左侧区域的收缩与展开，
 * 菜单与导航的切换，
 * @author 龚凡
 * @2010.11.05
 */
/*
 * 设计一套菜单li的ID生成规则，通过ID规则体现各级菜单的关系。
 * 以两位数字表示一级菜单，从现在的菜单级别划分来看，菜单可
 * 依次表示为：
 * 一级菜单：xx
 * 二级菜单：xxyy
 * 三级菜单：xxyyzz
 */
var last_focus_menu = "";// 上次点击菜单

/**
 * 一次只支持一个菜单展开
 * @param {Object} current_menu_id当前点击的菜单ID
 */
function menu_control(current_menu_id) {
	var len = current_menu_id.length;
	var className = $("#" + current_menu_id).attr("class");// 获取此次操作菜单的样式
	var status = -1;// 记录此菜单状态，0收起或未选中、1展开或选中

	// 判断当前菜单的状态
	if (className == "menu1" || className == "menu1_sub" || className == "menu2" || className == "menu2_sub" || className == "menu3") {
		status = 0;
	} else {
		status = 1;
	}

	// 如果当前菜单为展开状态，则此次转为收起
	// 如果当前菜单为收起状态，则此次转为展开
	$(".menu li").each(function() {// 遍历所有菜单进行处理
		var id = this.id;
		var id_len = id.length;
		
		if ((id.indexOf(current_menu_id) == 0) && (id_len - len == 2)) {// 它的直接下级菜单
			if (status) {// 收起
				$(this).hide();
			} else {// 展开
				$(this).show();
			}
			change_menu_css(id, 0);
		} else if ((current_menu_id.indexOf(id) == 0) && (len - id_len == 2 || len - id_len == 4)) {// 它的上级菜单
			$(this).show();
			change_menu_css(id, 1);
		} else if ((current_menu_id.indexOf(id) == 0) && (id_len - len == 0)) {// 它自己
			if (className == "menu1_sub" || className == "menu1_sub_curr" || className == "menu2_sub" || className == "menu2_sub_curr" || className == "menu3" || className== "menu3_curr") {
				change_menu_css(id, 1);
			} else {// 如果有叶子节点，则存在收缩与展开的切换
				if (status) {// 收起
					change_menu_css(id, 0);
				} else {// 展开
					change_menu_css(id, 1);
				}
			}
			$(this).show();
		} else {// 其它不相关的菜单，同级别菜单显示，其它菜单一律隐藏，且一律收起或未选中状态
			if (id_len == 2) {// 一级菜单显示
				$(this).show();
			} else if (id_len == 4 && (id.substring(0, 2) == current_menu_id.substring(0, 2))) {// 同级别的二级菜单显示
				$(this).show();
			} else if (id_len == 6 && (id.substring(0, 4) == current_menu_id.substring(0, 4))) {// 同级别的三级菜单显示
				$(this).show();
			} else {// 其它菜单隐藏
				$(this).hide();
			}
			change_menu_css(id, 0);
		}
	});
	
	last_focus_menu = current_menu_id;// 更新当前点击菜单
}

/**
 * 切换菜单的css样式
 * @param id(更改样式的菜单ID)
 * @param status(状态，有子菜单--1展开、0收起；无子菜单--1选中、0未选中)
 * 一级菜单(menu1,menu1_open)(menu1_sub,menu1_sub_curr)
 * 二级菜单(menu2,menu2_open)(menu2_sub,menu2_sub_curr)
 * 三级菜单(menu3,menu3_curr)
 */
function change_menu_css(id, status) {
	var len = id.length;
	if (len == 2) {// 一级菜单
		// 根据样式判断此一级菜单是否有子菜单
		var className = $("#" + id).attr("class");
		var hasSub = true;
		if (className == "menu1_sub" || className == "menu1_sub_curr") {
			hasSub = false;
		}
		if (status) {// 展开或选中
			if (hasSub) {
				$("#" + id).attr("class", "menu1_open");
			} else {
				$("#" + id).attr("class", "menu1_sub_curr");
			}
		} else {// 收起或未选中
			if (hasSub) {
				$("#" + id).attr("class", "menu1");
			} else {
				$("#" + id).attr("class", "menu1_sub");
			}
		}
	} else if (len == 4) {// 二级菜单
		// 根据样式判断此二级菜单是否有子菜单
		var className = $("#" + id).attr("class");
		var hasSub = true;
		if (className == "menu2_sub" || className == "menu2_sub_curr") {
			hasSub = false;
		}
		if (status) {// 展开或选中
			if (hasSub) {
				$("#" + id).attr("class", "menu2_open");
			} else {
				$("#" + id).attr("class", "menu2_sub_curr");
			}
		} else {// 收起或未选中
			if (hasSub) {
				$("#" + id).attr("class", "menu2");
			} else {
				$("#" + id).attr("class", "menu2_sub");
			}
		}
	} else {// 三级菜单
		if (status) {// 选中
			$("#" + id).attr("class", "menu3_curr");
		} else {// 未选中
			$("#" + id).attr("class", "menu3");
		}
	}
}

function reSizeiFrame() {// 刷新主页面内嵌帧的高度
	var FFextraHeight = 8;
	if(window.navigator.userAgent.indexOf("Firefox") >= 1) {
		FFextraHeight = 10;// 经测试最小为2
	}
	var iframe = document.getElementById('main');
	if(iframe && !window.opera) {
		var height = 0;
		var height_menu = 0;// 左侧菜单的高度
		var height_iframe_content = 0;// iframe内容的高度
		if(iframe.contentDocument && iframe.contentDocument.body != null && iframe.contentDocument.body.offsetHeight) {
			height_iframe_content = iframe.contentDocument.body.offsetHeight + FFextraHeight;
		} else if (iframe.Document && iframe.Document.body != null && iframe.Document.body.scrollHeight) {
			height_iframe_content = iframe.Document.body.scrollHeight + FFextraHeight;
		}
		if ($(".left_menu").eq(0).css("display") != "none") {
			height_menu = $(".left_menu").eq(0).height();
		}
		// 获取left_menu和iframe内容中较高的一个，将iframe的高度以其中较大者为准,解决IE6下改变iframe高度后，菜单的异常
		height = height_menu > height_iframe_content ? height_menu : height_iframe_content;
		// 收缩按钮相比，不得小于收缩按钮高度
		var expand_height = $("#expand").height();// 获取收缩图标高度--59
		height = height > expand_height ? height : expand_height;
		height = height + "px";
		$(iframe).css("height", height);
		$(".left").css("height", height);
		
		// 修正由于相对定位带来的空白问题
		$("#center").css("height", height);
	}
}

function resetPositionOfExpand() {// 定位收缩按钮，样式采用相对定位
	var top_height = $(".top").height();// 获得top元素的高度--113
	var center_height = $("#center").height();// 获得center元素的高度--随内容动态涨
	var footer_height = $(".footer").height();// 获得footer高度--80
	var left_menu_bar_height = $(".left_menu_bar").height();// 获取左侧菜单头高度--30
	var expand_height = $("#expand").height();// 获取收缩图标高度--59
	var scroll_height = getPageScroll();// 获得滚动条滚过的高度
	var page_size = getPageSize();// 获取页面大小，依次为页面宽度、页面高度、窗口宽度、窗口高度
	var ratio = 0.3;// 用于定位为相对窗口顶部高度的比例
	var absolute_height = ratio * page_size[3] + scroll_height[1];// 滚动条滚过的高度+相对窗口的高度=绝对定位时相对页面顶部的高度
	var relative_height = absolute_height - top_height;// 绝对高度-顶栏高度=相对定位时相对窗口上述比例的高度
	if (relative_height <= left_menu_bar_height) {// 为避免定位与菜单头重叠，要求最小相对定位=菜单头高度 
		relative_height = left_menu_bar_height
	}
	if (relative_height + expand_height >= center_height) {// 为避免定位与底边栏重叠，要求最大相对定位+收缩图标高度<=中间栏的高度
		relative_height = center_height - expand_height;
	}
	$("#expand").css("top", relative_height + "px");
}
/**
 * 根据弹出层中页面高度调整弹出层的高度
 */
function reSizePop(){
	for(var i=0; i<$(".outer").size(); i++){
		var outDiv = $(".outer").eq(i);
		var iframeHeight = Math.min($(".layerIframe",outDiv).contents().find("body").height(),750); //限高750
		var outDivTop = parseInt(outDiv.css("top").replace(/px/,""));
		if(outDivTop >Math.max(document.body.clientHeight,document.documentElement.clientHeight)- iframeHeight-72 ){
			outDiv.css("height",Math.max(document.body.clientHeight,document.documentElement.clientHeight)- outDivTop+"px");
		}else{
			outDiv.css("height",iframeHeight+72+"px");
		}
		$(".layerIframe",outDiv).css("height",iframeHeight+"px");
		$(".popup_win",outDiv).css("height", iframeHeight+52+"px");
		$(".popup_win_box",outDiv).css("height", iframeHeight+50+"px");
	}
}

function showLocale() {
	var dn, str;
	var objD;
	objD = new Date();
	var d = new initArray("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	var hh = objD.getHours();
	var mm = objD.getMinutes();
	var ss = objD.getSeconds();
	str = objD.getFullYear() + "年" + (objD.getMonth() + 1) + "月"
			+ objD.getDate() + "日  " + d[objD.getDay() + 1];
	return str;
}

function initArray() {
	this.length = initArray.arguments.length
	for ( var i = 0; i < this.length; i++)
		this[i + 1] = initArray.arguments[i];
}

function tip() {
	//alert("系统处于维护中，有些功能暂时无法使用，给您带来的不便敬请谅解！");
}

function refresh() {// 执行页面定时刷新的事件
	reSizeiFrame();// 刷新主页面内嵌帧高度
	resetPositionOfExpand();// 刷新主页面收缩按钮位置
	if($(".outer").size()>0){ //在有弹出层的时候执行
		reSizePop(); //刷新弹出层的高度
	}
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

function goTop(){
	$(window).scroll(function(e) {
		//若滚动条离顶部大于100元素
		if($(window).scrollTop()>100)
			$("#gotop").fadeIn(1000);//以1秒的间隔渐显id=gotop的元素
		else
			$("#gotop").fadeOut(1000);//以1秒的间隔渐隐id=gotop的元素
	});
};

function menufunction(label) {
	if (label == 0) {//显示菜单栏
		$("#menu").show();
		$("#func").hide();
		$("#menu_bar").removeClass("left_menu_curr2").addClass("left_menu_curr1");
		$("#func_bar").removeClass("left_menu_curr1").addClass("left_menu_curr2");
	} else if (label == 1) {
		//导航栏
		$("#menu").hide();
		$("#func").show();
		$("#menu_bar").removeClass("left_menu_curr1").addClass("left_menu_curr2");
		$("#func_bar").removeClass("left_menu_curr2").addClass("left_menu_curr1");
		getData();
	}
};
function showuser() {
	var ob;
	ob = document.getElementById("history");
	if (ob != null) {
		ob.style.display = ob.style.display == "block" ? "none" : "block";
	}
	var className = $("#his").attr("class");// 获取此次操作菜单的样式
	if (className == "menu1") {
		$("#his").attr("class", "menu1_open");
	} else {
		$("#his").attr("class", "menu1");
	}
}

function getData(){
	var url = "navigation/getHistory.action";
	$.ajax({
		  url: url,
		  type: "post",
		  dataType: 'json',
		  success: add
		});
};

function add(data) {
	var menusArray = data.menus;
	var s = document.getElementById('historylist');
	s.innerHTML = "";  
    for(var i=0; i<menusArray.length; i+=2) {
    	s.innerHTML= s.innerHTML + "<li class=menu2_sub ><a target= main href=" +menusArray[i+1] +" >"+menusArray[i]+"</a></li>";
    }  
}

$(document).ready(function() {
	var cut_width = 4;
	/**
	 * IE6下设置主页面中iframe的宽度。
	 * 在IE6中iframe初始载入时，其宽度计算是根据其父元素<div class="right">来的，
	 * 但是通过target其再次载入其它内容后，其宽度的计算却是根据元素<div class="container">来的，
	 * 所以需要进行特殊处理，不再设置百分数，而是直接计算，给iframe设置一个具体宽度值，为了适应定宽与变宽的变化，
	 * 这个值需要计算出来，根据其父元素<div class="right">的宽度，减去一个固定值，这里取"4"，得到
	 */
	if (getBrowserVersion() == "msie 6.0") {
		$("#main").css("width", $(".right").width() - cut_width + "px");
	}

	var interval = setInterval(refresh, 200);
	setTimeout("'clearInterval(" + interval + ")'", 100);
	
	$(window).load(function() {// 主页面加载后，显示提示
		setTimeout("tip();", 500);
	});
	$(window).resize(function() {// 窗口大小改变时设置收缩按钮位置
		resetPositionOfExpand();// 重置收缩按钮位置
		// IE6变宽条件下，重置iframe宽度
		var str = $(".container").css("margin-bottom");
		var container_variable = (str == "auto") ? true : false;
		if (getBrowserVersion() == "msie 6.0" && container_variable) {
			var tmp_width = $(".container").width();
			var tmp_menu = $(".left_menu");
			if (tmp_menu.css("display") == "block") {
				tmp_width -= tmp_menu.width();
			}
			$("#main").css("width", tmp_width - cut_width + "px");
		}// 此段代码IE6下效率低
		$("#loading").css("left", centerLoading());// 重置加载图片位置
	});
	$(window).scroll(function() {// 滚动条滚动时设置收缩按钮位置
		resetPositionOfExpand();
	});
	
	$("#expand_img").click(function() {// 收缩按钮点击事件
		var src = $("#expand_img").attr("src");
		if (src == "image/close_menu.gif") {
			$(".left_menu").hide();
			$(".right").css("margin-left", "0px");
			$("#expand").css("left", "0px");
			$("#expand_img").attr("src", "image/open_menu.gif");
			$("#center").attr("class", "center_away");
			if (getBrowserVersion() == "msie 6.0") {
				$("#main").css("width", $("#main").width() + 211 + "px");
			}
			$("#loading").css("left", centerLoading());
		} else {
			$(".left_menu").show();
			$(".right").css("margin-left", "211px");
			$("#expand").css("left", "-9px");
			$("#expand_img").attr("src", "image/close_menu.gif");
			$("#center").attr("class", "center");
			if (getBrowserVersion() == "msie 6.0") {
				$("#expand").css("left", "-12px");
				$("#main").css("width", $("#main").width() - 211 + "px");
			}
			$("#loading").css("left", centerLoading());
		}
	});
	
	$.ajax({
		url: "view/viewMemo.action",
		type: "post",
		data: "entityId=" + "first",
		dataType: "json",
		success: function(result) {
			if (result.errorInfo == null || result.errorInfo == "") {
				var memoCount = result.memoCount.memoCount;
				if(memoCount != 0){
					popMemo();
					return false;
				}
				return false;
			} else {
				alert(result.errorInfo);
			}
		}
	});
	

	$("#switch_database").bind("mouseover",function() {
		var serverName = $("#serverName").attr("alt");
		$("img[name='serverName']").each(function() {
			if ($(this).attr("value") == serverName) {
				$(this).parent().css({
					"background-color":"#e4751b"
				});
				$(this).parent().children().eq(2).css({
					"color":"white"
				});
				$(this).parent().attr("flag", "true");//标记当前系统
			}
		});
		$("#switchServer").show();
	}).bind("mouseleave",function() {
		$("#switchServer").hide();
	});
	
	$(".choose1").bind("mouseover",function() {
		if(!($(this).children().eq(0).attr("disabled")) && !(($(this).attr("flag")) == "true")) {
			$(this).css({
				"cursor":"pointer",
				"background-color":"#B15BFF"
			});
			$(this).children().eq(2).css({
				"color":"white"
			});
		};
	}).bind("mouseout",function(evt){
		if(!($(this).children().eq(0).attr("disabled")) && !(($(this).attr("flag")) == "true")){
			$(this).css({
				"background-color":""
			});
			$(this).children().eq(2).css({
				"color":"#6C6C96"
			});
		};
	}).bind("click",function(){
		if(!($(this).children().eq(0).attr("disabled"))){
			var serverName = $(this).children().eq(0).attr("value");
			$("#switchServer").hide();
			window.location.href = basePath + "login/switchServer.action?serverName=" + serverName;
		};
	});
	
	$("#switch_account").bind("click", function() {
		var accountId = $(this).attr("alt");
		popSwitchAccount({
			src : basePath + "login/toSwitchAccount.action?accountId=" + accountId,
			inData : {"accountId" : accountId},
			callBack : function(result){
				if(result.username != null && result.username != "" && result.username != undefined){
					window.location.href = basePath + "login/toSelectAccount.action?username=" + result.username;
					return false;
				} else if (result.accountId != accountId){
					window.location.href = basePath + "login/switchAccount.action?accountId=" + result.accountId;
					return false;
				}
				//window.location.href = basePath + "login/switchAccount.action?accountId=" + result.accountId + "&username=" + result.username;
			}
		});
	});
	
	$(".menu li").bind("click", function() {
		menu_control(this.id);
	});
	$(".menu3").mouseover(function() {
		$(this).attr("class", "menu3_curr");
	});
	$(".menu3").mouseout(function() {
		if (this.id == last_focus_menu) {
			$(this).attr("class", "menu3_curr");
		} else {
			$(this).attr("class", "menu3");
		}
	});
	
	$(".menu0 li").bind("click", function() {
		showuser();
	});
	
	$(function(){
		//点击回到顶部的元素
		$("#gotop").click(function(e) {
			//以1秒的间隔返回顶部
			$('body,html').animate({scrollTop:0},"fast");
		});
		$("#gotop").mouseover(function(e) {
			$(this).css("background","url(image/backtop2013.png) no-repeat 0px 0px");
		});
		$("#gotop").mouseout(function(e) {
			$(this).css("background","url(image/backtop2013.png) no-repeat -70px 0px");
		});
		goTop();//实现回到顶部元素的渐显与渐隐
	});
});