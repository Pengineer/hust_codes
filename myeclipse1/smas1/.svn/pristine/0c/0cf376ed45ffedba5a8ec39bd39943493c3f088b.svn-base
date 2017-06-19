
/**
 * 用于登录后首页，实现菜单的展开与收起，左侧区域的收缩与展开，
 * 菜单与导航的切换
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
		if (className == "menu1") {
			hasSub = false;
		}
		if (status) {// 展开或选中
			if (hasSub) {
				$("#" + id).attr("class", "menu1");
			} else {
				$("#" + id).attr("class", "menu1");
			}
		} else {// 收起或未选中
			if (hasSub) {
				$("#" + id).attr("class", "menu1");
			} else {
				$("#" + id).attr("class", "menu1");
			}
		}
	} else if (len == 4) {// 二级菜单
		// 根据样式判断此二级菜单是否有子菜单
		var className = $("#" + id).attr("class");
		var hasSub = true;
		if (className == "menu2" || className == "menu2") {
			hasSub = false;
		}
		if (status) {// 展开或选中
			if (hasSub) {
				$("#" + id).attr("class", "menu2");
			} else {
				$("#" + id).attr("class", "menu2");
			}
		} else {// 收起或未选中
			if (hasSub) {
				$("#" + id).attr("class", "menu2");
			} else {
				$("#" + id).attr("class", "menu2");
			}
		}
	} else {// 三级菜单
		if (status) {// 选中
			$("#" + id).attr("class", "menu3");
		} else {// 未选中
			$("#" + id).attr("class", "menu3");
		}
	}
}

/**
 *  执行页面定时刷新的事件
 */
function refresh() {
	reSizeiFrame();// 刷新主页面内嵌帧高度
}

/**
 * 更新内嵌帧和左侧菜单块的高度
 */
function reSizeiFrame(){  
	var FFextraHeight = 8;
	if(window.navigator.userAgent.indexOf("Firefox") >= 1) {
		FFextraHeight = 10;// 经测试最小为2
	}
	var iframe = document.getElementById('right');
	if(iframe && !window.opera) {
		var height = 0;
		var height_menu = 0;// 左侧菜单的高度
		var height_iframe_content = 0;// iframe内容的高度
		if(iframe.contentDocument && iframe.contentDocument.body != null && iframe.contentDocument.body.offsetHeight) {
			height_iframe_content = iframe.contentDocument.body.offsetHeight + FFextraHeight;
		} else if (iframe.Document && iframe.Document.body != null && iframe.Document.body.scrollHeight) {
			height_iframe_content = iframe.Document.body.scrollHeight + FFextraHeight;
		}
		//获取左侧菜单<div class="left_menu">所在div的高度
		if ($(".left_menu").eq(0).css("display") != "none") {
			height_menu = $(".left_menu").eq(0).height();
		}
		// 获取height_menu和iframe内容中较高的一个，将iframe的高度以其中较大者为准,解决IE6下改变iframe高度后，菜单的异常
		height = height_menu > height_iframe_content ? height_menu : height_iframe_content;
		height = height + "px";
		//更新iframe和左侧菜单块的高度
		$(iframe).css("height", height);
		$(".left").css("height", height);
		
		// 修正由于相对定位带来的空白问题
		$("#center").css("height", height);
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
		$("#right").css("width", $(".right").width() - cut_width + "px");
	}
	var interval = setInterval(refresh, 200);
	
	
	$("select[name=year]").change(function(){
		$.ajax({
			url: "main/changeYear.action",
			type: "post",
			data: {defaultYear: $(this).val()},
			success: function(){
				parent.frames["right"].window.location = basePath + "main/right.action";
			}
		});
//		$.post("main/changeYear.action", 
//				{defaultYear:$(this).val()},
//				parent.frames["right"].window.location = basePath + "main/right.action"
//		);//本简化写法在ie6下反应不正常
	});
	
	$("select[name=projectType]").change(function(){
		$.ajax({
			url: "main/changeProjectType.action",
			type: "post",
			data: {defaultProjectType: $(this).val()},
			success: function(){
				parent.frames["right"].window.location = basePath + "main/right.action";
			}
		});
	});
	
	$(".menu li").bind("click", function() {
		menu_control(this.id);
	});
	
});

/**
 * 左侧菜单导出项目（导出所有项目）
 * @param exportAll 其中1：表示所有项目；0：表示根据条件导出
 * @returns {Boolean}
 */
function exportProject() {
	var label = prompt("是否包含评审信息（y/n）？", "y");
	if (label != null) {
		label = label.toLowerCase();
		if (label === "y") {
			window.location.href = basePath + "project/general/exportProjectReviewer.action?exportAll=1";
		}else if (label === "n") {
			window.location.href = basePath + "project/general/exportProject.action?exportAll=1";
		}else {
			alert("无效输入！");
		}
	}
	return false;
}

