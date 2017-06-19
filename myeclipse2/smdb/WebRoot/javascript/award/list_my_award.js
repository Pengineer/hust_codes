/**
 * 用于我的奖励列表
 * @author 余潜玉
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('form');
	
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
		setOddEvenLine("list_table", 1);
		if (typeof(result.laData) == "undefined" || result.laData == 0) {
			setColspan("list_table");
		}
		$("#list_container").show();
		$(".session").each(function(){
			$(this).html(Num2Chinese(parseInt($(this).html())));
		});
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
	var init = function(){
		$(".viewapplicant").live("click", function() {//查看申请人详细信息
			popPerson(this.id, 7);
			return false;
		});
		$(".viewuniversity").live("click", function() {//查看所属高校详细信息
			popAgency(this.id, 1);
			return false;
		});
		$(".link1").live("click", function() {//查看详情
			var listflag = $("#listflag").val();
			var selectedTab = 'awarded';
			var url = basePath + 'award/toViewMyAward.action?entityId=' + this.id + "&listflag=" + listflag + "&selectedTab=" + selectedTab; 
			url += "&subType=" + this.name ;
			window.location.href = url;
			return false;
		});
		$("#search").submit(function() { 
	    	$(this).ajaxSubmit(optionssearch); 
	    	return false; 
		});
		$("#search").submit();
	};
	
	exports.initAward = function() {
		init();
	};
});






