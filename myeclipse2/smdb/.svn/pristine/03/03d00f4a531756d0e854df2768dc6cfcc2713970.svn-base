define(function(require, exports, module) {
	var view = require('javascript/view');
	var auditProd = require('javascript/product/audit');
	var prod = require('javascript/product/product');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	
	thisPopLayer = top.PopLayer.instances[top.PopLayer.id];	
	
	/**
	 * 显示查看数据
	 */
	var showDetails = function(result) {
		if(result.errorInfo == null || result.errorInfo == ""){
			$("#entityId").attr("value", result[result.productType].id);
			$("#entityIds").attr("value", result[result.productType].id);
			result.saveDate = (result.saveDate != null) ? result.saveDate.replace("T"," ") : null;
			result.authorid = result.authorid || null;
			$(["base", "basic", "project", "publication", "award"]).each(function(){
				$("#view_container_" + this).html(TrimPath.processDOMTemplate("view_template_" + this, result)).show();
			});
			$("#view_content").show();
			view.inittabs();
		} else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	var initSession = function(){
		prod.loadSession('session1');
		$("#session1").val($("#defualSession").val());
	};
	
	var viewAudit = function(nameSpace){
		$("#view_audit").live("click", function(){//审核成果
			popProductOperation({
				title : "审核成果",
				src : nameSpace + "/toAudit.action?exflag=1",
				callBack : function(result, layer){
					auditProd.audit(nameSpace + "/audit.action?auditResult=" + result.auditResult, 2, nameSpace, showDetails);
				}
			});
			return false;
		});
	};	
		
	var initLive = function(nameSpace){//初始化成果查看
		$(function() {
			//查看审核
			$("#view_audit_info").live("click", function(){
				popProductOperation({
					src : nameSpace + "/viewAudit.action?viewflag=2&entityId=" + $("#entityId").val(),
					title : "查看审核详情"
				});
				return false;
			});
			
			//下载成果文件
			$(".downlaod_product").live("click",function(){
				var validateUrl = nameSpace + "/validateFile.action?entityId=" + this.id + "&fileFileName=" + this.name;
				var successUrl = nameSpace + "/download.action?fileFileName=" + this.name;
				downloadFile(validateUrl, successUrl);
				return false;
			});
			
			//查看作者
			$(".view_author").live("click", function() {
				popPerson(this.id, 7);
				return false;
			});
			//查看团队
			$(".view_organization").live("click", function() {
				popOrganization(this.id);
				return false;
			});
			//查看高校
			$(".view_university").live("click", function() {
				popAgency(this.id, 1);
				return false;
			});
			//查看院系
			$(".view_author_dept").live("click", function() {
				popDept(this.id, 2);
				return false;
			});
			//查看机构
			$(".view_author_inst").live("click", function() {
				popInst(this.id, 3);
				return false;
			});
			//查看项目
			$(".view_project").live("click", function() {
				popProject(this.id, this.name);
				return false;
			});
		});
	};
	
	var initApplyAward = function(nameSpace, showDetails){//初始化成果查看（奖励信息）
		
		//下载奖励申请书
		$(".downlaodAwardFile").live("click", function(){
			var validateUrl = "award/moesocial/application/apply/validateFile.action?entityId=" + this.id + "&filePath=" + this.name;
			var successUrl = "award/moesocial/application/apply/download.action?filePath=" + this.name;
			downloadFile(validateUrl, successUrl);
			return false;
		});
		
		//下载奖励申请书模板
		$("#download_award_model").live("click", function(){
			window.location.href = basePath + "award/moesocial/application/apply/downloadModel.action";
			return false;
		});
		
		//添加奖励申请
		$("#apply_award").live("click", function(){
			if(!$("#entityId").val()){alert("请选择要报奖的成果！"); return;}
			prod.apply(nameSpace + "/toAddApply.action?entityId=" + $("#entityId").val(), nameSpace, showDetails);
			return false;
		});
		
		//提交奖励申请
		$("#submit_apply").live("click", function() {
			if(!$("#entityId").val()){alert("请选择要提交的成果报奖！");return;}
			prod.submitApply("award/moesocial/application/apply/submit.action?entityId=" + this.alt, nameSpace, showDetails);
			return false;
		});
		
		//修改奖励申请
		$("#modify_apply").live("click", function() {
			if(!$("#entityId").val() == "undefined"){alert("请选择要修改的报奖成果！"); return;}
			prod.modifyApply(nameSpace + "/toModifyApply.action?entityId=" + $("#entityId").val(), nameSpace, showDetails);
			return false;
		});
		
		//删除奖励申请
		$("#delete_apply").live("click", function() {
			prod.deleteApply("award/moesocial/application/apply/delete.action?entityIds=" + this.alt, nameSpace, showDetails);
			return false;
		});
	};
	
	//重载返回方法
	var back = function(nameSpace){
		$("#view_back").live("click", function() {
			if($("#productflag").val() == 1){
				$("#view_product").attr("action", "product/toSearchDirectProduct.action");
			}else{
				$("#view_product").attr("action", nameSpace + "/toList.action");
			}
			$("#view_product").submit();
		});
	};
	//重载show方法
	var show = function(nameSpace, showDetails){
		if (parent != null) {
			parent.loading_flag = true;
			setTimeout("parent.showLoading();", parent.loading_lag_time);
		};
		$.ajax({
			url: nameSpace + "/view.action",
			type: "post",
			data: "entityId=" + $("#entityId").val(),
			dataType: "json",
			success: showDetails
		});
	};
	
	exports.init = function() {
		var nameSpace = "product/" + $("#protype").val();
		show(nameSpace, showDetails);
		view.add(nameSpace);
		view.mod(nameSpace);
		view.del(nameSpace, "您确定要删除此条成果吗？");
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		back(nameSpace);// 所有查看页面公共部分
		view.download(nameSpace);
		viewAudit(nameSpace);
		initLive(nameSpace);
		initApplyAward(nameSpace, showDetails);
	};
	
	exports.showDetails = function(){
		showDetails();
	};
	
	exports.viewAudit = function(){
		viewAudit();
	};
	
	exports.initLive = function(nameSpace){
		initLive(nameSpace);
	};
	
	exports.initApplyAward = function(){
		initApplyAward(nameSpace, showDetails);
	};
});
