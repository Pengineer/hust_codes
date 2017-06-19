/**
 * @author 刘雅琴、肖雅
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	
	var init = function() {
		$(function() {
			//申请
			$("#apply").live("click", function() {
				var type = $('input:radio:checked').val();
				var url ="";
				if (type == '01') {//一般项目
					url = basePath + "project/general/application/apply/toAdd.action"
				} else if (type == '02') {//基地项目
					url = basePath +  "project/instp/application/apply/toAdd.action"
				} else if (type == '03') {//后期资助项目
					url = basePath +  "project/post/application/apply/toAdd.action"
				} else if (type == '04') {//重大攻关项目
					url = basePath +  "project/key/application/apply/toAdd.action"
				} else if (type == '05') {//委托应急课题
					url =  basePath + "project/entrust/application/apply/toAdd.action"
				} else {
					alert("请选择项目类型！");
					return false;
				}
				window.parent.frames["main"].location.href = url;
				thisPopLayer.destroy();
			});
			
		});
	};
	
	//初始化onclick事件
	exports.init = function() {
		if ($("#genappStatus").val() == 0) {
			document.getElementById("general").disabled=true;
			$("#general").next().css('color','gray');
		}
		if ($("#insappStatus").val() == 0) {
			$("#instp").next().css('color','gray');
			document.getElementById("instp").disabled=true;
		}
		if ($("#posappStatus").val() == 0) {
			$("#post").next().css('color','gray');
			document.getElementById("post").disabled=true;
		}
		if ($("#keyappStatus").val() == 0) {
			$("#key").next().css('color','gray');
			document.getElementById("key").disabled=true;
		}
		if ($("#entappStatus").val() == 0) {
			$("#entr").next().css('color','gray');
			document.getElementById("entr").disabled=true;
		}
		init();
	};
	
});
