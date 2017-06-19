/*
*1、   列表页面显示列表内容的div容器ID为list_container
*2、   列表页面模板区域textarea的ID为list_template
*3、   列表页面显示列表页码下拉框的div容器ID为list_select
*4、   列表页面选择页码到指定页的下拉框select的ID为list_goto
*5、   列表页面检索按钮ID为list_button_query
*6、   列表页面高级检索按钮ID为list_button_advsearch
*7、   列表页面改变页面大小选择框ID为list_changepagesize
*8、   列表页面当前页码隐藏表单的ID为list_pagenumber
*9、   列表页面排序列隐藏表单的ID为list_sortcolumn
*10、 列表页面检索form的ID为search
*11、列表页面功能form的ID为list
*/
/*
* init 初始化参数，可自定义参数如下所示：
* var nameSpace = "role";
* {
*		"nameSpace":nameSpace,
*       "sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2"],
*       "sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2},
*       "listType":(0-常规列表,1-弹出层单选列表,2-弹出层复选列表,3-查看页面内嵌列表)
* }
*/
var json = {}; // 服务器端返回的json对象
var data = []; // 客户端列表分页缓存对象
var itemNumber;// 总记录数
var pageSize;// 每页显示数
var pageNumber;// 总页数
var currentPageNo;// 当前页码
var pageInfo;// 页面信息，总记录数、总页数等
var pageSelect;// 页码输入框
var pageBackNumber;// 服务器端返回的页数
var startPageNumber;// 当前缓存区段的起始页码
var nameSpace;// URL前缀
var selectedTab;//标签定位
var sortColumnId;// 支持排序的列ID
var sortColumnValue;// 排序列的值
var listType;// (0-常规列表,1-弹出层单选列表,2-弹出层复选列表)
var viewParam;//查看详情时向后台传的参数
var truncateSettings;//截断显示设置
var loadTemplate = function(listjson) {// 加载模板
	$("#list_container").hide();
	$("#list_container").html(TrimPath.processDOMTemplate("list_template", listjson));
//	setOddEvenLine("list_container", listType);// 设置奇偶行效果
	if (typeof(listjson.root) == "undefined" || listjson.root.length == 0) {
		setColspan("list_table");// 设置空列表提示的跨列数
	}
	$("#list_container").show();
	if (parent != null && listType == 0) {
		parent.loading_flag = false;
		parent.hideLoading();
	}
};

var showPageNo = function() {// 加载页码及翻页按钮
	var tr = $("#list_container").find("table").eq(1).find("tr");// 列表最后一行
	// 生成总记录数、每页显示条数
	if (listType == 0) {
		pageInfo = $("<td align='right' class='border1' style='color:#000;'></td>");
		$("<span class='text_white'>共</span>").appendTo(pageInfo);
		$("<span id='list_itemNumber'></span>").appendTo(pageInfo);
		$("<span>条&nbsp;</span>").appendTo(pageInfo);
		$("<span>每页</span>").appendTo(pageInfo);
		var pageSizeSelect = $("<select id='list_changepagesize'></select>");
		$("<option value='10'>10</option>").appendTo(pageSizeSelect);
		$("<option value='20'>20</option>").appendTo(pageSizeSelect);
		$("<option value='50'>50</option>").appendTo(pageSizeSelect);
		pageSizeSelect.appendTo(pageInfo);
		$("<span>条&nbsp</span>").appendTo(pageInfo);
		tr.append(pageInfo);
		$("#list_itemNumber").html(itemNumber);// 显示总记录数
		$("#list_changepagesize").attr("value", json.pageSize);// 设置页面表单，每页显示数
	}
	// 生成翻页
//	tr.append($("<td class='wd_right border1' colspan='6'><img id='list_first' title='首页' src='image/first_page.gif' />&nbsp;<img id='list_previous' title='上页' src='image/prev_page.gif' />&nbsp;<img id='list_next' title='下页' src='image/next_page.gif' />&nbsp;<img id='list_last' title='末页' src='image/last_page.gif' /></td>"));
	tr.append($("<td width='28' align='center' class='border1'><img id='list_first' title='首页' src='image/first_page.gif' /></td>"));
	tr.append($("<td width='28' align='center' class='border1'><img id='list_previous' title='上页' src='image/prev_page.gif' /></td>"));
	tr.append($("<td width='28' align='center' class='border1'><img id='list_next' title='下页' src='image/next_page.gif' /></td>"));
	tr.append($("<td width='28' align='center' class='border1'><img id='list_last' title='末页' src='image/last_page.gif' /></td>"));
	tr.append($("<td class='wd_right border1'></td>").append("<span>第</span>").append(pageSelect).append($("<span>/" + pageNumber + "</span>")).append("<span>页</span>"));
	var td = tr.find("td:last-child");// 根据内容确定最后一个单元格的宽度
	var goto_width = $("#list_goto").width();
	td.css("width", 38 + goto_width * 2 + "px");
	td.css("text-align", "left");
	$("#list_sortcolumn").attr("value", json.sortColumn);// 设置页面隐藏表单，当前排序
	if (pageNumber == 0) {
		$("#list_goto").attr("value", 0);// 设置到指定页表单
		$("#list_pagenumber").val(0);
	} else {
		$("#list_goto").attr("value", currentPageNo);// 设置到指定页表单
		$("#list_pagenumber").val(currentPageNo);
	}
	$(".session").each(function(){
		$(this).html(Num2Chinese(parseInt($(this).html())));
	});
};

