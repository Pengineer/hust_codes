/**
 * 成果列表
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('form');
	
	//检索表单异步提交
	var optionssearch = {
		dataType: "json",
		success: function (result) {
			if (result.errorInfo == null || result.errorInfo == "") {
				showData(result);
			} else {
				alert(result.errorInfo);
			}
		}
	};

	//列表表单异步提交
	var optionslist = {
		dataType: "json",
		success: function (result) {
			if (result == undefined || result == null) {// 未知的错误异常
				alert("未知的错误异常");
			} else if (result.errorInfo == null || result.errorInfo == "") {
				$("#search").submit();
			} else {// 已知的错误异常
				alert(result.errorInfo);
			}
		}
	};

	//显示数据
	var showData = function(result) {
		$("#list_container").html(TrimPath.processDOMTemplate("list_template", formatJson(result)));
		setOddEvenLine("list_table", 0);
		if (typeof(result.laData) == "undefined" || result.laData == 0) {
			setColspan("list_table");
		}
		$("#list_container").show();
	};

	//重构列表数据格式
	var formatJson = function(result){
		var listjson = {};
		listjson = $.parseJSON('{"root": []}');
		for (var i = 0; i < result.laData.length; i++) {
			var root = {"laData": [], "num": 0};
			root.laData = result.laData[i];
			root.num = i + 1;
			listjson.root[i] = root;
		}
		return listjson;
	};
	
	var init = function(){
		//高校弹出层
		$(".linkUni").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		
		//作者弹出层
		$(".linkAuthor").live("click", function() {
			popPerson(this.id, 7);
			return false;
		});
		
		//查看成果详情
		$(".link1").live("click", function(){
			var productTypes = {'paper' : 1, 'book' : 2, 'consultation' : 3, 'electronic' : 4, 'patent' : 5, 'otherProduct' : 6};
			if(this.name in productTypes) {
				location.href = basePath + "product/" + this.name + 
					"/toView.action?entityId=" + this.id + "&productflag=1";
			}
			return false;
		});
		
		//绑定列表表单提交处理函数
		$("#list").submit(function() { 
	    	$(this).ajaxSubmit(optionslist); 
	    	return false; 
		});
		
		//绑定检索表单提交处理函数
		$("#search").submit(function() { 
	    	$(this).ajaxSubmit(optionssearch); 
	    	return false; 
		});
		
		//提交表单
		$("#search").submit();
	};
	
	exports.init = function() {
		init();
	};
});
