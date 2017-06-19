define(function(require, exports, module) {
	var viewUnit = require('javascript/unit/view');
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	var nameSpace = $("#namespace").val();
//	var nameSpace = "unit/department";
	var showDetails = function(result){
		image("image");
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr(("value"),result.department.id);
			$("#entityIds").attr("value",result.department.id);
			$("div[id^='view_container']").hide();
			$("#view_container_common").html(TrimPath.processDOMTemplate("view_template_common", result));
			$("#view_container_contact").html(TrimPath.processDOMTemplate("view_template_contact", result));
			$("#view_container_academic").html(TrimPath.processDOMTemplate("view_template_academic", result));
			setOddEvenLine("list_doctorial", 0);
			setOddEvenLine("list_discipline", 0);
			if (typeof(result.doctorial) == "undefined" || result.doctorial == 0) {
				setColspan("list_doctorial");
			}
			if (typeof(result.discipline) == "undefined" || result.discipline == 0) {
				setColspan("list_discipline");
			}
			$("div[id^='view_container']").show();
			$("#tabcontent").show();
			initImage("image",$(".link_bar").eq(0).attr("id"));

			$(".value.address").each(function(){
				$(this).html($(this).html().replace(/^\s*|\n/gm,"").replace(/；$/,""));
			});
		}else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	exports.init = function() {
		viewUnit.init(nameSpace, showDetails);//公共部分
		$("#view_addAccount").live("click", function(){
			popAccountAdd({
				src : "account/department/main/extIfToAdd.action",
				inData : {data : {"typeName" : "高校院系主账号", "belongId" : $("#entityId").val(), "belongName" : $("#unitName").html()}},
				callBack : function(result){
					view.show(nameSpace, showDetails);
				}
			});
			return false;
		});
	};
});
