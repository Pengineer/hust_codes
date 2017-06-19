/**
 * @author 刘雅琴、肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('form');
	var list = require('javascript/list');
	
	var init = function() {
		$(function() {
			//申报
			$("#list_apply").live("click", function() {
				popAddProject({
					src : "project/toAdd.action",
					callBack : function(layer){
						layer.destroy();
					}
				});
				return false;
			});
			//查看依托高校
			$(".view_university").live("click", function() {
				popAgency(this.id, 1);
				return false;
			});
			
			//查看负责人
			$(".view_director").live("click", function() {
				popPerson(this.id, 7);
				return false;
			});
			
			//查看项目
			$(".link1").live("click", function(){
				var url = basePath + "project/toViewMyProject.action?entityId=" + this.id;
				url += (this.type) ? "&listType=" + this.type : "";//(项目列表类型先如是判别)
				url += "&selectedTab=granted";
				url += "&projectType=" + this.name;
				window.location.href = url;
				return false;
			});
			
			//查看列表
			$("#search").submit(function() { 
		    	$(this).ajaxSubmit(optionssearch); 
		    	return false; 
			});
			
			$("#search").submit();
		});
	};
	
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

	var showData = function(result) {
		$("#list_container").html(TrimPath.processDOMTemplate("list_template", formatJson(result)));
		setOddEvenLine("list_table", 0);
		if (typeof(result.laData) == "undefined" || result.laData == 0) {
			setColspan("list_table");
		}
		$("#list_container").show();
	};

	var formatJson = function(result){
		var listjson={};
		listjson = $.parseJSON('{"root": []}');
		for (var i = 0; i < result.laData.length; i++) {
			var root = {"laData": [], "num": 0};
			root.laData = result.laData[i];
			root.num = i+1;
			listjson.root[i]=root;
		}
		return listjson;
	};
	
	//初始化onclick事件
	exports.init = function() {
		init();
	};
	
});
