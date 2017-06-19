/**
 * 成果列表
 */
define(function(require, exports, module) {
	var list = require('javascript/list');
	var auditProd = require('javascript/product/audit');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	thisPopLayer = top.PopLayer.instances[top.PopLayer.id];	
	
	//审核成果
	var audit = function(url, flag){
		if(flag == 1) {//1.列表页面
			$("#pagenumber").attr("value", $("#list_goto").val());
			$("#list").attr("action", url).submit();
		} else if(flag == 2) {//2.查看页面
			$.ajax({
				url: nameSpace + "/view.action",
				type: "post",
				data: "entityIds=" + $("#entityId").val(),
				dataType: "json",
				success: function(result){
					if(result.errorInfo == null || result.errorInfo == "") {
						$("#update").val(1);
						view.show(nameSpace, showDetails);
					} else {
						alert(result.errorInfo);
					};
				}
			});
		};
	};
	//初始化弹出层绑定
	var initLive = function(){
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
	};
	
	var leaf = function(nameSpace){
		//下载成果
		$(".downlaod_product").live("click", function() {
			var validateUrl = nameSpace + "/validateFile.action?entityId=" + this.id + "&fileFileName=" + this.name;
			var successUrl = nameSpace + "/download.action?entityId=" + this.id + "&fileFileName=" + this.name;
			downloadFile(validateUrl, successUrl);
			return false;
		});
		
		//审核成果
		$("#list_audit").live("click", function() {
			var cnt = count("entityIds");
			if (cnt == 0){alert("请选择要审核的成果！"); return;}
			popProductOperation({
				title : "审核成果",
				src : nameSpace + "/toAudit.action?exflag=1",
				callBack : function(result){
					audit(nameSpace + "/audit.action?auditResult=" + result.auditResult, 1);
				}
			});
			return false;
		});
	};
	
	exports.init = function(nameSpace) {
		initLive();
		leaf(nameSpace);
	};
});
