define(function(require, exports, module) {
	var viewUnit = require('javascript/unit/view');
	require('tool/poplayer/js/pop');
	var view = require('javascript/view');
	require('pop-self');
	require('javascript/template_tool');
	var nameSpace = $("#namespace").val();
//	var nameSpace = "unit/agency/province";
	var showDetails = function(result){
		image("image");
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr(("value"),result.agency.id);
			$("#entityIds").attr("value",result.agency.id);
			$("div[id^='view_container']").hide();
			$("#view_container_common").html(TrimPath.processDOMTemplate("view_template_common", result));
			$("#view_container_basic").html(TrimPath.processDOMTemplate("view_template_basic", result));
			$("#view_container_social").html(TrimPath.processDOMTemplate("view_template_social", result));
			$("#view_container_financial").html(TrimPath.processDOMTemplate("view_template_financial", result));
			setOddEvenLine("bank", 0);
			if(result.agency.type == 3 || result.agency.type == 4){
				$("#view_container_department").html(TrimPath.processDOMTemplate("view_template_department", result));
				$("#view_container_academic").html(TrimPath.processDOMTemplate("view_template_academic", result));
				setOddEvenLine("list_department", 0);
				setOddEvenLine("list_doctorial", 0);
				setOddEvenLine("list_discipline", 0);
				if (typeof(result.deptList) == "undefined" || result.deptList == 0) {
					setColspan("list_department");
				}
				if (typeof(result.doctorial) == "undefined" || result.doctorial == 0) {
					setColspan("list_doctorial");
				}
				if (typeof(result.discipline) == "undefined" || result.discipline == 0) {
					setColspan("list_discipline");
				}
			}
			if (typeof(result.bankList) == "undefined" || result.bankList == 0) {
				setColspan("list_bank");
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
				src : "account/province/main/extIfToAdd.action",
				inData : {data : {"typeName" : "省级主账号", "belongId" : $("#entityId").val(), "belongName" : $("#unitName").html()}},
				callBack : function(result){
					view.show(nameSpace, showDetails);
				}
			});
			return false;
		});
	};
});
