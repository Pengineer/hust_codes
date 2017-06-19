define(function(require, exports, module) {
	var viewPerson = require('javascript/person/view');
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	
	var nameSpace = "person/expert";
	var showDetails = function(result) {// 显示查看内容
		image("image");
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr("value", result.expert.id);
			$("#entityIds").attr("value", result.expert.id);
			Template_tool.populate(result);
			$(".value.address").each(function(){
				$(this).html($(this).html().replace(/^\s*|\n/gm,"").replace(/；$/,""));
			});
			initImage("image",$(".link_bar").eq(0).attr("id"));
			setOddEvenLine("education", 0);
			setOddEvenLine("work", 0);
			setOddEvenLine("abroad", 0);
			setOddEvenLine("project", 0);
			setOddEvenLine("product", 0);
			setOddEvenLine("bank", 0);
			if (typeof(result.person_education) == "undefined" || result.person_education == 0) {
				setColspan("list_edu");
			}
			if (typeof(result.person_work) == "undefined" || result.person_work == 0) {
				setColspan("list_work");
			}
			if (typeof(result.person_abroad) == "undefined" || result.person_abroad == 0) {
				setColspan("list_abroad");
			}
			if (typeof(result.person_project) == "undefined" || result.person_project == 0) {
				setColspan("list_proj");
			}
			if (typeof(result.person_product) == "undefined" || result.person_product == 0) {
				setColspan("list_prod");
			}
			if (typeof(result.person_bankAccount) == "undefined" || result.person_bankAccount == 0) {
				setColspan("list_bank");
			}
			showNumber();
			var td_width = 120;
			var td_height = 133;
			var img = new Image();
			img.src = $("#photo img").attr("src");
			$("#photo img").width(td_width);
			$("#photo img").load(function(){
				$(this).removeAttr("style");
				if (img.width / img.height > td_width / td_height){
					$(this).width(td_width);
				} else {
					$(this).height(td_height);
				}
			});
		} else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	exports.init = function() {
		viewPerson.init(nameSpace, showDetails);//公共部分
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		$(".linkDir").live("click", function() {
			popPerson(this.id, 7);
			return false;
		});
		$(".linkAcc").live("click", function() {
			popAccount(this.id);
			return false;
		});
		$(".linkPcc").live("click", function() {
			popPassport(this.id);
			return false;
		});
		$("#view_addAccount").live("click", function(){
			popAccountAdd({
				src : "account/expert/extIfToAdd.action?belongId=" + $("#entityId").val(),
				inData : {data : {"typeName" : "外部专家账号", "belongId" : $("#entityId").val(), "belongName" : $("#personName").html()}},
				callBack : function(result){
					view.show(nameSpace, showDetails);
				}
			});
			return false;
		});
	};
});
