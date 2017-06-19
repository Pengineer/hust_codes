define(function(require, exports, module) {
	require('tool/tree/js/dhtmlxcommon');
	require('tool/tree/js/dhtmlxtree');
	var datepick = require("datepick-init");
	require('validate');
	var validate = require('javascript/security/role/validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	/**
	 * 根据编辑的角色类别信息，设置页面对应的输入区域的显示
	 * @param {Object} obj 单选框对象
	 */
	var setRoleType = function(obj) {
		var value = obj.value;
		if (value == 1) {
			$("#accountType").show();
			$("#agencyId").hide();
		} else if (value == 2) {
			$("#accountType").hide();
			$("#agencyId").show();
		} else {
			$("#accountType").hide();
			$("#agencyId").hide();
		}
	};
	$("#role_list").find("input").bind("click", function() {
		var that = this;
		if (that.checked == true) {
			$("#treeId").empty();
			$("#rightTree").show();
			$("#mainRoleId").val(that.id);
			rightTree(that);
		} else {
			$("#rightTree").hide();
		}
	});
	/*
	 * 过滤权限树
	 */
	$("#button_query").live("click", function(){
		$("#treeId").empty();
		$("#rightTree").show();
		var keyword = $("#keyword").val();
		var url = "role/createRightTree.action?keyword="+keyword;
		tree2 = new dhtmlXTreeObject("treeId", "100%", "100%", 0);
		tree2.setSkin('dhx_skyblue');
		tree2.setImagePath(basePath+"/image/tree/csh_bluebooks/"); // 设定图片的路径
		tree2.enableCheckBoxes(1); // 1：为有复选框(做权限)  0：为没有复选框(做部门管理的时候) 
		tree2.enableThreeStateCheckboxes(1); // tree: 假如选中了上级下级也全部选中   false:上级与下级的选择状态无关，不关联
		tree2.setOnClickHandler(tonclick);  // 单击树的时候，做的操作  tonclick为点击的方法名 (类似于回调函数)
		tree2.loadXML(encodeURI(encodeURI(url)));
		var tonclick = function(id) { // id 直接就是这颗树的id
			$("#text").attr("value", tree2.getItemText(id));// tree2.getItemText(id)
			$("#id").attr("value", id);
		};
	});
	
	var rightTree = function(obj) {// 获得权限树
		var mainRoleId = obj.id;
		tree2 = new dhtmlXTreeObject("treeId", "100%", "100%", 0);
		tree2.setSkin('dhx_skyblue');
		tree2.setImagePath(basePath+"/image/tree/csh_bluebooks/"); // 设定图片的路径
		tree2.enableCheckBoxes(1); // 1：为有复选框(做权限)  0：为没有复选框(做部门管理的时候) 
		tree2.enableThreeStateCheckboxes(1); // tree: 假如选中了上级下级也全部选中   false:上级与下级的选择状态无关，不关联
		tree2.setOnClickHandler(tonclick);  // 单击树的时候，做的操作  tonclick为点击的方法名 (类似于回调函数)
		tree2.loadXML("role/createRightTree.action?mainRoleId="+mainRoleId);
		var tonclick = function(id) { // id 直接就是这颗树的id
			$("#text").attr("value", tree2.getItemText(id));// tree2.getItemText(id)
			$("#id").attr("value", id);
		};
	};
	
	var getRightTree = function() {// 获得权限树
		tree2 = new dhtmlXTreeObject("treeId", "100%", "100%", 0);
		tree2.setSkin('dhx_skyblue');
		tree2.setImagePath(basePath+"/image/tree/csh_bluebooks/"); // 设定图片的路径
		tree2.enableCheckBoxes(1); // 1：为有复选框(做权限)  0：为没有复选框(做部门管理的时候) 
		tree2.enableThreeStateCheckboxes(1); // tree: 假如选中了上级下级也全部选中   false:上级与下级的选择状态无关，不关联
		tree2.setOnClickHandler(tonclick);  // 单击树的时候，做的操作  tonclick为点击的方法名 (类似于回调函数)
		tree2.loadXML("role/createRightTree.action");
		var tonclick = function(id) { // id 直接就是这颗树的id
			$("#text").attr("value", tree2.getItemText(id));// tree2.getItemText(id)
			$("#id").attr("value", id);
		};
	};
	
	
	var getnn = function() {// 生成角色拥有权限字符串
		var tree = tree2.getAllChecked();
		tree = tree.split(',');
		var str = '';
		for (var i = 0; i < tree.length; i++) {
			if (tree2.getOpenState(tree[i]) == 0)
				str+=tree[i]+',';
		}
		str = str.substr(0, str.length-1);
		return str;
	};
	
	var getAccountType = function() {// 生成角色所属账号类型字符串
		var accountType = "";
		$("#accountType").find("input").each(function() {
			accountType += (this.checked == true ? 1 : 0);
		});
		return accountType;
	};
	
	var getApplyTo = function() {// 获取给机构创建角色时，应用到的账号类别
		var applyTo = "";
		$("#applyTo").find("input[type='checkbox']").each(function() {
			applyTo += (this.checked == true ? 1 : 0);
		});
		return applyTo;
	};
	
	var getAgencyId = function() {// 生成角色所属机构ID字符串
		var ministry = $("#ministry_id").val();
		var province = $("#province_id").val();
		var university = $("#university_id").val();
		ministry = (ministry == null || ministry == "") ? "" : (ministry + "; ");
		province = (province == null || province == "") ? "" : (province + "; ");
		university = (university == null || university == "") ? "" : (university + "; ");
		var agencyId = ministry + province + university;
		agencyId = (agencyId == "" ? "" : agencyId.substring(0, agencyId.length - 2));
		return agencyId;
	};
	
	// 绑定选择机构事件
	var setParamAndCallBack = function(id, name, type) {// 设置弹出层相关参数、回调函数等
		var param = {"data":{"id":"", "name":""}};
		var param_id = $("#" + id).val();
		var param_name = $("#" + name).html();
		if (param_id != null && param_id != "") {
			param.data.id = param_id.split("; ");
		} else {
			param.data.id = [];
		}
		if (param_name != null && param_name != "") {
			param.data.name = param_name.split("; ");
		} else {
			param.data.name = [];
		}
		var callBackFunction = function(result) {
			var result_id = result.data.id;
			var result_name = result.data.name;
			if (result_id != null) {
				result_id = result_id.join("; ");
			}
			if (result_name != null) {
				result_name = result_name.join("; ");
			}
			$("#" + id).val(result_id);
			$("#" + name).empty().html(result_name);
			$("#" + name).next().val(result_name);
		};
		popSelectGroup({
			type : type,
			inData : param,
			outData : param,
			callBack : function(result){
				callBackFunction(result);
			}
		});
	};
	
	var doSubmit = function() {// 提交表单前，给页面隐藏表单赋值
		$("#rightNodeValues").val(getnn());// 设置权限节点字符串
		// 获取当前角色的类别
		var type = 0;
		$("#type").find("input").each(function() {
			var that = this;
			if (that.checked == true) {
				type = that.value;
				return false;
			}
		});
		if (type == 1) {// 创建账号默认角色
			$("#defaultAccountType").val(getAccountType());// 设置账号类型字符串
			$("#defaultAgencyIds").val("");// 置空机构ID字符串
			return true;
		} else if (type == 2) {// 创建机构默认角色
			var applyTo = getApplyTo();
			var agencyId = getAgencyId();
			if (applyTo == "00") {// 如果没有选择应用到的账号类型
				alert("请选择应用到的账号类型");
				return false;
			} else if (agencyId == "") {// 如果没有选择机构
				alert("请给角色选择至少一个应用到的机构");
				return false;
			} else {
				$("#defaultAccountType").val(applyTo);// 设置账号类型字符串
				$("#defaultAgencyIds").val(agencyId);// 设置机构ID字符串
				return true;
			}
		} else {// 创建非默认角色
			$("#defaultAccountType").val("");// 设置账号类型字符串
			$("#defaultAgencyIds").val("");// 设置机构ID字符串
			return true;
		}
	};

	exports.init = function() {
		var accountType = $("#accountTypes").val();
		var isPrincipal = $("#isPrincipal").val();
		if(accountType == "ADMINISTRATOR" ){//系统管理员
			$("#RoleType").show();
			$("#DefaultMinistry").show();
			$("#DefaultProvince").show();
			$("#DefaultUniversity").show();
			$("#MainSubAccount").show();
			$("#rightTree").show();
			$("#ownRole").hide();
			getRightTree();
		}
		validate.valid();
		datepick.init();
		// 绑定角色类别单选框点击事件
		$("#type").find("input").bind("click", function() {
			var that = this;
			setRoleType(that);
		});
		$("#ministry").bind("click", function() {
			setParamAndCallBack("ministry_id", "ministry_name",1);
		});
		$("#province").bind("click", function() {
			setParamAndCallBack("province_id", "province_name",2);
		});
		$("#university").bind("click", function() {
			setParamAndCallBack("university_id", "university_name",3);
		});
		// 页面初始载入，设置默认添加的角色类型对应的表单
		var type = 0;
		$("#type").find("input").each(function() {
			var that = this;
			if (that.checked == true) {
				type = that.value;
				setRoleType(that);
				return false;
			}
		});
		var parentId = $("#parentId").val();
		$("#role_list").find("input").each(function() {
			var rolId = this.id
			if (parentId == rolId) {
				this.checked = true;
				$("#rightTree").show();
				$("#mainRoleId").val(parentId);
				rightTree(this);
			}
		});
		
		var defaultAccountType = $("#defaultAccountType").val();
		if (defaultAccountType != undefined && defaultAccountType != null && defaultAccountType != "") {
			if (type == 1) {// 页面初始载入，设置按账号类型创建角色时的默认选中
				$("#accountType").find("input").each(function(index) {
					var tmp = defaultAccountType.substr(index, 1);
					this.checked = (tmp == 1 ? true : false);
				});
			} else {// 页面初始载入，设置按所属机构创建角色时的默认值
				// 通过JS生成的相关不同机构表单信息，难以重建，暂不予实现
				// 设置默认应用到的账号类型
				$("#applyTo").find("input").each(function(index) {
					var tmp = defaultAccountType.substr(index, 1);
					this.checked = (tmp == 1 ? true : false);
				});
			}
		}
		$("#submit").click(function(){
			doSubmit();
		});
		$("#save").live("click", function(){
			doSubmit();
			$("#saveOrSubmit").val(1);
		});
	};

});
