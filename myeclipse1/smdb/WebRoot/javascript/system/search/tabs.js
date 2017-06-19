/**
 * 文件说明： 初始化标签页tabs 
 * @author  liujia
 */
define(function(require, exports, module) {
	/* seajs 载入依赖 */
	require('form');
	var view = require('javascript/view');
	var list = require('javascript/list');
	exports.flag = true;
	
	var optionssearch = {
			dataType: "json",
			success: function (result) {
				$("#tabs ul").html("");
				var mapp = {award:"奖励",person:"人员",product:"成果",project:"项目",agency:"机构",info:"信息"};		
				var resultName = [],i = 0;
				for(var check in result){
					resultName[i] = check;
					i ++ ;
					var temp = $('<li></li>').addClass("un_granted").appendTo($("#tabs ul"));
					$('<a>').attr("class",function(){
						return check;
					}).attr('href',"#list_container").appendTo(temp).html(function(){
						return mapp	[check];
					});
				}
				view.inittabs(1);	//初始化标签页，参数1表示默认选中第一个标签
				$("#dataType").val(function(){
					return $("#tabs li a").eq(0).attr("class");;
				});
				
				if($("#search_range_list").val() == "all"){
					$("#" + resultName[0]).attr("id","list_template");
				} else {
					$("#" + $("#search_range_list").val()).attr("id","list_template");
				}
				
				if(exports.flag){
					list.pageShow({
						"nameSpace": "system/search",
						"sortColumnId": ["sortcolumn0", "sortcolumn1", "sortcolumn2"],
						"sortColumnValue": {"sortcolumn0": 0, "sortcolumn1": 1, "sortcolumn2": 2}
					});
				}
				parent.hideLoading();
				list.getData();
				$("#container_box").show();
				$("#loading").hide();
				$("#tabs li a").each(function(){
					var templateName = $(this).attr("class");
					$(this).click(function(){
						$("#list_template").attr("id",function(){
							return $('#list_template').attr("name");
						});
						$("#" + templateName).attr("id","list_template");
						$("#dataType").val(function(){
							return templateName;
						});//动态更改列表模版
						list.getData();		
						
					});
				});
				exports.flag = false;
			}
		};
	
	var initTabs = function(){
		$("#getTabs").submit(function() {// 提交ajax请求，返回列表数据
			$(this).ajaxSubmit(optionssearch);
			return false;
		});
		$("#getTabs").submit();
	};
	
	exports.init = function(){
		parent.loading_flag = true;
		parent.showLoading();
		initTabs();
		
	}; 
	exports.getData = function(){
		$("#getTabs").submit();
		parent.loading_flag = true;
		parent.showLoading();
	}
	
});
