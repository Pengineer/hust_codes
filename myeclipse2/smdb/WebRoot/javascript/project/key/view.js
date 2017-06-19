define(function(require, exports, module) {
	var view = require('javascript/view');
	var project = require('javascript/project/project_share/project');
	var viewProject = require('javascript/project/project_share/view');
//	var viewProduct = require('javascript/product/extIf/view');
	
	var projectType = "key";
	
	var initClick = function() {
		$(".popProject").live("click", function() {// 项目
			popProject(this.id, $("#projectTypeId").val());
			return false;
		});
		
		$("#view_add").bind("click", function() {
			var topsId = $("#topsId").val();
			if($("#listType").val() == 12){//选题
				if($("#accountType").val() != "EXPERT" && $("#accountType").val() != "TEACHER" && $("#accountType").val() != "STUDENT"){//录入添加
					window.location.href = basePath + "project/key/topicSelection/apply/toAddResult.action";
				}else{
					window.location.href = basePath + "project/key/topicSelection/apply/toAdd.action";
				}
			}else if($("#listType").val() == 1){ //申报项目
				if($("#accountType").val() != "EXPERT" && $("#accountType").val() != "TEACHER" && $("#accountType").val() != "STUDENT"){//录入添加
					window.location.href = basePath + "project/key/application/apply/toAddResult.action";
				}else{
					window.location.href = basePath + "project/key/application/apply/toAdd.action";
				}
			}else if($("#listType").val() == 2){
				window.location.href = basePath + "project/key/application/granted/toAdd.action";
			}else{}
			return false;
		});
		
		$("#view_mod").bind("click", function() {
			var topsId = $("#topsId").val();
			var entityId = $("#entityId").val();
			if($("#listType").val() == 12){ //申报选题
				if($("#accountType").val() != "EXPERT" && $("#accountType").val() != "TEACHER" && $("#accountType").val() != "STUDENT"){//录入修改
					window.location.href = basePath + "project/key/topicSelection/apply/toModifyResult.action?topsId="+topsId;
				}else{//研究人员申请修改
					window.location.href = basePath + "project/key/topicSelection/apply/toModify.action?topsId="+topsId;
				}
			}else if($("#listType").val() == 1){ //申报项目
				if($("#accountType").val() != "EXPERT" && $("#accountType").val() != "TEACHER" && $("#accountType").val() != "STUDENT"){//录入修改
					window.location.href = basePath + "project/key/application/apply/toModifyResult.action?entityId="+ entityId + "&topsId=" + topsId;
				}else{//研究人员申请修改
					window.location.href = basePath + "project/key/application/apply/toModify.action?entityId="+entityId + "&topsId=" + topsId;
				}
			}else if($("#listType").val() == 2){ //立项项目
				window.location.href = basePath + "project/key/application/granted/toModify.action?entityId="+entityId + "&topsId=" + topsId;
			}else{}
			return false;
		});
		
		$("#view_del").bind("click", function() {
			if($("#listType").val() == 12){ //申报选题
				if (confirm("此操作将删除该选题。您确定要删除吗？")) {
					var info=[{
							"url": "project/key/topicSelection/apply/delete.action?topsIds="+ $("#topsId").val(),
							"successUrl": "project/key/topicSelection/apply/toList.action?listType=11&update=1"
						}];
					$("#view_key").attr("action", info[0]["url"]);
					$("#view_key").ajaxSubmit({
						success:function(result){
							if (result.errorInfo == null || result.errorInfo == "") {
								window.location.href = basePath + info[0]["successUrl"];
								return false;
							} else {
								alert(result.errorInfo);
							}
						}
					});
				}
			}else{
				if (confirm("此操作将删除该项目所有信息包括申报、立项、中检、结项、变更等。您确定要删除吗？")) {
					var info=[{
							"url": "project/key/application/apply/delete.action?entityIds="+ $("#entityId").val(),
							"successUrl": "project/key/application/apply/toList.action?listType=1&update=1"
						},
						{
							"url": "project/key/application/granted/delete.action?entityIds="+ $("#entityId").val(),
							"successUrl": "project/key/application/granted/toList.action?listType=2&update=1"
							
						}];
					$("#view_key").attr("action", info[$("#listType").val()-1]["url"]);
					$("#view_key").ajaxSubmit({
						success:function(result){
							if (result.errorInfo == null || result.errorInfo == "") {
								window.location.href = basePath + info[$("#listType").val()-1]["successUrl"];
								return false;
							} else {
								alert(result.errorInfo);
							}
						}
					});
				}
			}
			return false;
		});
		
		$("#view_back").bind("click", function() {
			if($("#listType").val() == 1){
				backToList("project/key/application/apply/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
			}
			if($("#listType").val() == 2){
				backToList("project/key/application/granted/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
			}
			if($("#listType").val() == 3){
				backToList("project/key/midinspection/apply/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
			}
			if($("#listType").val() == 4){
				backToList("project/key/endinspection/apply/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
			}
			if($("#listType").val() == 5){
				backToList("project/key/variation/apply/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
			}
			if($("#listType").val() == 6){
				window.location.href = basePath + "login/projectRight.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val();
			}
			if($("#listType").val() == 7){
				backToList("project/toSearchMyProject.action");
			}
			if($("#listType").val() == 8){
				backToList("project/key/endinspection/review/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
			}
			if($("#listType").val() == 9){
				window.location.href = basePath + "login/ucenterRight.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val();
			}
			if($("#listType").val() == 10){
				backToList("project/key/application/review/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
			}
			if($("#listType").val() == 11){
				backToList("project/key/anninspection/apply/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
			}
			if($("#listType").val() == 12){
				backToList("project/key/topicSelection/apply/toList.action?topsId=" + $("#topsId").val() + "&update=" + $("#update").val());
			}
			return false;
		});
		
		$("#view_prev").bind("click", function() {
			if($("#listType").val() == 1){
				prev("project/key/application/apply/prev.action");
			}
			if($("#listType").val() == 2){
				prev("project/key/application/granted/prev.action");
			}
			if($("#listType").val() == 3){
				prev("project/key/midinspection/apply/prev.action");
			}
			if($("#listType").val() == 4){
				prev("project/key/endinspection/apply/prev.action");
			}
			if($("#listType").val() == 5){
				prev("project/key/variation/apply/prev.action");
			}
			if($("#listType").val() == 8){
				prev("project/key/endinspection/review/prev.action");
			}
			if($("#listType").val() == 10){
				prev("project/key/application/review/prev.action");
			}
			if($("#listType").val() == 11){
				prev("project/key/anninspection/apply/prev.action");
			}
			if($("#listType").val() == 12){
				prev("project/key/topicSelection/apply/prev.action");
			}
			return false;
		});
		
		$("#view_next").bind("click", function() {
			if($("#listType").val() == 1){
				next("project/key/application/apply/next.action");
			}
			if($("#listType").val() == 2){
				next("project/key/application/granted/next.action");
			}
			if($("#listType").val() == 3){
				next("project/key/midinspection/apply/next.action");
			}
			if($("#listType").val() == 4){
				next("project/key/endinspection/apply/next.action");
			}
			if($("#listType").val() == 5){
				next("project/key/variation/apply/next.action");
			}
			if($("#listType").val() == 8){
				next("project/key/endinspection/review/next.action");
			}
			if($("#listType").val() == 10){
				next("project/key/application/review/next.action");
			}
			if($("#listType").val() == 11){
				next("project/key/anninspection/apply/next.action");
			}
			if($("#listType").val() == 12){
				next("project/key/topicSelection/apply/next.action");
			}
			return false;
		});
		
		$(["annimage", "midimage", "endimage", "varimage"]).each(function(){
			$("." + this).live("click", function(){
				openOrNotImage($(this));
			});
		});
		
		if($("#listType").val() == 1){
			viewKey("project/key/application/apply/view.action");
		}
		if($("#listType").val() == 2){
			viewKey("project/key/application/granted/view.action");
		}
		if($("#listType").val() == 3){
			viewKey("project/key/midinspection/apply/view.action");
		}
		if($("#listType").val() == 4){
			viewKey("project/key/endinspection/apply/view.action");
		}
		if($("#listType").val() == 5){
			viewKey("project/key/variation/apply/view.action");
		}
		if($("#listType").val() == 6){
			viewKey("project/key/application/granted/view.action");//后台view方法需要根据listType是否有值判断是否修改pageNumber
		}
		if($("#listType").val() == 7){
			viewKey("project/viewMyProject.action");//后台view方法需要根据listType是否有值判断是否修改pageNumber
		}
		if($("#listType").val() == 8){
			viewKey("project/key/endinspection/review/viewReview.action");
		}
		if($("#listType").val() == 9){
			viewKey("project/key/application/granted/view.action");//后台view方法需要根据listType是否有值判断是否修改pageNumber
		}
		if($("#listType").val() == 10){
			viewKey("project/key/application/review/viewReview.action");//后台view方法需要根据listType是否有值判断是否修改pageNumber
		}
		if($("#listType").val() == 11){
			viewKey("project/key/anninspection/apply/view.action");
		}
		if($("#listType").val() == 12){
			if($("#entityId").val() != ''){
				viewKey("project/key/application/apply/view.action");
			}else{
				viewTopic("project/key/topicSelection/apply/viewTopic.action");
			}
		}
	};
	
	//查看重大攻关项目
	var viewKey = function(url) {
		if (parent != null) {
			parent.loading_flag = true;
			setTimeout("parent.showLoading();", parent.loading_lag_time);
		}
		$.ajax({
			url: url,
			type: "post",
			data: "projectid=" + $("#projectid").val()+"&entityId=" + $("#entityId").val()+"&topsId=" + $("#topsId").val()+"&pageNumber=" + $("#pageNumber").val() + "&projectType=" + $("#projectType").val() + "&listType=" + $("#listType").val(),
			dataType: "json",
			success: showProjectDetails
		});
	};
	//查看重大攻关选题
	var viewTopic = function(url) {
		if (parent != null) {
			parent.loading_flag = true;
			setTimeout("parent.showLoading();", parent.loading_lag_time);
		}
		$.ajax({
			url: url,
			type: "post",
			data: "topsId=" + $("#topsId").val()+"&entityId=" + $("#topsId").val()+"&pageNumber=" + $("#pageNumber").val() + "&projectType=" + $("#projectType").val() + "&listType=" + $("#listType").val(),
			dataType: "json",
			success: showProjectDetails
		});
	};
	
	/**
	 * 查看上条
	 */
	var prev = function(url){
		if($("#entityId").val() != ''){
			viewKey(url);
		}else{
			viewTopic(url);
		}
	};
	
	/**
	 * 查看下条
	 */
	var next = function(url){
		if($("#entityId").val() != ''){
			viewKey(url);
		}else{
			viewTopic(url);
		}
	};
	/**
	 * 返回列表
	 */
	var backToList = function(url){
		$("#view_key").attr("action", url);
		$("#view_key").submit();
	};
	
	var showProjectDetails = function(result) {
		image("image");
		if (result.errorInfo == null || result.errorInfo == "") {
//			$(["annimage", "midimage", "endimage", "varimage"]).each(function(){
//				image(this);
//			});
			$("#topsId").attr("value", result.topicSelection.id);//刷新选题id
			$("#appFlag").attr("value", result.appFlag);//刷新选题id
			$("#view_base").html(TrimPath.processDOMTemplate("view_base_template", result));//刷新基本信息
			$("#view_tops").html(TrimPath.processDOMTemplate("view_tops_template", result));//刷新申请信息
			if(!(result.topicSelection.finalAuditStatus == 3 && result.topicSelection.finalAuditResult == 2)){//未选题
				$("#appFormEntityid").attr("value", "");//刷新id
				$("#view_apply").html("");
				$(".un_selected").each(function(){
					$(this).hide();
				});
			}else{//已选题
				if(result.application != null){//选题已关联项目
					$("#entityId").attr("value", result.application.id);//刷新申请id
					$("#view_apply").html(TrimPath.processDOMTemplate("view_apply_template", result));//刷新申请信息
					$("#view_related").html(TrimPath.processDOMTemplate("view_related_template", result));//刷新相关成员信息
					setOddEvenLine("list_key_member", 0);
					if (result.memberFlag == 1 && (typeof(result.memberList) == "undefined" || result.memberList == 0)) {
						setColspan("list_key_member");
					}
					if (!(result.application.finalAuditStatus == 3 && result.application.finalAuditResult == 2)) {//未立项
						$("#appFormEntityid").attr("value", result.application.id);//刷新id
						$("#projectid").attr("value", "");//刷新id
						$("#annFormProjectid").attr("value", "");//刷新id
						$("#annFileProjectid").attr("value", "");//刷新id
						$("#midFormProjectid").attr("value", "");//刷新id
						$("#midFileProjectid").attr("value", "");//刷新id
						$("#endFileProjectid").attr("value", "");//刷新id
						$("#endFormProjectid").attr("value", "");//刷新id
						$("#view_ann").html("");
						$("#view_mid").html("");
						$("#view_end").html("");
						$("#view_var").html("");
						$("#view_fund").html("");
						$("#view_relprod").html("");
						$(".un_granted").each(function(){
							$(this).hide();
						});
					}
					else {//已立项
						$("#appFormEntityid").attr("value", result.application.id);//刷新id
						$("#projectid").attr("value", result.granted.id);//刷新id
						$("#annFormProjectid").attr("value", result.granted.id);//刷新id
						$("#annFileProjectid").attr("value", result.granted.id);//刷新id
						$("#midFormProjectid").attr("value", result.granted.id);//刷新id
						$("#midFileProjectid").attr("value", result.granted.id);//刷新id
						$("#endFileProjectid").attr("value", result.granted.id);//刷新id
						$("#endFormProjectid").attr("value", result.granted.id);//刷新id
						$("#endImportedFormProjectid").attr("value", result.granted.id);//刷新id
						$(".un_granted").each(function(){
							$(this).show();
						});
						$("#view_established").html(TrimPath.processDOMTemplate("view_established_template", result));//刷新立项信息
						$("#view_ann").html(TrimPath.processDOMTemplate("view_ann_template", result));
						$("#view_mid").html(TrimPath.processDOMTemplate("view_mid_template", result));
						$("#view_end").html(TrimPath.processDOMTemplate("view_end_template", result));
						$("#view_var").html(TrimPath.processDOMTemplate("view_var_template", result));
						$("#view_fund").html(TrimPath.processDOMTemplate("view_fund_template", result));
						//操作校验参数
						$("#midPassAlready").attr("value",result.midPassAlready);
						$("#endPassAlready").attr("value",result.endPassAlready);
						$("#endAllow").attr("value",result.endAllow);
						$("#midForbid").attr("value",result.midForbid);
						$("#annPending").attr("value",result.annPending);
						$("#midPending").attr("value",result.midPending);
						$("#endPending").attr("value",result.endPending);
						$("#varPending").attr("value",result.varPending);
						if (result.varList != null && result.varList.length > 0) {
							$("#currentStop").attr("value",result.varList[0].stop);
							$("#currentWithDraw").attr("value",result.varList[0].withdraw);
						}
						setOddEvenLine("project_found", 0);
						if(result.varList.length>0){
							for(var i = 0; i < result.varList.length; i++){
								setOddEvenLine("list_key_variation"+i, 0);
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
						//年检、中检、结项成果
						$([result.annProdInfo, result.midProdInfo, result.endProdInfo]).each(function(index1){
							var ids = ["list_table_ann_", "list_table_mid_", "list_table_end_"]
							$(this).each(function(index2){
								setOddEvenLine(ids[index1] + index2, 0);
								if(this.productSize == 0) {
									setColspan(ids[index1] + index2);
								}
							})
						});
						showListNumber();
//						$(["annimage", "midimage", "endimage", "varimage"]).each(function(){
//							initImage(this, $(".link_bar").eq(0).attr("id"));
//						});
						viewProject.showAnnMidEndVarNumber();
						//获取结项证书显示信息
						(function(){
							certificateInfo = [];
							$([(result.researchTypeNameNew) ? result.researchTypeNameNew : "", 
								(result.granted && result.granted.name) ? result.granted.name : "", 
								(result.granted && result.granted.applicantName) ? result.granted.applicantName : "",  
								result.endList,                                                 
								(result.granted && result.granted.number) ? result.granted.number : "", 
								result.endList
							]).each(function(){
								certificateInfo.push(this);
							});
						})();
						$("#view_ann").show();
						$("#view_mid").show();
						$("#view_end").show();
						$("#view_var").show();
						$("#view_fund").show();
						$("#view_relprod").show();
					}
				}else{
					$("#view_apply").html("");
					$(".un_selected").each(function(){
						$(this).hide();
					});
				}
			}
			initImage("image", $(".link_bar").eq(0).attr("id"));
			viewProject.showDirectorNumber();
			view.inittabs($("#selectedTab").val(), 1);
			view.inittabs();
			project.director();//在页面加载前生成a标签，用于显示负责人信息
			project.varDirector();//在页面加载前生成a标签，用于显示负责人信息
			$("#view_base").show();
			$("#view_tops").show();
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
//			$(["annimage", "midimage", "endimage", "varimage"]).each(function(){
//				image(this);
//			});
			$("#topsId").attr("value", result.topicSelection.id);//刷新选题id
			$("#appFlag").attr("value", result.appFlag);//刷新选题id
			$("#view_base").html(TrimPath.processDOMTemplate("view_base_template", result));//刷新基本信息
			$("#view_tops").html(TrimPath.processDOMTemplate("view_tops_template", result));//刷新申请信息
			if(!(result.topicSelection.finalAuditStatus == 3 && result.topicSelection.finalAuditResult == 2)){//未选题
				$("#appFormEntityid").attr("value", "");//刷新id
				$("#view_apply").html("");
				$(".un_selected").each(function(){
						$(this).hide();
				});
			}else{//已选题
				if(result.application != null){//选题已关联项目
					$("#entityId").attr("value", result.application.id);//刷新申请id
					$("#view_apply").html(TrimPath.processDOMTemplate("view_apply_template", result));//刷新申请信息
					$("#view_related").html(TrimPath.processDOMTemplate("view_related_template", result));//刷新相关成员信息
					setOddEvenLine("list_key_member", 0);
					if (result.memberFlag == 1 && (typeof(result.memberList) == "undefined" || result.memberList == 0)) {
						setColspan("list_key_member");
					}
					if (!(result.application.finalAuditStatus == 3 && result.application.finalAuditResult == 2)) {//未立项
						$("#projectid").attr("value", "");//刷新id
						$("#midFormProjectid").attr("value", "");//刷新id
						$("#midFileProjectid").attr("value", "");//刷新id
						$("#endFileProjectid").attr("value", "");//刷新id
						$("#endFormProjectid").attr("value", "");//刷新id
						$("#view_ann").html("");
						$("#view_mid").html("");
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
						$("#annFormProjectid").attr("value", result.granted.id);//刷新id
						$("#annFileProjectid").attr("value", result.granted.id);//刷新id
						$("#midFormProjectid").attr("value", result.granted.id);//刷新id
						$("#midFileProjectid").attr("value", result.granted.id);//刷新id
						$("#endFileProjectid").attr("value", result.granted.id);//刷新id
						$("#endFormProjectid").attr("value", result.granted.id);//刷新id
						$(".un_granted").each(function(){
							$(this).show();
						});
						$("#view_established").html(TrimPath.processDOMTemplate("view_established_template", result));//刷新立项信息
						$("#view_ann").html(TrimPath.processDOMTemplate("view_ann_template", result));
						$("#view_mid").html(TrimPath.processDOMTemplate("view_mid_template", result));
						$("#view_end").html(TrimPath.processDOMTemplate("view_end_template", result));
						$("#view_var").html(TrimPath.processDOMTemplate("view_var_template", result));
						$("#view_fund").html(TrimPath.processDOMTemplate("view_fund_template", result));
						//操作校验参数
						$("#midPassAlready").attr("value",result.midPassAlready);
						$("#endPassAlready").attr("value",result.endPassAlready);
						$("#endAllow").attr("value",result.endAllow);
						$("#midForbid").attr("value",result.midForbid);
						$("#annPending").attr("value",result.annPending);
						$("#midPending").attr("value",result.midPending);
						$("#endPending").attr("value",result.endPending);
						$("#varPending").attr("value",result.varPending);
						if (result.varList != null && result.varList.length > 0) {
							$("#currentStop").attr("value",result.varList[0].stop);
							$("#currentWithDraw").attr("value",result.varList[0].withdraw);
						}
						setOddEvenLine("project_found", 0);
						if(result.varList.length>0){
							for(var i = 0; i < result.varList.length; i++){
								setOddEvenLine("list_key_variation"+i, 0);
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
						//年检、中检、结项成果
						$([result.annProdInfo, result.midProdInfo, result.endProdInfo]).each(function(index1){
							var ids = ["list_table_ann_", "list_table_mid_", "list_table_end_"]
							$(this).each(function(index2){
								setOddEvenLine(ids[index1] + index2, 0);
								if(this.productSize == 0) {
									setColspan(ids[index1] + index2);
								}
							})
						});
						showListNumber();
//						$(["annimage", "midimage", "endimage", "varimage"]).each(function(){
//							initImage(this, $(".link_bar").eq(0).attr("id"));
//						});
						viewProject.showAnnMidEndVarNumber();
						//获取结项证书显示信息
						(function(){
							certificateInfo = [];
							$([(result.researchTypeNameNew) ? result.researchTypeNameNew : "", 
								(result.granted && result.granted.name) ? result.granted.name : "", 
								(result.granted && result.granted.applicantName) ? result.granted.applicantName : "",  
								result.endList,                                                 
								(result.granted && result.granted.number) ? result.granted.number : "", 
								result.endList
							]).each(function(){
								certificateInfo.push(this);
							});
						})();
						$("#view_ann").show();
						$("#view_mid").show();
						$("#view_end").show();
						$("#view_var").show();
						$("#view_fund").show();
						$("#view_relprod").show();
					}
				}else{
					$("#view_apply").html("");
					$(".un_selected").each(function(){
						$(this).hide();
					});
				}
			}
			initImage("image", $(".link_bar").eq(0).attr("id"));
			viewProject.showDirectorNumber();
			view.inittabs($("#selectedTab").val(), 1);
			project.director();//在页面加载前生成a标签，用于显示负责人信息
			project.varDirector();//在页面加载前生成a标签，用于显示负责人信息
			$("#view_base").show();
			$("#view_tops").show();
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
	 * 初始化重大攻关项目查看页面及按钮，主要包括：查看、添加、修改、删除、上条、下条、弹出层查看详情,下载。
	 * @param {string} nameSpace
	 */
	exports.init = function() {
		initClick();
		viewProject.initListButton(projectType);
		viewProject.download(projectType);
	};
	
	exports.viewKey = function(url){
		viewKey(url);
	}
});