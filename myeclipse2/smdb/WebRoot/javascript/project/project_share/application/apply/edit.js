define(function(require, exports, module) {
	require('form');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('uploadify');
	require('uploadify-ext');
	var validApp = require('javascript/project/project_share/application/validate');
	
	var flag = 99999;

	/**
	 * 提交前检查是否添加了项目成员及负责人
	 */
	var checkMember = function(){
		//检查是否添加了项目成员
		var allTable = $("#member .table_valid");
		if(!allTable){
			return false;
		}
		if(allTable.length<1){
			alert("请添加项目成员");
			return false;
		}
		var director = 0;
		//检查是否添加了项目负责人
		$("[name$='isDirector']").each(function(index){
			if($(this).val() == 1){
				director++;
			}
		});
		if(director == 0){
			alert("请选择项目负责人");
			return false;
//		}else if(director >1){
//			alert("只能选择一个项目负责人");
//			return false;
//		}else{
//			var $directorYes = $("[name$='isDirector'][value=1]:eq(0)");
//			$here = $directorYes.parent().parent().parent();
//			var memType = $("[name$='memberType']", $here).val();
//			if (memType == '2') {
//				alert("一般项目负责人只能是教师或学生");
//				return false;
//			}
		}
		return true;
	};
	
	/**
	 * 提交前按table重新分配下标
	 */
	var memberNum = function(projectType){
		$("#member table").each(function(key, value){
			$("input, select", $(value)).each(function(key1, value1){
				value1.name = value1.name.replace(/\[.*\]/, "[" + (key) + "]");
			});
		});
	 	validApp.valid_member(projectType);//分配下标后重新设置校验，否则修改完成时会出错
	};
	
	/**
	 * 添加一个相关人员
	 */
	var addTable = function(obj ,tableId, projectType){
		var onthis = $(obj).parent().parent().parent().parent();
		var addTable = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>").insertAfter(onthis);
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag++;
		validApp.valid_member(projectType);
		sortNum();
	};
	
	var addLastTable = function(topId, tableId, projectType) {
		$("#" + topId).append("<table id='newTable' width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
		var addTable = $("#newTable");
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag++;
		validApp.valid_member(projectType);
		sortNum();
		addTable.removeAttr("id");
	};
	
	/**
	 * 判断其他成果类别输入框是否要显示
	 * @param {Object} isChecked	是否选择其他成果
	 * @param {Object} name	点中的多选框的值
	 */
	function showOtherProductTypeOrNot(isChecked, value){
		if(value == undefined || typeof(value) == 'undefined'){
			if(isChecked){
				$("#productTypeOtherSpan").show();
			}else{
				$("#productTypeOtherSpan").hide();
				$("#profuctTypeOtherTd").html("");
			}
		}else{
			if(isChecked && value == '其他'){
				$("#productTypeOtherSpan").show();
			}else{
				$("#productTypeOtherSpan").hide();
				$("#profuctTypeOtherTd").html("");
			}
		}
		validApp.valid_apply();
	};

	//上传申请评审书
	var uploadFileResult = function(data, type, layer){
		var appFileId = $("#fsUploadProgress_end").find("input[name='fileIds']").val();
		var dis = {
			"appFileId":appFileId
		};
		layer.callBack(dis, type);
		layer.destroy();
	};
	
	//自动编号
	var sortNum = function() {
	 	var spans = getElementsByTagTitle("span","memberSpan");
	 	for(var iLoop = 0; iLoop < spans.length; iLoop++) {
	 		spans[iLoop].innerHTML=iLoop+1;
	 	}
	};
	
	var initUpload = function() {
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
		});
	};
	
	module.exports = {
		initUpload: function() {initUpload()},
		uploadFileResult: function(data, type, layer){uploadFileResult(data, type, layer)},
		checkMember: function(){return checkMember()}, 
		memberNum: function(projectType){memberNum(projectType)}, 
		addTable: function(obj ,tableId, projectType){addTable(obj ,tableId, projectType)},
		addLastTable: function(topId, tableId, projectType){addLastTable(topId, tableId, projectType)},
		showOtherProductTypeOrNot: function(isChecked, value){showOtherProductTypeOrNot(isChecked, value)}
	};
});