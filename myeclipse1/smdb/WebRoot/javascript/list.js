/**
 * 列表方法
 * 1、列表页面显示列表内容的div容器ID为list_container
 * 2、列表页面模板区域textarea的ID为list_template
 * 3、列表页面显示列表页码下拉框的div容器ID为list_select
 * 4、列表页面选择页码到指定页的下拉框select的ID为list_goto
 * 5、列表页面检索按钮ID为list_button_query
 * 6、列表页面高级检索按钮ID为list_button_advsearch
 * 7、列表页面改变页面大小选择框ID为list_changepagesize
 * 8、列表页面当前页码隐藏表单的ID为list_pagenumber
 * 9、列表页面排序列隐藏表单的ID为list_sortcolumn
 * 10、列表页面检索form的ID为search
 * 11、列表页面功能form的ID为list
 * 
 * init 初始化参数，可自定义参数如下所示：
 * var nameSpace = "role";
 * {
 * 	"nameSpace":nameSpace,
 * 	"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2"],
 * 	"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2},
 * 	"listType":(0-常规列表(默认值),1-弹出层单选列表,2-弹出层复选列表,3-查看页面内嵌列表)
 * }
 * @author gongfan, zhouzj
 * @version 1.1
 * @depends 参考seajs文档，依赖模块在require中定义
 */
