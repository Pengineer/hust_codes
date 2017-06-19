define(function(require, exports, module) {
	var view = require('javascript/view');
	var viewUnit = require('javascript/unit/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	var nameSpace = $("#namespace").val();
//	var nameSpace = "unit/institute";
	var showDetails = function(result) {
		image("image");
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr("value", result.institute.id);
			$("#entityIds").attr("value", result.institute.id);
			$("div[id^='view_container']").hide();
			$("#view_container_common").html(TrimPath.processDOMTemplate("view_template_common", result));
			$("#view_container_basic").html(TrimPath.processDOMTemplate("view_template_basic", result));
			$("#view_container_academic").html(TrimPath.processDOMTemplate("view_template_academic", result));
			$("#view_container_contact").html(TrimPath.processDOMTemplate("view_template_contact", result));
			$("#view_container_project").html(TrimPath.processDOMTemplate("view_template_project", result));
			$("#view_container_product").html(TrimPath.processDOMTemplate("view_template_product", result));
			$("#view_container_person").html(TrimPath.processDOMTemplate("view_template_person", result));
			$("#view_container_funding").html(TrimPath.processDOMTemplate("view_template_funding", result));
			$("#view_container_report").html(TrimPath.processDOMTemplate("view_template_report", result));
			$("#view_container_assessment").html(TrimPath.processDOMTemplate("view_template_assessment", result));
			setOddEvenLine("list_doctorial", 0);
			setOddEvenLine("list_discipline", 0);
			setOddEvenLine("list_project", 0);
			setOddEvenLine("list_product", 0);
			setOddEvenLine("list_officer", 0);
			setOddEvenLine("list_teacher", 0);
			setOddEvenLine("list_student", 0);
			setOddEvenLine("list_funding", 0);
			showNumber();
			if (typeof(result.doctorial) == "undefined" || result.doctorial == 0) {
				setColspan("list_doctorial");
			}
			if (typeof(result.discipline) == "undefined" || result.discipline == 0) {
				setColspan("list_discipline");
			}
			if (typeof(result.institute_project) == "undefined" || result.institute_project == 0) {
				setColspan("list_project");
			}
			if (typeof(result.institute_product) == "undefined" || result.institute_product == 0) {
				setColspan("list_product");
			}
			if (typeof(result.officer) == "undefined" || result.officer == 0) {
				setColspan("list_officer");
			}
			if (typeof(result.teacher) == "undefined" || result.teacher == 0) {
				setColspan("list_teacher");
			}
			if (typeof(result.student) == "undefined" || result.student == 0) {
				setColspan("list_student");
			}
			if (typeof(result.funding) == "undefined" || result.funding == 0) {
				setColspan("list_funding");
			}
			$("div[id^='view_container']").show();
			$("#tabcontent").show();
			initImage("image",$(".link_bar").eq(0).attr("id"));
			view.inittabs();
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
				src : "account/institute/main/extIfToAdd.action",
				inData : {data : {"typeName" : "研究机构主账号", "belongId" : $("#entityId").val(), "belongName" : $("#unitName").html()}},
				callBack : function(result){
					view.show(nameSpace, showDetails);
				}
			});
			return false;
		});
	};
});