var showFirstPage = function() {// 首页
	if (pageNumber != 0) {
		if (currentPageNo != 1) {
			if (currentPageNo > data.length) {
				currentPageNo = 1;
				$("#list_pagenumber").attr("value", currentPageNo);
				$("#search").attr("action", nameSpace + "/toPage.action");
				getData();
			} else {
				currentPageNo = 1;
				$("#list_pagenumber").attr("value", currentPageNo);
				loadTemplate(data[0]);
				showPageNo();
			}
		}
	}
};
var showPreviousPage = function() {// 上页
	if (pageNumber != 0) {
		if (currentPageNo != 1) {
			currentPageNo--;
			$("#list_pagenumber").attr("value", currentPageNo);
			if (currentPageNo < startPageNumber) {
				$("#search").attr("action", nameSpace + "/toPage.action");
				getData();
			} else {
				loadTemplate(data[currentPageNo - startPageNumber]);
				showPageNo();
			}
		}
	}
};
var showNextPage = function() {// 下页
	if (pageNumber != 0) {
		if (currentPageNo != pageNumber) {
			currentPageNo++;
			$("#list_pagenumber").attr("value", currentPageNo);
			if (currentPageNo >= startPageNumber + pageBackNumber) {
				$("#search").attr("action", nameSpace + "/toPage.action");
				getData();
			} else {
				loadTemplate(data[currentPageNo - startPageNumber]);
				showPageNo();
			}
		}
	}
};
var showLastPage = function() {// 末页
	if (pageNumber != 0) {
		if (currentPageNo != pageNumber) {
			currentPageNo = pageNumber;
			$("#list_pagenumber").attr("value", currentPageNo);
			if (startPageNumber + data.length - 1 < currentPageNo) {
				$("#search").attr("action", nameSpace + "/toPage.action");
				getData();
			} else {
				loadTemplate(data[data.length - 1]);
				showPageNo();
			}
		}
	}
};
var showGoTo = function() {// 到指定页
	var oldPageNo = $("#list_pagenumber").val();
	currentPageNo = $("#list_goto").val();
	if (currentPageNo <= pageNumber && currentPageNo >= 1) {
		$("#list_pagenumber").attr("value", currentPageNo);
		if (currentPageNo >= startPageNumber && currentPageNo <= startPageNumber + data.length - 1) {
			loadTemplate(data[currentPageNo - startPageNumber]);
			showPageNo();
		} else {
			$("#search").attr("action", nameSpace + "/toPage.action");
			getData();
		}
	} else {
		$("#list_goto").val(oldPageNo);
	}
};
var showPageSize = function() {// 改变列表大小
	$("#list_pagesize").attr("value", $("#list_changepagesize").val());
	$("#search").attr("action", nameSpace + "/changePageSize.action");
	getData();
};
var showSort = function(sortColumn) {// 排序
	$("#list_sortcolumn").attr("value", sortColumn);
	$("#list_pagenumber").attr("value", $("#list_goto").val());
	$("#search").attr("action", nameSpace + "/sort.action");
	getData();
};
var showSimpleSearch = function() {// 检索
	var keyword = $("#keyword").val().trim();
	$("#keyword").val(keyword);
	$("#search").attr("action", nameSpace + "/simpleSearch.action");
	getData();
};
var showAdvSearch = function() {// 高级检索，参考smdb
	$("#advSearch").attr("action", nameSpace + "/advSearch.action");
//	if (parent != null && listType == 0) {
//		parent.loading_flag = true;
//		setTimeout("parent.showLoading();", parent.loading_lag_time);
//	}
	$("#advSearch").submit();
};
var getData = function() {// 获取数据
	if (parent != null && listType == 0) {
		parent.loading_flag = true;
		setTimeout("parent.showLoading();", parent.loading_lag_time);
	}
	$("#search").submit();
};
var optionssearch = {
	dataType: "json",
	success: function (result) {
		if (result.errorInfo == null || result.errorInfo == "") {
			json = result;
			showData();
		} else {
			alert(result.errorInfo);
		}
	}
};
var optionslist = {
	dataType: "json",
	success: function (result) {
		if (result == undefined || result == null) {// 未知的错误异常
			alert("未知的错误异常");
		} else if (result.errorInfo == null || result.errorInfo == "") {
			$("#search").attr("action", nameSpace + "/toPage.action?update=1");
			getData();
		} else {// 已知的错误异常
			alert(result.errorInfo);
		}
	}
};
var getNumberLength = function(number) {// 根据数字的位数，确定输入框的宽度
	var strNumber = "" + number;
	var length = strNumber.length + 1;
	return 7 * length + "px";
};
var showData = function() {// 显示数据
	itemNumber = json.totalRows;// 初始化总记录数
	pageBackNumber = json.pageBackNumber;// 初始化后台返回页数
	pageSize = json.pageSize;// 初始化每页显示数
	pageNumber = 0;// 初始化总页数
	if (itemNumber % pageSize == 0) {
		pageNumber = itemNumber / pageSize;
	} else {
		pageNumber = Math.floor(itemNumber / pageSize) + 1;
	}
	currentPageNo = json.pageNumber;// 初始化当前页码
	startPageNumber = json.startPageNumber;// 初始化当前缓存起始页码
	pageSelect = $("<input id='list_goto' type='text' style='width:" + getNumberLength(pageNumber) + "; height:14px; font-size:12px;' />");// 初始化页码下拉框
	var tmpdata = [];
	var startRow = (json.startPageNumber - 1) * pageSize;
	for (var i = 0, j = 0, k = 0; i < json.laData.length; i++) {
		if (!tmpdata[j]) {
			tmpdata[j] = $.parseJSON('{"root": [], "sortColumn": -1, "sortColumnLabel": -1}');
		}
		var root = {"laData": [], "num": 0};
		root.laData = json.laData[i];
		root.num = startRow + (json.pageSize * j) + (k + 1);
		tmpdata[j].root[k] = root;
		tmpdata[j].sortColumn = json.sortColumn;
		tmpdata[j].sortColumnLabel = json.sortColumnLabel;
		k++;
		if (i % pageSize == pageSize - 1) {
			j++;
			k = 0;
		}
	}
	data = tmpdata;
	if (data.length > 0) {
		loadTemplate(data[currentPageNo - startPageNumber]);	// 加载初始页
	} else {
		data = $.parseJSON('{"root": [], "sortColumn": -1, "sortColumnLabel": -1}');
		data.sortColumn = json.sortColumn;
		data.sortColumnLabel = json.sortColumnLabel;
		loadTemplate(data);
	}
	showPageNo();
};
var doTruncateShow = function(){
	if(!truncateSettings){return;}
	$(truncateSettings).each(function(){
		var $this = this;
		$("." + $this["className"]).each(function(){
			var value = $(this).html().toString();
			$(this).html(setTruncate(value, $this["length"], "..."))
				.bind("mouseover", function(){
					//$(this).html(value);
					$(this).attr("title", value);
				}).bind("mouseout", function(){
					$(this).html(setTruncate(value, $this["length"], "..."));
			});
		});
	});
};
var initPageShow = function(init) {
	// 参数预处理
	if (typeof(init.nameSpace) == 'undefined') {
		nameSpace = null;
	} else {
		nameSpace = init.nameSpace;
	}
	if (typeof(init.selectedTab) == 'undefined') {
		selectedTab = null;
	} else {
		selectedTab = init.selectedTab;
	}
	if (typeof(init.sortColumnId) == 'undefined') {
		sortColumnId = null;
	} else {
		sortColumnId = init.sortColumnId;
	}
	if (typeof(init.sortColumnValue) == 'undefined') {
		sortColumnValue = null;
	} else {
		sortColumnValue = init.sortColumnValue;
	}
	if (typeof(init.listType) == 'undefined') {
		listType = 0;
	} else {
		listType = init.listType;
	}
	if (typeof(init.viewParam) == 'undefined') {
		viewParam = null;
	} else {
		viewParam = init.viewParam;
	}
	if(typeof(init.truncateSettings) == 'undefined'){
		truncateSettings = null;
	} else {
		truncateSettings = init.truncateSettings;
	}
	$("#search").submit(function() {
		$(this).ajaxSubmit(optionssearch);
		return false;
	});
	$("#advSearch").submit(function() {// 提交ajax请求，返回列表数据处理
		$(this).ajaxSubmit(optionssearch);
		return false;
	});
	$("#list").submit(function() {
		$(this).ajaxSubmit(optionslist);
		return false;
	});
	getData();
	
	//如果表头选中，则将所有checkbox标为选中状态
	$("#check").live("click", function() {
		//调用common.js中checkAll()方法，将所有checkbox标为选中状态
		checkAll(this.checked, "entityIds");
	});// 弹出层选择列表设置默认选中
	$("#list_add").live("click", function() {
		var expertType = $("#expertType").val();
		var businessType = $("#businessType").val();
		if(("" == expertType || "undefined" == typeof(expertType) || null == expertType) && businessType != null && typeof(businessType) != "undefined" && null != businessType){
			var url = basePath + nameSpace + "/toAdd.action?listType=1&businessType=" + businessType;
		}else{
			var url = basePath + nameSpace + "/toAdd.action?expertType=" + expertType;
		}
		if(viewParam != null && viewParam.listflag != undefined){
			url += "&listflag=" + viewParam.listflag;
		}
		window.location.href = url;
		return false;
	});
	
	/*
	 * 绑定删除按钮点击事件，当点击删除时调用common.js中count()方法，检查选中的行
	 */
	$("#list_delete").live("click", function() {
		var cnt = count("entityIds");
		if (cnt == 0) {
			alert("请选择要删除的记录！");
		} else {
			if (confirm("您确定要删除选中的记录吗？")) {
				$("#type").attr("value", 1);
				$("#pagenumber").attr("value", $("#list_goto").val());
				$("#list").attr("action", nameSpace + "/delete.action");
				$("#list").submit();
			}
		}
		return false;
	});
	$(".link1").live("click", function(){
		//var url = basePath + nameSpace + "/toView.action?entityId=" + this.id + "&pageNumber=" + $("#list_pagenumber").val();
		var url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
		url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
		url += (selectedTab != null) ? "&selectedTab=" + selectedTab : "";
		if(viewParam != null && viewParam.listflag != undefined){
			url += "&listflag=" + viewParam.listflag;
		}
		window.location.href = url;
		return false;
	});
	$("#list_button_query").bind("click", function() {
		showSimpleSearch();
		return false;
	});
	$("#list_button_advsearch").live("click", function() {
		showAdvSearch();
		return false;
	});
	//参考smdb，做高级检索类型
/*	$("#list_button_advSearch").bind("click", function() {
		$("#advSearch").submit();
	});*/
	$("#list_search_more").click(function(){// 点击展开更多条件
		$("#adv_search").slideToggle("slow");
		$("#simple_search").hide();
		$(this).attr("value", "更多条件");
	});
	$("#list_search_hide").click(function(){// 点击收起更多条件
		$("#adv_search").slideToggle(50);
		$("#simple_search").show();
		$(this).attr("value", "隐藏条件");
	});
/*	$("#list_button_advsearch").bind("click", function() {
		var url = basePath + nameSpace + "/toAdvSearch.action?type=1";
		if(viewParam != null && viewParam.listflag != undefined){
			url += "&listflag=" + viewParam.listflag;
		}
		window.location.href = url;
		return false;
	});*/
	$("#list_changepagesize").live("change", function() {
		showPageSize();
		return false;
	});
	$("#list_first").live("click", function() {
		showFirstPage();
		return false;
	});
	$("#list_previous").live("click", function() {
		showPreviousPage();
		return false;
	});
	$("#list_next").live("click", function() {
		showNextPage();
		return false;
	});
	$("#list_last").live("click", function() {
		showLastPage();
		return false;
	});
	$("#keyword").live("keypress", function(event) {
		var keyCode = event.which;
		if (keyCode == 13) {
			showSimpleSearch();
			return false;
		} else {
			return true;
		}
	});
	$("#list_goto").live("click", function(){
		this.select();
	}).live("keypress", function(event) {
		var keyCode = event.which;
		if (keyCode == 13) {
			showGoTo();
			return false;
		} else {
			return true;
		}
	}).live("blur", function(event) {
		showGoTo();
	});
	if (sortColumnId != null && sortColumnValue != null) {
		for (var i = 0; i < sortColumnId.length; i++) {
			$("#" + sortColumnId[i]).live("click", function(){
				showSort(sortColumnValue[this.id]);
				return false;
			});
		}
	}
	$("input[name='entityIds']").live("click", function() {
		var checkbox_length = $("input[name='entityIds']").length;
		var cnt = count("entityIds");// 当前已选中的个数
		if (this.checked) {// 当有项被选中时，判断当前是否已全选了
			if (cnt == checkbox_length) {
				$("#check").eq(0).attr("checked", true);
			}
		} else {// 当有项撤销选中时，判断头是否处于非全选状态
			$("#check").eq(0).attr("checked", false);
		}
	});
};
var pageShow = function(init) {
	initPageShow(init);
};
