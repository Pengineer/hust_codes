/**
 * 查看页面基础模块。
 * 定义了查看页面的公用方法。
 * 查看页面的三层结构：页面view(引用)模块view(引用)全局view
 * 
 * 可在各模块中通过require引入并定义模块名(比如 view)。
 * 引入后通过模块名.方法的形式调用模块定义的公用方法。
 * 如：
 * var view = require('javascript/view');
 * view.add(nameSpace);
 * 
 * @author zhouzj
 */
define(function(require, exports, module) {
	require('cookie');
	require('loading');
	
	var haveRead = [];
	
	/**
	 * ajax请求查看页面数据并显示（调用各模块或页面定义的showDetails方法）
	 * @param showDetails 作为参数传入，定义了如何将json对象显示在模板中
	 */
	var show = function(nameSpace, showDetails, viewType){
		viewType = viewType || "";
		$.isLoading(); //显示加载信息
		$.ajax({
			url: nameSpace + "/view.action",
			type: "post",
			data: "entityId=" + $("#entityId").val() + "&viewType=" + viewType,//type选填
			dataType: "json",
			success: showDetails//在具体的查看页面js实现
		});
	};
	/**
	 * 添加按钮绑定事件，点击跳转至添加页面
	 */
	var add = function(nameSpace){
		$("body").on("click","#view_add", function() {
			window.location.href = basePath + nameSpace + "/toAdd.action";
			return false;
		});
	};
	
	/**
	 * 修改按钮绑定事件，点击修改当前查看内容
	 */
	var mod = function(nameSpace){
		$("body").on("click","#view_mod", function() {
			window.location.href = basePath + nameSpace + "/toModify.action?entityId=" + $("#entityId").val();
			return false;
		});
	};
	
	/**
	 * 删除按钮绑定事件，点击删除当前查看内容，删除成功返回列表，失败则提示错误信息。
	 */
	var del = function(nameSpace, del_confirm){
		$("body").on("click","#view_del", function() {
			if (confirm("您确定要删除选中的记录吗？")) {
				$.ajax({
					url: nameSpace + "/delete.action?entityIds=" + $("#entityId").val(),
					type: "post",
					dataType: "json",
					success: function(result) {
						if (result.errorInfo == null || result.errorInfo == "") {
							window.location.href = basePath + nameSpace + "/toList.action?update=1&entityId=" + $("#entityId").val();
							return false;
						} else {
							alert(result.errorInfo);
						}
					}
				});
			};
			return false;
		});
	};
	
	/**
	 * 上一条
	 */
	var prev = function(nameSpace, showDetails){
		$("body").on("click","#view_prev", function() {
			$.isLoading(); //显示加载信息
			$.ajax({
				url: nameSpace + "/prev.action",
				type: "post",
				data: "entityId=" + $("#entityId").val(),
				dataType: "json",
				success: showDetails//在各模块中定义
			});
			return false;
		});
	};
	
	/**
	 * 下一条
	 */
	var next = function(nameSpace, showDetails){
		$("body").on("click", "#view_next",function() {
			$.isLoading(); //显示加载信息
			$.ajax({
				url: nameSpace + "/next.action",
				type: "post",
				data: "entityId=" + $("#entityId").val(),
				dataType: "json",
				success: showDetails
			});
			return false;
		});
	};
	
	/**
	 * 返回列表
	 */
	var back = function(nameSpace){
		$("body").on("click", "#view_back", function() {
			window.location.href = basePath + nameSpace + "/toList.action?entityId=" + $("#entityId").val() + "&update=" + 0;
			return false;
		});
	};
	

	/**
	 * 下载附件
	 */
	var download = function(nameSpace){
		$("body").on("click", ".attach",function() {
			var validateUrl = nameSpace + "/validateFile.action?entityId="+this.id+"&status=" + this.parentNode.id;
			var successUrl = nameSpace + "/download.action?entityId=" + $("#entityId").val() + "&status=" + this.parentNode.id;
			downloadFile(validateUrl, successUrl);
			return false;
		});
	};
	/**
	 * 初始化一组标签，账号、人员、机构等分标签查看页面用到。
	 * @param {Object} defaultTab 若有此参数，则跳到li a[href=defaultTab]的那个标签，否则跳到最后一次访问的标签
	 * @param {Object} cookieMajor 为1则以cookie为主，没有或者为0则以defaultIndex为主
	*/
	var inittabs = function(defaultTab, cookieMajor){
		if ($("#tabs").length) {
			var oriTab = defaultTab;
			var key = "tabs" + location.pathname;
			if ((cookieMajor || defaultTab == undefined) && $.cookie && $.cookie(key) != null){
				defaultTab = $.cookie(key);
			}
			var defaultIndex = undefined;
			$("div#tabs li a").each(function(index) {
				if ($(this).attr("href").indexOf(defaultTab) >= 0){
					defaultIndex = index;
				}
			});
			if( $("#tabs li:eq(" + defaultIndex + ")").css("display")=="none"){
				defaultTab = oriTab;
				defaultIndex = undefined;
				$("div#tabs li a").each(function(index) {
					if ($(this).attr("href").indexOf(defaultTab) >= 0){
						defaultIndex = index;
					}
				});
			}
			$("#tabs").tabs({
				select: function(event, ui){
					if (!haveRead[ui.index]){
						haveRead[ui.index] = 1;
						if (typeof(doRead) == "function"){
							doRead(ui.index);
						}
					}
					if ($.cookie){
						$.cookie(key, $("#tabs li:eq(" + ui.index + ") a").attr('href'));
					}
				},
				selected: defaultIndex || 0
			});
		}
	};
	
	/**
	 * 全局查看模块对外接口。
	 * 定义了查看、添加、修改、删除、上下条、返回、下载和初始化标签方法。
	 */
	module.exports = {
		show: function(nameSpace, showDetails, viewType) {show(nameSpace, showDetails, viewType);},
		add: function(nameSpace) {add(nameSpace)},
		mod: function(nameSpace) {mod(nameSpace)},
		del: function(nameSpace, del_confirm) {del(nameSpace, del_confirm)},
		prev: function(nameSpace, showDetails) {prev(nameSpace, showDetails)},
		next: function(nameSpace, showDetails) {next(nameSpace, showDetails)},
		back: function(nameSpace) {back(nameSpace)},
		download: function(nameSpace) {download(nameSpace)},
		inittabs: function(defaultTab, cookieMajor) {inittabs(defaultTab, cookieMajor)}
	};
});