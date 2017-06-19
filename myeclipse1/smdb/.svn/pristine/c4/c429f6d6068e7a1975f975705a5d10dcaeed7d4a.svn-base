define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	
	//弹出层查看详情，包括：负责人、项目成员详情、高校、院系、基地
	var initListButton = function(projectType){
		$(function() {
			$(".linkDirectors").live("click", function() {// 人
				popPerson(this.id, 7);
				return false;
			});
			$(".linkVarDirectors").live("click", function(nameSpace) {
				var id = $(this).attr("id");
				popProjectOperation({
					title: "查看项目成员详细信息",
					src : "project/" + projectType + "/variation/apply/viewDir.action?entityId=" + id
				});
				return false;
			});
			$(".linkUni").live("click", function() {// 高校
				popAgency(this.id, 1);
				return false;
			});
			$(".linkDep").live("click", function() {// 院系
				popDept(this.id, 2);
				return false;
			});
			$(".linkIns").live("click", function() {// 基地
				popInst(this.id, 3);
				return false;
			});
		});

	};
	
	//重载add方法
	var add = function(projectType){
		$(function() {
			$("#view_add").bind("click", function() {
				if($("#listType").val() == 1){ //申请项目
					if($("#accountType").val() != "EXPERT" && $("#accountType").val() != "TEACHER" && $("#accountType").val() != "STUDENT"){//录入添加
						window.location.href = basePath + "project/" + projectType + "/application/apply/toAddResult.action";
					}else{
						window.location.href = basePath + "project/" + projectType + "/application/apply/toAdd.action";
					}
				}else if($("#listType").val() == 2){//立项项目
					window.location.href = basePath + "project/" + projectType + "/application/granted/toAdd.action";
				}else{}
				return false;
			});
		});
	};
	
	//重载modify方法
	var modify = function(projectType){
		$(function() {
			$("#view_mod").bind("click", function() {
				var entityId = $("#entityId").val();
				if($("#listType").val() == 1){ //申请项目
					if($("#accountType").val() != "EXPERT" && $("#accountType").val() != "TEACHER" && $("#accountType").val() != "STUDENT"){//录入修改
						window.location.href = basePath + "project/" + projectType + "/application/apply/toModifyResult.action?entityId="+entityId;
					}else{//研究人员申请修改
						window.location.href = basePath + "project/" + projectType + "/application/apply/toModify.action?entityId="+entityId;
					}
				}else if($("#listType").val() == 2){ //立项项目
					window.location.href = basePath + "project/" + projectType + "/application/granted/toModify.action?entityId="+entityId;
				}else{}
				return false;
			});
		});
	};
	
	var del = function(projectType, delInfo){
		$(function() {
			$("#view_del").bind("click", function() {
				if (confirm(delInfo)) {
					var info=[{
						"url": "project/" + projectType + "/application/apply/delete.action?entityIds="+ $("#entityId").val(),
						"successUrl": "project/" + projectType + "/application/apply/toList.action?listType=1&update=1"
					},
					{
						"url": "project/" + projectType + "/application/granted/delete.action?entityIds="+ $("#entityId").val(),
						"successUrl": "project/" + projectType + "/application/granted/toList.action?listType=2&update=1"
						
					}];
					$("#view_" + projectType).attr("action", info[$("#listType").val()-1]["url"]);
					$("#view_" + projectType).ajaxSubmit({
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
				return false;
			});
		});
	};
	
	var back = function(projectType){
		$(function() {
			$("#view_back").bind("click", function() {
				if($("#listType").val() == 1){
					backToList(projectType, "project/" + projectType + "/application/apply/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
				}
				if($("#listType").val() == 2){
					backToList(projectType, "project/" + projectType + "/application/granted/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
				}
				if($("#listType").val() == 3){
					backToList(projectType, "project/" + projectType + "/midinspection/apply/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
				}
				if($("#listType").val() == 4){
					backToList(projectType, "project/" + projectType + "/endinspection/apply/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
				}
				if($("#listType").val() == 5){
					backToList(projectType, "project/" + projectType + "/variation/apply/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
				}
				if($("#listType").val() == 6){
					window.location.href = basePath + "login/projectRight.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val();
				}
				if($("#listType").val() == 7){
					backToList(projectType, "project/toSearchMyProject.action");
				}
				if($("#listType").val() == 8){
					backToList(projectType, "project/" + projectType + "/endinspection/review/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
				}
				if($("#listType").val() == 9){
					window.location.href = basePath + "login/ucenterRight.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val();
				}
				if($("#listType").val() == 10){
					backToList(projectType, "project/" + projectType + "/application/review/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
				}
				if($("#listType").val() == 11){
					backToList(projectType, "project/" + projectType + "/anninspection/apply/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val());
				}
				return false;
			});
			$("#view_back_mypro").bind("click", function() {
					backToList(projectType, "project/toSearchMyProject.action");
				return false;
			});
		});
	};
	
	/**
	 * 返回列表
	 */
	function backToList(projectType, url){
		$("#view_" + projectType).attr("action", url);
		$("#view_" + projectType).submit();
	};
	
	//查看上条，重载prev方法
	var prev = function(projectType, showProjectDetails){
		$(function() {
			$("#view_prev").bind("click", function() {
				if($("#listType").val() == 1){
					viewProject("project/"+ projectType + "/application/apply/prev.action", showProjectDetails);
				}
				if($("#listType").val() == 2){
					viewProject("project/" + projectType + "/application/granted/prev.action", showProjectDetails);
				}
				if($("#listType").val() == 3){
					viewProject("project/" + projectType + "/midinspection/apply/prev.action", showProjectDetails);
				}
				if($("#listType").val() == 4){
					viewProject("project/" + projectType + "/endinspection/apply/prev.action", showProjectDetails);
				}
				if($("#listType").val() == 5){
					viewProject("project/" + projectType + "/variation/apply/prev.action", showProjectDetails);
				}
				if($("#listType").val() == 8){
					viewProject("project/" + projectType + "/endinspection/review/prev.action", showProjectDetails);
				}
				if($("#listType").val() == 10){
					viewProject("project/" + projectType + "/application/review/prev.action", showProjectDetails);
				}
				if($("#listType").val() == 11){
					viewProject("project/" + projectType + "/anninspection/apply/prev.action", showProjectDetails);
				}
				return false;
			});
		});
	};
	
	//查看下条，重载next方法
	var next = function(projectType, showProjectDetails){
		$(function() {
			$("#view_next").bind("click", function() {
				if($("#listType").val() == 1){
					viewProject("project/" + projectType + "/application/apply/next.action", showProjectDetails);
				}
				if($("#listType").val() == 2){
					viewProject("project/" + projectType + "/application/granted/next.action", showProjectDetails);
				}
				if($("#listType").val() == 3){
					viewProject("project/" + projectType + "/midinspection/apply/next.action", showProjectDetails);
				}
				if($("#listType").val() == 4){
					viewProject("project/" + projectType + "/endinspection/apply/next.action", showProjectDetails);
				}
				if($("#listType").val() == 5){
					viewProject("project/" + projectType + "/variation/apply/next.action", showProjectDetails);
				}
				if($("#listType").val() == 8){
					viewProject("project/" + projectType + "/endinspection/review/next.action", showProjectDetails);
				}
				if($("#listType").val() == 10){
					viewProject("project/" + projectType + "/application/review/next.action", showProjectDetails);
				}
				if($("#listType").val() == 11){
					viewProject("project/" + projectType + "/anninspection/apply/next.action", showProjectDetails);
				}
				return false;
			});
		});
	};
	
	var viewDeltails = function(projectType, showProjectDetails){
		if($("#listType").val() == 1){
			viewProject("project/" + projectType + "/application/apply/view.action", showProjectDetails);
		}
		if($("#listType").val() == 2){
			viewProject("project/" + projectType + "/application/granted/view.action", showProjectDetails);
		}
		if($("#listType").val() == 3){
			viewProject("project/" + projectType + "/midinspection/apply/view.action", showProjectDetails);
		}
		if($("#listType").val() == 4){
			viewProject("project/" + projectType + "/endinspection/apply/view.action", showProjectDetails);
		}
		if($("#listType").val() == 5){
			viewProject("project/" + projectType + "/variation/apply/view.action", showProjectDetails);
		}
		if($("#listType").val() == 6){
			viewProject("project/" + projectType + "/application/granted/view.action", showProjectDetails);//后台view方法需要根据listType是否有值判断是否修改pageNumber
		}
		if($("#listType").val() == 7){
			viewProject("project/viewMyProject.action", showProjectDetails);//后台view方法需要根据listType是否有值判断是否修改pageNumber
		}
		if($("#listType").val() == 8){
			viewProject("project/" + projectType + "/endinspection/review/viewReview.action", showProjectDetails);
		}
		if($("#listType").val() == 9){
			viewProject("project/" + projectType + "/application/granted/view.action", showProjectDetails);//后台view方法需要根据listType是否有值判断是否修改pageNumber
		}
		if($("#listType").val() == 10){
			viewProject("project/" + projectType + "/application/review/viewReview.action", showProjectDetails);//后台view方法需要根据listType是否有值判断是否修改pageNumber
		}
		if($("#listType").val() == 11){
			viewProject("project/" + projectType + "/anninspection/apply/view.action", showProjectDetails);
		}
	};
	
	//重载show方法
	var viewProject = function(url, showProjectDetails){
		if (parent != null) {
			parent.loading_flag = true;
			setTimeout("parent.showLoading();", parent.loading_lag_time);
		}
		$.ajax({
			url: url,
			type: "post",
			data: "projectid=" + $("#projectid").val()+"&entityId=" + $("#entityId").val()+"&pageNumber=" + $("#pageNumber").val() + "&projectType=" + $("#projectType").val() + "&listType=" + $("#listType").val(),
			dataType: "json",
			success: showProjectDetails
		});
	};
	
	var showAnnMidEndVarNumber = function(){
		$(".number").each(function(){
			if(!isNaN(parseInt($(this).html()))){
				$(this).html( Num2Chinese(parseInt($(this).attr("name")) - parseInt($(this).html())));
			}
		});
	}
	
	var showDirectorNumber = function (){
		$(".director_num").each(function(){
			if(!isNaN(parseInt($(this).html()))){
				$(this).html( Num2Chinese(parseInt($(this).html()) + 1));
			}
		});
	};
	
	var download = function(projectType){
		$(function() {
			$(".download_" + projectType + "_1").live("click", function(nameSpace) {//申请申请书下载
				var validateUrl = "project/" + projectType + "/application/apply/validateFile.action?entityId="+this.id;
				var successUrl = "project/" + projectType + "/application/apply/downloadApply.action?entityId="+this.id;
				downloadFile(validateUrl, successUrl);
				return false;
			});
			$(".download_" + projectType + "_8").live("click", function(nameSpace) {//立项计划书下载
				var validateUrl = "project/" + projectType + "/application/granted/validateFile.action?entityId="+this.id;
				var successUrl = "project/" + projectType + "/application/granted/downloadApply.action?entityId="+this.id;
				downloadFile(validateUrl, successUrl);
				return false;
			});
			$(".download_" + projectType + "_2").live("click", function(nameSpace) {//中检申请书下载
				var validateUrl = "project/" + projectType + "/midinspection/apply/validateFile.action?entityId="+this.id;
				var successUrl = "project/" + projectType + "/midinspection/apply/downloadApply.action?entityId="+this.id;
				downloadFile(validateUrl, successUrl);
				return false;
			});
			$(".download_" + projectType + "_3").live("click", function(nameSpace) {//结项申请书下载
				var validateUrl = "project/" + projectType + "/endinspection/apply/validateFile.action?entityId="+this.id;
				var successUrl = "project/" + projectType + "/endinspection/apply/downloadApply.action?entityId="+this.id;
				downloadFile(validateUrl, successUrl);
				return false;
			});
			$(".download_" + projectType + "_4").live("click", function(nameSpace) {//变更申请书下载
				var validateUrl = "project/" + projectType + "/variation/apply/validateFile.action?entityId="+this.id;
				var successUrl = "project/" + projectType + "/variation/apply/downloadApply.action?entityId="+this.id;
				downloadFile(validateUrl, successUrl);
				return false;
			});
			$(".download_" + projectType + "_5").live("click", function(nameSpace) {//结项数据包下载
				var validateUrl = "project/" + projectType + "/endinspection/apply/validateData.action?entityId="+this.id+"&filepath="+this.name;
				var successUrl = "project/" + projectType + "/endinspection/apply/downloadData.action?filepath="+this.name;
				downloadFile(validateUrl, successUrl);
				return false;
			});
			$(".download_" + projectType + "_6").live("click", function(nameSpace) {//年检申请书下载
				var validateUrl = "project/" + projectType + "/anninspection/apply/validateFile.action?entityId="+this.id;
				var successUrl = "project/" + projectType + "/anninspection/apply/downloadApply.action?entityId="+this.id;
				downloadFile(validateUrl, successUrl);
				return false;
			});
			$(".download_" + projectType + "_7").live("click", function(nameSpace) {//变更延期计划书下载
				var validateUrl = "project/" + projectType + "/variation/apply/validatePostponementFile.action?entityId="+this.id;
				var successUrl = "project/" + projectType + "/variation/apply/downloadPostponement.action?entityId="+this.id;
				downloadFile(validateUrl, successUrl);
				return false;
			});
		});
	};
	
	
	
	/**
	 * 初始化项目查看页面及按钮，主要包括：查看、添加、修改、上条、下条、弹出层查看详情。
	 * @param {string} projectType项目类型：general、instp、post、key、entrust。
	 */
	module.exports = {
		add: function(projectType){add(projectType)},// 添加，重载add方法
		modify: function(projectType){modify(projectType)},// 修改，重载modify方法
		del: function(projectType, delInfo){del(projectType, delInfo)},// 删除，重载del方法
		back: function(projectType){back(projectType)},//退回，重载back方法
		viewDeltails: function(projectType, showProjectDetails){viewDeltails(projectType, showProjectDetails)},// 查看页面，重载show方法
		viewProject: function(url, showProjectDetails){viewProject(url, showProjectDetails)},
		prev: function(projectType, showProjectDetails){prev(projectType, showProjectDetails)},// 重载prev方法
		next: function(projectType, showProjectDetails){next(projectType, showProjectDetails)},// 重载prev方法
		initListButton: function(projectType){initListButton(projectType)},// 查看页面弹出层链接
		showAnnMidEndVarNumber: function(){showAnnMidEndVarNumber()},
		showDirectorNumber: function(){showDirectorNumber()},
		download: function(projectType){download(projectType)}//下载，重载download方法
	};
});
