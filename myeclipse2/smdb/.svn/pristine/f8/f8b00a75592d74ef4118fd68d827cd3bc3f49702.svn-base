define(function(require, exports, module) {
	var view = require('javascript/view');
	var project = require('javascript/project/project_share/project');
	var viewProject = require('javascript/project/project_share/view');
//	var viewProduct = require('javascript/product/extIf/view');
	
	var projectType = "entrust";
	
	var showProjectDetails = function(result) {
		image("image");
		if (result.errorInfo == null || result.errorInfo == "") {
//			$(["endimage", "varimage"]).each(function(){
//				image(this);
//			});
			$("#entityId").attr("value", result.application.id);//刷新申请id
			$("#view_base").html(TrimPath.processDOMTemplate("view_base_template", result));//刷新基本信息
			$("#view_apply").html(TrimPath.processDOMTemplate("view_apply_template", result));//刷新申请信息
			$("#view_related").html(TrimPath.processDOMTemplate("view_related_template", result));//刷新相关成员信息
			setOddEvenLine("list_entrust_member", 0);
			if (result.memberFlag == 1 && (typeof(result.memberList) == "undefined" || result.memberList == 0)) {
				setColspan("list_entrust_member");
			}
			if (!(result.application.finalAuditStatus == 3 && result.application.finalAuditResult == 2)) {//未立项
				$("#appFormEntityid").attr("value", result.application.id);//刷新id
				$("#projectid").attr("value", "");//刷新id
				$("#endFileProjectid").attr("value", "");//刷新id
				$("#endFormProjectid").attr("value", "");//刷新id
				$("#endImportedFormProjectid").attr("value", "");//刷新id
				$("#view_established").html("");
				$("#view_end").html("");
				$("#view_var").html("");
				$("#view_fund").html("");
				$("#view_relprod").html("");
				$(".un_granted").each(function(){
					$(this).hide();
				});
			}
			else {//已立项
				$("#projectid").attr("value", result.granted.id);//刷新id
				$("#appFormEntityid").attr("value", result.application.id);//刷新id
				$("#endFileProjectid").attr("value", result.granted.id);//刷新id
				$("#endFormProjectid").attr("value", result.granted.id);//刷新id
				$("#endImportedFormProjectid").attr("value", result.granted.id);//刷新id
				$(".un_granted").each(function(){
					$(this).show();
				});
				$("#view_established").html(TrimPath.processDOMTemplate("view_established_template", result));//刷新立项信息
				$("#view_end").html(TrimPath.processDOMTemplate("view_end_template", result));
				$("#view_var").html(TrimPath.processDOMTemplate("view_var_template", result));
				$("#view_fund").html(TrimPath.processDOMTemplate("view_fund_template", result));
				//操作校验参数
				$("#endPassAlready").attr("value",result.endPassAlready);
				$("#endPending").attr("value",result.endPending);
				$("#varPending").attr("value",result.varPending);
				if (result.varList != null && result.varList.length > 0) {
					$("#currentStop").attr("value",result.varList[0].stop);
					$("#currentWithDraw").attr("value",result.varList[0].withdraw);
				}
				setOddEvenLine("project_found", 0);
				if(result.varList.length>0){
					for(var i = 0; i < result.varList.length; i++){
						setOddEvenLine("list_entrust_variation"+i, 0);
					}
				}
				if (typeof(result.fundList) == "undefined" || result.fundList == 0) {
					setColspan("project_found");
				}
				//相关成果  
				$("#view_relprod").html(TrimPath.processDOMTemplate("view_relprod_template", result));
					setOddEvenLine("view_relprod", 0);
					if(typeof(result.relProdInfo[0]) == "undefined" || result.relProdInfo[0].productSize == 0){
						setColspan("list_table_rel");
				};
				//结项成果
				$([result.endProdInfo]).each(function(index1){
					var ids = ["list_table_end_"]
					$(this).each(function(index2){
						setOddEvenLine(ids[index1] + index2, 0);
						if(this.productSize == 0) {
							setColspan(ids[index1] + index2);
						}
					})
				});
				showListNumber();
//				$(["endimage", "varimage"]).each(function(){
//					initImage(this, $(".link_bar").eq(0).attr("id"));
//				});
				viewProject.showAnnMidEndVarNumber();
				//获取结项证书显示信息
				(function(){
					certificateInfo = [];
					$([(result.subTypeNameNew) ? result.subTypeNameNew : "", 
						(result.granted && result.granted.name) ? result.granted.name : "", 
						(result.granted && result.granted.applicantName) ? result.granted.applicantName : "",  
						result.endList,                                                 
						(result.granted && result.granted.number) ? result.granted.number : "", 
						result.endList
					]).each(function(){
						certificateInfo.push(this);
					});
				})();
				$("#view_established").show();
				$("#view_end").show();
				$("#view_var").show();
				$("#view_fund").show();
				$("#view_relprod").show();
			}
			initImage("image", $(".link_bar").eq(0).attr("id"));
			viewProject.showDirectorNumber();
			view.inittabs($("#selectedTab").val(), 1);
			project.director();//在页面加载前生成a标签，用于显示负责人信息
			project.varDirector();//在页面加载前生成a标签，用于显示负责人信息
			$("#view_base").show();
			$("#view_apply").show();
			$("#view_established").show();
			$("#view_related").show();
			$("#tabcontent").show();
		}else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	exports.showProjectDetails = function(result) {
		image("image");
		if (result.errorInfo == null || result.errorInfo == "") {
//			$(["endimage", "varimage"]).each(function(){
//				image(this);
//			});
			$("#entityId").attr("value", result.application.id);//刷新申请id
			$("#view_base").html(TrimPath.processDOMTemplate("view_base_template", result));//刷新基本信息
			$("#view_apply").html(TrimPath.processDOMTemplate("view_apply_template", result));//刷新申请信息
			$("#view_related").html(TrimPath.processDOMTemplate("view_related_template", result));//刷新相关成员信息
			setOddEvenLine("list_entrust_member", 0);
			if (result.memberFlag == 1 && (typeof(result.memberList) == "undefined" || result.memberList == 0)) {
				setColspan("list_entrust_member");
			}
			if (!(result.application.finalAuditStatus == 3 && result.application.finalAuditResult == 2)) {//未立项
				$("#projectid").attr("value", "");//刷新id
				$("#endFileProjectid").attr("value", "");//刷新id
				$("#endFormProjectid").attr("value", "");//刷新id
				$("#view_established").html("");
				$("#view_end").html("");
				$("#view_var").html("");
				$("#view_fund").html("");
				$("#view_relprod").html("");
				$(".un_granted").each(function(){
					$(this).hide();
				});
			}
			else {//已立项
				$("#projectid").attr("value", result.granted.id);//刷新id
				$("#endFileProjectid").attr("value", result.granted.id);//刷新id
				$("#endFormProjectid").attr("value", result.granted.id);//刷新id
				$(".un_granted").each(function(){
					$(this).show();
				});
				$("#view_established").html(TrimPath.processDOMTemplate("view_established_template", result));//刷新立项信息
				$("#view_end").html(TrimPath.processDOMTemplate("view_end_template", result));
				$("#view_var").html(TrimPath.processDOMTemplate("view_var_template", result));
				$("#view_fund").html(TrimPath.processDOMTemplate("view_fund_template", result));
				//操作校验参数
				$("#endPassAlready").attr("value",result.endPassAlready);
				$("#endPending").attr("value",result.endPending);
				$("#varPending").attr("value",result.varPending);
				if (result.varList != null && result.varList.length > 0) {
					$("#currentStop").attr("value",result.varList[0].stop);
					$("#currentWithDraw").attr("value",result.varList[0].withdraw);
				}
				setOddEvenLine("project_found", 0);
				if(result.varList.length>0){
					for(var i = 0; i < result.varList.length; i++){
						setOddEvenLine("list_entrust_variation"+i, 0);
					}
				}
				if (typeof(result.fundList) == "undefined" || result.fundList == 0) {
					setColspan("project_found");
				}
				//相关成果  
				$("#view_relprod").html(TrimPath.processDOMTemplate("view_relprod_template", result));
					setOddEvenLine("view_relprod", 0);
					if(typeof(result.relProdInfo[0]) == "undefined" || result.relProdInfo[0].productSize == 0){
						setColspan("list_table_rel");
				};
				//结项成果
				$([result.endProdInfo]).each(function(index1){
					var ids = ["list_table_end_"]
					$(this).each(function(index2){
						setOddEvenLine(ids[index1] + index2, 0);
						if(this.productSize == 0) {
							setColspan(ids[index1] + index2);
						}
					})
				});
				showListNumber();
//				$(["endimage", "varimage"]).each(function(){
//					initImage(this, $(".link_bar").eq(0).attr("id"));
//				});
				viewProject.showAnnMidEndVarNumber();
				//获取结项证书显示信息
				(function(){
					certificateInfo = [];
					$([(result.subTypeNameNew) ? result.subTypeNameNew : "", 
						(result.granted && result.granted.name) ? result.granted.name : "", 
						(result.granted && result.granted.applicantName) ? result.granted.applicantName : "",  
						result.endList,                                                 
						(result.granted && result.granted.number) ? result.granted.number : "", 
						result.endList
					]).each(function(){
						certificateInfo.push(this);
					});
				})();
				$("#view_established").show();
				$("#view_end").show();
				$("#view_var").show();
				$("#view_fund").show();
				$("#view_relprod").show();
			}
			initImage("image", $(".link_bar").eq(0).attr("id"));
			viewProject.showDirectorNumber();
			view.inittabs($("#selectedTab").val(), 1);
			project.director();//在页面加载前生成a标签，用于显示负责人信息
			project.varDirector();//在页面加载前生成a标签，用于显示负责人信息
			$("#view_base").show();
			$("#view_apply").show();
			$("#view_established").show();
			$("#view_related").show();
			$("#tabcontent").show();
		}else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	
	/**
	 * 初始化委托应急课题项目查看页面及按钮，主要包括：查看、添加、修改、删除、上条、下条、弹出层查看详情,下载。
	 * @param {string} nameSpace
	 */
	exports.init = function() {
		viewProject.add(projectType);
		viewProject.modify(projectType);
		viewProject.del(projectType, "此操作将删除该项目所有信息包括申报、立项、结项、变更等。您确定要删除吗？");
		viewProject.back(projectType);// 重载back方法
		viewProject.prev(projectType, showProjectDetails);
		viewProject.next(projectType, showProjectDetails);
		viewProject.initListButton(projectType);
		viewProject.download(projectType);
//		viewProduct.init(projectType, showProjectDetails);
		viewProject.viewDeltails(projectType, showProjectDetails);
	};
	
});