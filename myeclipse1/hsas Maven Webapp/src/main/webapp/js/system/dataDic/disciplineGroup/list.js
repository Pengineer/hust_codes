/**
 * @author liujia
 * @date 2015/8/14
 * @descrioption 学科分组列表
 */
define(function(require, exports, module){
	var list = require("list");
	var nameSpace = "system/dataDic/disciplineGroup/";
	require("form");
	//初始化函数，初始化列表
	exports.init = function(){
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
		});
	};
	
	//添加学科代码
	$("body").on("click", "#list_add_displineGroup", function(){
		dialog({
			title: '添加学科分组',
			width:400,
		    content: $(".form_addDisplineGroup")[0],
		    okValue: '确定',
		    ok: function () {
		        this.title('提交中…');
		        return false;
		    },
		    cancelValue: '取消',
		    cancel: function () {}
		}).showModal();
	})
	//重新分组
	$("body").on("click", "#list_reGrouping", function(){
		dialog({
			title: '重新分组',
			width:400,
		    content: $(".form_reGrouping")[0],
		    okValue: '确定',
		    ok: function () {
		        this.title('提交中…');
		        return false;
		    },
		    cancelValue: '取消',
		    cancel: function () {}
		}).showModal();
	})

});

