define(function(require, exports, module) {
	require('tool/tree/js/dhtmlxcommon');
	require('tool/tree/js/dhtmlxtree');
	var view = require('javascript/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "role";
	

	$("#view_cop").live("click", function() {
		window.location.href = basePath + nameSpace + "/toCopy.action?entityId=" + $("#entityId").val();
		return false;
	});
	
	
	var showDetails = function(result) {// 显示查看内容
		if (result.errorInfo == null || result.errorInfo == "") {
			$("#entityId").attr("value", result.role.id);
			$("#entityIds").attr("value", result.role.id);
			$("#view_container").hide();
			result.ministry = [];
			result.province = [];
			result.university = [];
			result.ministry_length = 0;
			result.province_length = 0;
			result.university_length = 0;
			if (result.type == 2) {// 机构默认角色
				var defaultAgencyId = result.role.defaultAgencyId;
				var defaultAccountType = result.role.defaultAccountType;
				var ministryId = result.ministryId;
				var ministryName = result.ministryName;
				var provinceId = result.provinceId;
				var provinceName = result.provinceName;
				var universityId = result.universityId;
				var universityName = result.universityName;
				if (ministryId != "" && ministryName != "") {
					ministryId = ministryId.split("; ");
					ministryName = ministryName.split("; ");
				}
				if (provinceId != "" && provinceName != "") {
					provinceId = provinceId.split("; ");
					provinceName = provinceName.split("; ");
				}
				if (universityId != "" && universityName != "") {
					universityId = universityId.split("; ");
					universityName = universityName.split("; ");
				}
				result.ministry_length = ministryId.length;
				result.province_length = provinceId.length;
				result.university_length = universityId.length;
				var ministryLength = ministryId.length;
				var provinceLength = provinceId.length;
				var universityLength = universityId.length;
				for (var i = 0; i < ministryLength; i++) {
					result.ministry[i] = {id:ministryId[i], name:ministryName[i]};
				}
				for (var i = 0; i < provinceLength; i++) {
					result.province[i] = {id:provinceId[i], name:provinceName[i]};
				}
				for (var i = 0; i < universityLength; i++) {
					result.university[i] = {id:universityId[i], name:universityName[i]};
				}
			}
			$("#view_container").html(TrimPath.processDOMTemplate("view_template", result));
			if (result.type == 1) {
				$("#accountType").show();
				$("#agencyId").hide();
				$("#unDefault").hide();
				// 设置角色分配类别的默认选中
				if (result.role.defaultAccountType != "") {
					$("#accountType").find("input[type='checkbox']").each(function(index) {
						var tmp = result.role.defaultAccountType.substr(index, 1);
						this.checked = (tmp == 1 ? true : false);
					});
				}
			} else if (result.type == 2) {
				var defaultType = result.role.defaultAccountType;
				if (defaultType == "11" || defaultType == "10") {
					$("#main").attr("checked", true);
				}
				if (defaultType == "11" || defaultType == "01") {
					$("#sub").attr("checked", true);
				}
				$("#agencyId").show();
				$("#accountType").hide();
				$("#unDefault").hide();
			} else {
				$("#agencyId").hide();
				$("#accountType").hide();
				$("#unDefault").show();
			}
			$("#view_container").show();
			viewTree();
		} else {
			alert(result.errorInfo);
		}
		if (parent != null) {
			parent.loading_flag = false;
			parent.hideLoading();
		}
	};
	
	var viewTree = function() {// 加载树
		$("#treeparent").html("<div id='treeId'></div>");
		tree2 = new dhtmlXTreeObject("treeId", "100%", "100%", 0);
		tree2.setSkin('dhx_skyblue');
		tree2.setImagePath(basePath+"/image/tree/csh_bluebooks/"); // 设定图片的路径
		tree2.enableCheckBoxes(0); // 1：为有复选框(做权限)  0：为没有复选框(做部门管理的时候) 
		tree2.enableThreeStateCheckboxes(false); // tree: 假如选中了上级下级也全部选中   false:上级与下级的选择状态无关，不关联
		tree2.setOnClickHandler(null);  // 单击树的时候，做的操作  tonclick为点击的方法名 (类似于回调函数)
		tree2.loadXML("role/createRightTree.action?entityId=" + $("#entityId").val());
	};
	
	exports.init = function() {
		view.show(nameSpace, showDetails);
		view.add(nameSpace);
		view.mod(nameSpace);
		view.del(nameSpace, "您确定要删除此角色吗？");
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		view.back(nameSpace);
		$(".link2").live("click", function() {
			popAccount(this.id);
			return false;
		});
		$(".link3").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
	};
});