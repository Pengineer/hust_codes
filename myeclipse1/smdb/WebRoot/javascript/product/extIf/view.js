/**
 * @author 王燕
 */
define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	var viewEndinspection = require('javascript/project/project_share/endinspection/apply/view');
	
	//定义列表类型
	var viewTypeMap = {"ann_prod" : 1, "mid_prod" : 2, "end_prod" : 3, "rel_prod" : 4};
	
	var initLive = function(projectType, showProjectDetails){
		//查看成果作者详细信息
		$(".view_author").live("click", function() {
			popPerson(this.id, 5);
			return false;
		});
		
		//查看学校详细信息
		$(".view_university").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		
		//弹出层查看成果详情
		$(".view_product").live("click", function(){
			popProductOperation({
				title : "查看成果",
				src : "product/" + this.type + "/toView.action?entityId=" + this.id + "&exflag=1"
			});
			return false;
		});
		
		//弹出层添加成果
		$("#view_add_product").live("click", function(){
			var projectId = $("#projectid").val();//项目id
			if(!projectId) {alert("该项目没有立项，无法添加成果！"); return;}
			var viewType = viewTypeMap["" + this.name],
				inspectionId = (viewType == 1) ? $("#anninspectionId").val() : (viewType == 2) ? $("#midInspectionId").val() : (viewType == 3) ? $("#endInspectionId").val() : "";
			popProductOperation({
				title : "添加成果",
				src : "product/toAdd.action?projectId=" + projectId + "&viewType=" + viewType + "&inspectionId=" + inspectionId,
				callBack : function(result){
					viewEndinspection.readEnd(projectType, showProjectDetails);
				}
			});
			return false;
		});
		
		//弹出层修改成果
		$("#view_mod_product").live("click", function() {
			var $context = $(this).parent("div").next();
			var checkedboxs = $("input[type='checkbox'][name='entityIds']:checked", $context);
			if(checkedboxs.length == 0) {
				alert("请选择要修改的成果！"); return;
			} else if(checkedboxs.length > 1) {
				alert("请选择单个成果！"); return;
			}
			var projectId = $("#projectid").val();//项目id
			var viewType = viewTypeMap[this.name],
				inspectionId = (viewType == 1) ? $("#anninspectionId").val() : (viewType == 2) ? $("#midInspectionId").val() : (viewType == 3) ? $("#endInspectionId").val() : "";
			var checkedbox = checkedboxs.eq(0);
			//判定成果能否被修改，正在引用不能被修改
			$.ajax({
				url: "product/checkModifyProduct.action",
				data: {"entityId" : checkedbox.val()},
				dataType: "json",
				success: function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						popProductOperation({
							title : "修改成果",
							src : "product/" + checkedbox.attr("alt") + "/toModify.action?entityId=" + checkedbox.val() + "&projectId=" + projectId + "&viewType=" + viewType + "&inspectionId=" + inspectionId,
							callBack : function(result){
								viewEndinspection.readEnd(projectType, showProjectDetails);
							}
						});
					} else {
						alert(result.errorInfo);
					}
				}
			});
			return false;
		});
		
		//删除成果与中检、结项、相关等关联
		$("#view_del_product").live("click", function(){
			var $context = $(this).parent("td").parent("tr").parent("tbody").parent("table").prev();
			var $checkEles = $("input[type='checkbox'][name='entityIds']:checked", $context);
			var viewType = viewTypeMap[this.name];
			if($checkEles.length == 0){
				alert("请选择要删除的成果！"); 
				return false;
			}
			
			//被年检、中检、结项引用的相关成果不能被删除
			if(viewType == 4) {
				var flag = true;
				$checkEles.each(function() {
					var isAnn = $("input[name='isAnn']", $(this).parent().next()).val();
					var isMid = $("input[name='isMid']", $(this).parent().next()).val();
					var isEnd = $("input[name='isEnd']", $(this).parent().next()).val();
					if(isAnn == 1 || isMid == 1 || isEnd == 1) {
						alert("不能删除年检、中检或结项成果！"); flag = false;
						return false;
					}
				});
				if(!flag) return false;
			}
			//删除成果
			if (confirm("您确定要删除成果吗？")) {
				$.ajax({
					url : "product/deleteProjectInfo.action",
					type : "post",
					data : (function(){
							var obj = {"viewType": viewType, "projectId": $("#projectid").val(), 
									"inspectionId" : viewType == 1 ? $("#anninspectionId").val() : (viewType == 2 ? $("#midInspectionId").val() : (viewType == 3 ? $("#endInspectionId").val() : ""))
							}
							for(var i = 0; i < $checkEles.length; i++) {
								obj["entityIds[" + i + "]"] = $($checkEles[i]).val();
							}
							return obj;
						})(),
					dataType : "json",
					success : function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
							viewEndinspection.readEnd(projectType, showProjectDetails);
						} else {
							alert(result.errorInfo);
						}
					}
				});
			}
			return false;
		});
		
		//批量审核成果
		$("#view_aud_product").live("click", function() {
			var $context = $(this).parent("td").parent("tr").parent("tbody").parent("table").prev();
			var checkIds = $("input[type='checkbox'][name='entityIds']:checked", $context);
			var checkpTypes = $("input[type='checkbox'][name='productTypes']:checked", $context);
			var viewType = viewTypeMap["" + this.name];
			if(checkIds.length == 0) {
				alert("请选择要审核的成果！"); return false;
			}
			if (confirm("您确定要审核这些成果吗？")) {
				var entityIds = [], productTypes = [];
				for(var i = 0; i < checkIds.length; i++) {
					entityIds.push($(checkIds[i]).val());
					productTypes.push($(checkpTypes[i]).val());
				}
				popProductOperation({
					title : "审核成果",
					src : "product/toAudit.action?exflag=1",
					callBack : function(result, layer){
						$.ajax({
							url : "product/audit.action",
							type : "post",
							data : (function(ids, types){
								var obj = {"auditResult" : result.auditResult, "viewType" : viewType, "projectId" : $("#projectid").val(), 
									"inspectionId" :  viewType == 1 ? $("#anninspectionId").val() : (viewType == 2 ? $("#midInspectionId").val() : (viewType == 3 ? $("#endInspectionId").val() : ""))};
								for(var i = 0; i < ids.length; i++) {
									obj["entityIds[" + i + "]"] = ids[i];
								}
								return obj;
							})(entityIds, productTypes),
							dataType : "json",
							success : function(result) {
								if (result.errorInfo == null || result.errorInfo == "") {
									viewEndinspection.readEnd(projectType, showProjectDetails);
								} else {
									alert(result.errorInfo);
								}
							}
						});
					}
				});
			}
		});
		
		//弹出层添加中检、结项成果
		$("#view_add_inspection_product").live("click", function(){
			var $this = this;
			popProductOperation({
				title : "添加成果",
				src : "product/toAddSelect.action",
				callBack : function(layer){
					var isSelect = layer.outData.isSelect;
					layer.destroy();
					if(isSelect == 1) {//从已有成果中选择
						
					} else {//添加一条新成果
						var projectId = $("#projectid").val();//项目id
						if(!projectId) {alert("该项目没有立项，无法添加成果！"); return;}
						var viewType = viewTypeMap["" + $this.name];
						popProductOperation({
							title : "添加成果",
							src : "product/toAdd.action?projectId=" + projectId + "&viewType=" + viewType,
							callBack : function(layer){
								viewEndinspection.readEnd(projectType, showProjectDetails);
								var productId = layer.outData.productId;
								alert(productId);
								layer.destroy();
							}
						});
					}
					
				}
			});
			return false;
		});
		
		//删除成果与中检、结项、相关等关联
		$("#list_del").live("click", function(){
			var $checkEles = $("input[type='checkbox'][name='entityIds']:checked", $context);
			var $checkpTypes = $("input[type='checkbox'][name='productTypes']:checked", $context);
			var viewType = (this.name == "mid_prod") ? 1 : ((this.name == "end_prod") ? 2 : 3);
			var inspectionId =  viewType == 1 ? $("#anninspectionId").val() : (viewType == 2 ? $("#midInspectionId").val() : (viewType == 3 ? $("#endInspectionId").val() : ""))
			if($checkEles.length == 0){
				alert("请选择要删除的结项成果！"); return false;
			}
			if (confirm("您确定要删除这些结项成果吗？")) {
				var entityIds = [], productTypes = [];
				for(var i = 0; i < $checkEles.length; i++) {
					entityIds.push($($checkEles[i]).val());
					productTypes.push($($checkpTypes[i]).val());
				}
				$.ajax({
					url : "product/deleteProjectInfo.action",
					type : "post",
					data : (function(ids, types){
						var obj = {"viewType" : viewType, "inspectionId" : inspectionId};
						for(var i = 0; i < ids.length; i++) {
							obj["entityIds[" + i + "]"] = ids[i];
							obj["productTypes[" + i + "]"] = productTypes[i];
						}
						return obj;
					})(entityIds, productTypes),
					dataType : "json",
					success : function(result){
						if (result.errorInfo == null || result.errorInfo == "") {
							viewEndinspection.readEnd(projectType, showProjectDetails);
						} else {
							alert(result.errorInfo);
						}
					}
				});
			}
			return false;
		});
		
		//点击全选
		$("#check").live("click", function() {
			checkAll(this.checked, "entityIds");
			checkAll(this.checked, "productTypes");
		});
		//选择成果
		$(".selectProduct").live("click",function(){
			if($(this).attr("checked") == false){
				$(this).parent().next().children(0).children(0).attr("checked", false);
			} else if($(this).attr("checked") == true){
				$(this).parent().next().children(0).children(0).attr("checked", true);
			}
		});
		
		//选定一条为最终成果
		$("#list_select_final_prod").live("click", function(){
			var $context = $(this).parent("td").parent("tr").parent("tbody").parent("table").prev();
			var $checkEle = $("input[type='radio'][name='entityId']:checked", $context);
			if($checkEle.length == 0){
				alert("请选择一条最终结项成果！"); return false;
			}
			$.ajax({
				url : "product/confirmFinalProduct.action",
				type : "post",
				data : {"entityId" : $checkEle.val(), "productType" : $checkEle.attr("alt"), 
						"projectType" : $("#project_type").val(), "inspectionId" : 	$("#endInspectionId").val()},
				dataType : "json",
				success : function(result){
					if (result.errorInfo == null || result.errorInfo == "") {
						viewEndinspection.readEnd(projectType, showProjectDetails);
					} else {
						alert(result.errorInfo);
					}
				}
			});
		});
		
		//单条审核
		$(".add_audit").live("click",function(){
			popProductOperation({
				title : "审核成果",
				inData : {"productId" : $(this).attr("id"), "productType" : $(this).attr("name")},
				src : "product/toAudit.action?exflag=1",
				callBack : function(result, layer){
					$.ajax({
						url: "product/audit.action",
						type: "post",
						data: "auditResult=" + result.auditResult + "&entityIds=" + layer.inData.productId + "&productTypes=" + layer.inData.productType,
						dataType: "json",
						success: function(result, layer){
							if (result.errorInfo == null || result.errorInfo == "") {
								viewEndinspection.readEnd(projectType, showProjectDetails);
							} else {
								alert(result.errorInfo);
							}
						}
					});
				}
			});
			return false;
		});
		
		//批量审核
		$("#list_audit").live("click", function() {
			var $context = $(this).parent("td").parent("tr").parent("tbody").parent("table").prev();
			var checkIds = $("input[type='checkbox'][name='entityIds']:checked", $context);
			var checkpTypes = $("input[type='checkbox'][name='productTypes']:checked", $context);
			if(checkIds.length == 0) {
				alert("请选择要审核的成果！"); return false;
			}
			if (confirm("您确定要审核这些成果吗？")) {
				var entityIds = [], productTypes = [];
				for(var i = 0; i < checkIds.length; i++) {
					entityIds.push($(checkIds[i]).val());
					productTypes.push($(checkpTypes[i]).val());
				}
				popProductOperation({
					title : "审核成果",
					src : "product/toAudit.action?exflag=1",
					callBack : function(result, layer){
						$.ajax({
							url : "product/audit.action?auditResult=" + result.auditResult,
							type : "post",
							data : (function(ids, types){
								var obj = {};
								for(var i = 0; i < ids.length; i++) {
									obj["entityIds[" + i + "]"] = ids[i];
									obj["productTypes[" + i + "]"] = productTypes[i];
								}
								return obj;
							})(entityIds, productTypes),
							dataType : "json",
							success : function(result) {
								if (result.errorInfo == null || result.errorInfo == "") {
									viewEndinspection.readEnd(projectType, showProjectDetails);
								} else {
									alert(result.errorInfo);
								}
							}
						});
					}
				});
			}
		});
	};
	
	var getProduct = function(productId, projectType, showProjectDetails) {
		$.ajax({
			url : "product/getSelectedProduct.action",
			type : "post",
			data : (function(ids, types){
				var obj = {"auditResult" : result.auditResult, "viewType" : viewType, "projectId" : $("#projectid").val(), 
					"inspectionId" : viewType == 1 ? $("#anninspectionId").val() : (viewType == 2 ? $("#midInspectionId").val() : (viewType == 3 ? $("#endInspectionId").val() : ""))};
				for(var i = 0; i < ids.length; i++) {
					obj["entityIds[" + i + "]"] = ids[i];
					obj["productTypes[" + i + "]"] = productTypes[i];
				}
				return obj;
			})(entityIds, productTypes),
			dataType : "json",
			success : function(result) {
				if (result.errorInfo == null || result.errorInfo == "") {
					viewEndinspection.readEnd(projectType, showProjectDetails);
				} else {
					alert(result.errorInfo);
				}
			}
		});
	};
	
	exports.init = function(projectType, showProjectDetails) {
		initLive(projectType, showProjectDetails);
//		getProduct(productId, projectType, showProjectDetails);
	};
});