define(function(require, exports, module) {
	require('form');
	require('tool/highcharts/highcharts');
	require.async('tool/highcharts/exporting');
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
	var dealWith;//附加处理函数
	var dealWithBefore;//用来对模版或数据进行处理
	var nameSpace;// URL前缀
	var selectedTab;//标签定位
	var sortColumnId;// 支持排序的列ID
	var sortColumnValue;// 排序列的值
	var listType;// (0-常规列表,1-弹出层单选列表,2-弹出层复选列表)
	var viewParam;//查看详情时向后台传的参数
	var truncateSettings;//截断显示设置
	
	/**
	 * 字符串截断
	 * string : 字符串
	 * length : 截断长度(汉字为长度1;其它字符为长度0.5)
	 * append : 补充串
	*/
	var setTruncate = function(string, length, append){
		if(typeof s != "string" || !/^[1-9]\d*$/.test("" + length)){return string;}
		var os = string, count = 0, sub = "";
		while(/[\u4E00-\u9FFF]/.test(string)){
			string = string.replace(/[\u4E00-\u9FFF]/, "");
			count++;
		}
		if(string.length/2 + count > length){
			var cnt = 0, len = 0;
			for(var i = 0; i < os.length; i++){
				if(/[\u4E00-\u9FFF]/.test(os.charAt(i).toString())){
					cnt += 2;				
				} else {
					cnt++;
				}
				len++;
				if(cnt/2 >= length){
					sub = os.substring(0, len) + ((append) ? append : "");
					break;
				}
			}
		} else {
			sub = os;
		}
		return sub;
	};
	// 截断显示
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
	var getData = function() {// 获取数据
		if (parent != null && listType == 0) {
			parent.loading_flag = true;
			setTimeout("parent.showLoading();", parent.loading_lag_time);
		}
		$("#list_first, #list_previous, #list_next, #list_last").attr("disabled", true);
		$("#search").submit();
	};
	var loadTemplate = function(listjson) {// 加载模板
		dealWithBefore(listjson, json);
		$("#list_container").hide();
		$("#list_container").html(TrimPath.processDOMTemplate("list_template", listjson));
		setOddEvenLine("list_container", listType);// 设置奇偶行效果
		if (typeof(listjson.root) == "undefined" || listjson.root.length == 0) {
			setColspan("list_table");// 设置空列表提示的跨列数
		}
		dealWith(json);
		$("#list_container").show();
		if (parent != null && listType == 0) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
		if($("#checkedIds").length > 0){
			checkIds();
		}
	};
	var showPageNo = function() {// 加载页码及翻页按钮
		var tr = $("#list_container").find("table").eq(1).find("tr");// 列表最后一行
		// 生成总记录数、每页显示条数
		if (listType == 0) {
			pageInfo = $("<td align='right' style='color:#FFF;'></td>")
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
		tr.append($("<td width='24' align='center'><img id='list_first' src='image/first_page.gif' title='首页' class='to_page'/></td>"));
		tr.append($("<td width='24' align='center'><img id='list_previous' src='image/prev_page.gif' title='上一页' class='to_page'/></td>"));
		tr.append($("<td width='24' align='center'><img id='list_next' src='image/next_page.gif' title='下一页' class='to_page'/></td>"));
		tr.append($("<td width='24' align='center'><img id='list_last' src='image/last_page.gif' title='末页' class='to_page'/></td>"));
		tr.append($("<td class='text_white'></td>").append("<span>第</span>").append(pageSelect).append($("<span>/" + pageNumber + "</span>")).append("<span>页</span>"));
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
		// 如是弹出层选择页面，则设置默认选中
		if($("#trimedName").size() > 0){
			defaultSelected();
		}
	};
	var showFirstPage = function() {// 首页
		if($("#checkedIds").length > 0){updateCheckedIds();}
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
		if($("#checkedIds").length > 0){updateCheckedIds();}
		if (pageNumber != 0) {
			if (currentPageNo != 1) {
				currentPageNo--;
				$("#list_pagenumber").attr("value", currentPageNo);
	//			if (currentPageNo % pageBackNumber == 0) {
				if (currentPageNo < startPageNumber) {
					$("#search").attr("action", nameSpace + "/toPage.action");
					getData();
				} else {
	//				loadTemplate(data[currentPageNo % pageBackNumber - 1]);
					loadTemplate(data[currentPageNo - startPageNumber]);
					showPageNo();
				}
			}
		}
	};
	var showNextPage = function() {// 下页
		if($("#checkedIds").length > 0){updateCheckedIds();}
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
		if($("#checkedIds").length > 0){updateCheckedIds();}
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
		if($("#checkedIds").length > 0){updateCheckedIds();}
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
	
	var Highch = function() {
		$.ajax({
			url: nameSpace + "/assist.action",
 			type: "post",
 			dataType: "json",
 			success: function(result) {
 				if (result.errorInfo == null || result.errorInfo == "") {
 					initHighcharts(result);
 				} else {
 					alert(result.errorInfo);
 				}
 				return false;
 			}	
		})
	};
	
	var showSimpleSearch = function() {// 检索
		var keyword = $("#keyword").val().trim();
		$("#keyword").val(keyword);
		$("#search").attr("action", nameSpace + "/simpleSearch.action");
		if($("#checkedIds").length > 0){//检索时要判断是否有checkedIds，如果有要清空
			$("#checkedIds").attr("value", "");
		};
		getData();
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
	var optionssearch = {
		dataType: "json",
		success: function (result) {
			if (result.errorInfo == null || result.errorInfo == "") {
				if (result.totalRows > 50) {
					if(nameSpace.indexOf("project") != -1){
						Highch();
					}					
					$("#container").show();
				} else {
					$("#container").hide();
				}
				json = result;
				showData();
			} else {
				alert(result.errorInfo);
			}
			$("#list_first, #list_previous, #list_next, #list_last").attr("disabled", false);
		}
	};
	var initHighcharts = function(json) {
		$('#container').highcharts({
			chart : {
				type : 'column'
			},
			title : {
				text : null
			},
	        subtitle: {
	            text: json.xTitle + '分布情况（前十条）'
	        },
			xAxis : {
				categories : json.lTitle,
				title: {
					text: json.xTitle,
				}
			},
			yAxis : {
				min : 0,
				title : {
					text : json.yTitle
				}
			},
			legend: {
	            enabled: false
	        },
			tooltip: {
                valueSuffix: '　条'
            },
			plotOptions : {
				series : {
					pointWidth : 50
				},
				column : {
					pointPadding : 0.2,
					borderWidth : 0
				}
			},
			series : [ {
				name : json.yTitle,
				data : json.lData
			} ]
		});
	}	
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
			$("#list_first, #list_previous, #list_next, #list_last").attr("disabled", false);
		}
	};
	var getNumberLength = function(number) {// 根据数字的位数，确定输入框的宽度
		var strNumber = "" + number;
		var length = strNumber.length + 1;
		return 7 * length + "px";
	};
	var initPageShow = function(init) {
		// 参数预处理
		if(typeof(init.dealWith) == 'undefined'){
			dealWith = function(){};
		}else{
			dealWith = init.dealWith;
		}
		if(typeof(init.dealWithBefore) == 'undefined'){
			dealWithBefore = function(){};
		}else{
			dealWithBefore = init.dealWithBefore;
		}
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
		$("#advSearch").submit(function() {
			$(this).ajaxSubmit(optionssearch);
			return false;
		});
		$("#search").submit(function() {// 提交ajax请求，返回列表数据
			$(this).ajaxSubmit(optionssearch);
			return false;
		});

		$("#list").submit(function() {
			$(this).ajaxSubmit(optionslist);
			return false;
		});
		getData();
		$("#check").live("click", function() {// 全选
			checkAll(this.checked, "entityIds");
		});
		$("#list_add").live("click", function() {// 添加按钮
			var url = basePath + nameSpace + "/toAdd.action?type=1"
			if(viewParam != null && viewParam.listflag != undefined){
				url += "&listflag=" + viewParam.listflag;
			}
			window.location.href = url;
			return false;
		});
		$("#list_add_result").live("click", function() {// 项目中用到
			var url = basePath + nameSpace + "/toAddResult.action?type=1"
			if(viewParam != null && viewParam.listflag != undefined){
				url += "&listflag=" + viewParam.listflag;
			}
			window.location.href = url;
			return false;
		});
		$("#list_delete").live("click", function() {// 删除按钮
			var cnt = count("entityIds");
			if (cnt == 0) {
				alert("请选择要删除的记录！");
			} else {
				if (confirm("您确定要删除选中的记录吗？")) {
					if($("#checkedIds").length > 0){
						$("#checkedIds").attr("value", "");
					};
					$("#type").attr("value", 1);
					$("#pagenumber").attr("value", $("#list_goto").val());
					$("#list").attr("action", nameSpace + "/delete.action");
					$("#list").submit();
				}
			}
			return false;
		});
		
		$("#list_funding").live("click", function() {// 批量拨款按钮
			var cnt = count("entityIds");
			if (cnt == 0) {
				alert("请选择要拨款的记录！");
			} else {
				if (confirm("您确定要对选中的记录拨款吗？")) {
					if($("#checkedIds").length > 0){
						$("#checkedIds").attr("value", "");
					};
					$("#type").attr("value", 1);
					$("#pagenumber").attr("value", $("#list_goto").val());
					$("#list").attr("action", nameSpace + "/funding.action");
					$("#list").submit();
				}
			}
			return false;
		});
		$(".link1").live("click", function(){// 点击进入查看页面
			//var url = basePath + nameSpace + "/toView.action?entityId=" + this.id + "&pageNumber=" + $("#list_pagenumber").val();
			var url = "";	//不再需要页码
			if(this.type == 8 || this.type == 10){//结项评审或者申请评审列表进入查看页面url
				url = basePath + nameSpace + "/toViewReview.action?entityId=" + this.id;	//不再需要页码
			}else{
				url = basePath + nameSpace + "/toView.action?entityId=" + this.id;	//不再需要页码
			}
			url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
			url += (selectedTab != null) ? "&selectedTab=" + selectedTab : "";
			if(viewParam != null && viewParam.listflag != undefined){
				url += "&listflag=" + viewParam.listflag;
			}
			window.location.href = url;
			return false;
		});
		$("#list_button_query").bind("click", function() {// 初级检索
			showSimpleSearch();
			return false;
		});
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
		if($("#advFlag").val() == 1){
			$("#adv_search").show();
			$("#simple_search").hide();
			$("#list_search_hide").attr("value", "隐藏条件");
		} else {
			$("#adv_search").hide();
			$("#simple_search").show();
			$("#list_search_more").attr("value", "更多条件");
		}
		$("#list_button_advSearch").live("click", function() {
			if (parent != null && listType == 0) {
				parent.loading_flag = true;
				setTimeout("parent.showLoading();", parent.loading_lag_time);
			}
			$("#advSearch").attr("action", nameSpace + "/advSearch.action");
			$("#advSearch").submit();
			return false;
		});
		$("#list_changepagesize").live("change", function() {// 改变每页显示条目数量
			showPageSize();
			return false;
		});
		$("#list_first").live("click", function() {// 首页
			showFirstPage();
			return false;
		});
		$("#list_previous").live("click", function() {// 上一页
			showPreviousPage();
			return false;
		});
		$("#list_next").live("click", function() {// 下一页
			showNextPage();
			return false;
		});
		$("#list_last").live("click", function() {// 末页
			showLastPage();
			return false;
		});
		$("#keyword").live("keypress", function(event) {// 给检索添加键盘事件，回车提交
			var keyCode = event.which;
			if (keyCode == 13) {
				showSimpleSearch();
				return false;
			} else {
				return true;
			}
		});
		
		
		$("#list_goto").live("click", function(){// 点击选中页码，给换页绑定回车及弹出事件
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
		
		// 点击单个的checkbox时，维护全选框的状态
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
	
	/**
	 * 将本页所选id和之前保存的id拼接并放入checkedIds隐藏域。
	 * 在离开本页前需执行此方法。
	 * @memberOf {TypeName} 
	 */
	var updateCheckedIds = function(){
		var checkedIds = $("#checkedIds").val();
		$("input[name='entityIds']").each(function() {
			var idx = checkedIds.indexOf(this.value);
			if (this.checked && (idx == -1)) {// 选择不在隐藏域保存的id
				checkedIds += $(this).val() + ",";
			} else if(!this.checked && (idx != -1)) {// 取消选择隐藏域中的id
				var left = checkedIds.substring(0, idx);
				var right = checkedIds.substring(idx);
				checkedIds = left + right.substring(right.indexOf(",") + 1);
			}
		});
		$("#checkedIds").attr("value",checkedIds);
	};
	
	/**
	 * 翻页（loadTemplate）之后将已选项勾选
	 * @memberOf {TypeName} 
	 */
	var checkIds = function(){
		var checkedIds = $("#checkedIds").val();
		$("input[name='entityIds']").each(function() {
			if (checkedIds.indexOf(this.value) != -1) {
				this.checked = true;
			}
		});
	};
	
	module.exports = {
		pageShow: function(init) {initPageShow(init);},
		getData: function() {getData();},
		// 提供给一些模块的添加和删除按钮重写
		selectedTab: selectedTab,
		viewParam: viewParam
	};
	
});