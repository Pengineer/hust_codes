/**
 * 用于修改奖励申请
 * @author 王燕
 */
define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/awardService');
	require('dwr/interface/personExtService');
	var edit = require("javascript/award/moesocial/application/edit");
	var datepick = require("datepick-init");
	var validate = require('javascript/award/moesocial/application/apply/validate');
	require('validate');
	require('cookie');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/step_tools');
	require('uploadify');
	require('uploadify-ext');
	
	var pop = function(){//初始化事件绑定
		$("#select_dep_btn").live("click",function() {// 绑定弹出层选择院系
			popSelect({
				type : 4,
				label : 3,
				inData : {"id":$("#departmentId").val(), "name":$("#unit").html()},
				callBack : function(result){
					$("#departmentId").val(result.data.id);
					$("#instituteId").val("");
					$("#unit").html(result.data.name);
					$("#unitName").val(result.data.name);
					$("#deptInstFlag").val(2);
				}
			});
		});
		doWithXX($("[name=academic.discipline]").val(), $("#selectDiscipline").next().attr("id"), $("#selectDiscipline").next().next().attr("id"));
		$("#selectDiscipline").click(function(){//以个人名义申请绑定学科代码弹出层
			var relyDispId = $(this).next();
			popSelect({
				type : 12,
				inData : relyDispId.attr("value"),
				callBack : function(result){
					doWithXX(result, relyDispId.attr("id"), relyDispId.next().attr("id"));
					$(document.forms[0]).valid();
				}
			});
		});
		$("#selectOrNot").live("change",function(){//团队名称是选择还是输入
			if($(this).val() == 1){
				$("#applicant_name").html($("#applicantName").val());
				$("#select_yes").show();
				$("#select_no").hide();
			}else if($(this).val() == 0){
				$("#select_yes").hide();
				$("#select_no").show();
			}else{
				$("#select_yes").hide();
				$("#select_no").hide();
			}
		});
		$("#select_organ_btn").live("change", function(){
			var organId = $(this).val();
			$.ajax({
				url: "award/moesocial/application/apply/fetchOrganInfo.action",
				type: "post",
				data: "organId=" + organId,
				dataType: "json",
				success: function(result) {
					if (result.errorInfo == null || result.errorInfo == "") {
						//获取数据填充团队信息
						$("#unit").html(result.organ.agencyName + " " + result.organ.divisionName);
						$("#instituteId").val(result.instituteId);
						$("#departmentId").val(result.departmentId);
						$("#unitName").val(result.organ.agencyName);
						$("#disp").html(result.organ.discipline);
						$("#disciplineId").val(result.organ.discipline);
						$("input[name=organization.officeAddress]").val(result.organ.officeAddress);
						$("input[name=organization.officePhone]").val(result.organ.officePhone);
						$("input[name=organization.officePostcode]").val(result.organ.officePostcode);
						$("input[name=organization.mobilePhone]").val(result.organ.mobilePhone);
						$("input[name=organization.email]").val(result.organ.email);
						$("input[name=organization.member]").val(result.organ.member);
						//初始化团队成果
						var ptype=document.getElementById('ptypeid').value;
						selectProduct(ptype);
						return false;
					} else {
						alert(result.errorInfo);
					}
				}
			});
		});
		$("#select_dep_btn").live("click", function() {// 以团队名义申请绑定弹出层选择院系
			popSelect({
				type : 4,
				label : 3,
				inData : {"id":$("#departmentId").val(), "name":$("#unit").html()},
				callBack : function(result){
					$("#departmentId").val(result.data.id);
					$("#instituteId").val("");
					$("#unit").html(result.data.name);
					$("#unitName").val(result.data.name);
					$("#deptInstFlag").val(2);
				}
			});
		});
		$("#select_ins_btn").live("click", function() {//以团队名义申请 绑定弹出层选择基地
			popSelect({
				type : 5,
				label : 3,
				inData : {"id":$("#instituteId").val(), "name":$("#unit").html()},
				callBack : function(result){
					$("#instituteId").val(result.data.id);
					$("#departmentId").val("");
					$("#unit").html(result.data.name);
					$("#unitName").val(result.data.name);
					$("#deptInstFlag").val(1);
				}
			});
		});
		$("#organSelectDiscipline").click(function(){//以团队名义申请绑定弹出层学科代码
			popSelect({
				type : 12,
				inData : $("#disciplineId").val(),
				callBack : function(result){
					doWithXX(result, "disciplineId", "disp");
					$("#disciplineId").valid();
				}
			});
		});
		$("#download_award_model").live("click", function(){//离线奖励申报时下载人文社科奖申请书模板
			window.location.href = basePath + 'award/moesocial/application/apply/downloadModel.action';
			return false;
		});
	};
	/*-----------------------以下为在线奖励申报的成果部分--------------------------------*/
	var initModifyApply = function(){//成果信息部分，初始化奖励申请(用于修改奖励申请)
		//获取申请届次
		edit.loadSession('session1');
		$("#session1").val($("#defualSession").val());
	    //获取奖励申请方式
		var applicationType = $("input[name='awardApplication.applicationType'][type='radio']:checked").val();
		edit.app(applicationType);
		//获取申请的团队名称
		$("#selectOrNot").val(1);
		$("#select_yes").show();
		$("#select_no").hide();
	};
	var addProduct = function(){// 弹出层添加成果  viewType=4
		popAwardOperation({
			title : "添加成果",
			src : "product/toAdd.action?viewType=4&organization.id=" + $("select[name='organization.id']").val(),
			callBack : function(result){
				initNewApply(result);
			}
		});
		return false;
	};
	var callbackPro1 = function(data){
		DWRUtil.removeAllOptions('product1');
		DWRUtil.removeAllOptions('dtype');
		DWRUtil.addOptions('dtype', [{name:'--请选择--',id:'-1'}],'id','name');
		DWRUtil.addOptions('product1', [{name:'--请选择--',id:'-1'}],'id','name');
		DWRUtil.addOptions('product1', data);
	};
	var selectProduct = function(producttype){//列出奖励申请者对应成果形式的成果列表
		var personid = $("#personid").val();
		var organizationId = $("#select_organ_btn").val();
		// Dwr 同步通信方式
		DWREngine.setAsync(false);
		awardService.getProduct(producttype, personid, organizationId, callbackPro1);
		// Dwr 异步通信方式
		DWREngine.setAsync(true);
	};
	var selectDtype = function(){//列出所选成果的学科门类列表 
		var product = document.getElementById('product1').value;
		var ptype = $("input[name='resultType'][type='radio']:checked").val();
		if(product != '-1'){
		    awardService.getDtype(ptype, product, callbackPro);
		}
		else{
			DWRUtil.removeAllOptions('dtype');
			DWRUtil.addOptions('dtype', [{name:'--请选择--',id:'-1'}],'id','name');
		}
	};
	var callbackPro = function(data){
		DWRUtil.removeAllOptions('dtype');
		DWRUtil.addOptions('dtype', [{name:'--请选择--',id:'-1'}],'id','name');
		DWRUtil.addOptions('dtype', data);
	};
	var initNewApply = function(info){//弹出层添加成果后初始化添加奖励申请的页面
		$("input[name='existOrNot'][value=1][type='radio']").attr("checked", true);
		$("input[name='resultType'][value="+info.productType+"][type='radio']").attr("checked", true);
		edit.addOrNot(1);
		selectProduct(info.productType);
		$('#product1').attr("value", info.productId);
		selectDtype();
	};
	var submitOrNot = function(data){//是否提交奖励申请
		var existOrNot = $("input[name='existOrNot'][type='radio']:checked").val();
		var product = document.getElementById("product").value;
		if(existOrNot == 0 && product == '-1'){
			alert("请添加成果！");
			return;
		}
		if(data==3){
			if( !confirm('提交后无法修改，是否确认提交？'))
				return ;
		}
		$("#applicantSubmitStatus").val(data);
		$("#application_form").submit();
	};
	var saveOrNotModify = function(data){//是否提交奖励申请的修改
		if(data==3){
			if( !confirm('提交后无法修改，是否确认提交？'))
				return ;
		}
		$("#applicantSubmitStatus2").val(data);
		$("#application_form").submit();
	};
	var addProduct = function(){// 弹出层添加成果  viewType=4
		popAwardOperation({
			title : "添加成果",
			src : "product/toAdd.action?viewType=4&organization.id=" + $("select[name='organization.id']").val(),
			callBack : function(result){
				initNewApply(result);
			}
		});
		return false;
	};
	/*----------------------------------结束---------------------------------------------*/
	
	exports.initClick = function() {
		
		$(function() {
			var awardApplicationId =  $("#awardApplicationId").val();
			$("#file_"+awardApplicationId).uploadifyExt({
				uploadLimitExt : 1,
				fileSizeLimit : '100MB',
				fileTypeDesc : '附件'
			});
		});
		
		window.addProduct = function(){addProduct()};
		window.selectDtype = function(){selectDtype()};
		
		$("input[name='existOrNot'][type='radio']").click(function(){
			var data = $("input[name='existOrNot'][type='radio']:checked").val();
			edit.applyType(data);
		});
		
		//添加成果
		$("#add_product").live("click", function(){
			addProduct();
		});
		
		$(".j_addSaveModify").live("click", function(){
			saveOrNotModify(2);
		});

		$(".j_addSubmitModify").live("click", function(){
			saveOrNotModify(3);
		});
		
		$(".j_addProduct").live("click", function(){
			addProduct();
		});
		
		$("input[name='resultType'][type='radio']").click(function(){
			var producttype = $("input[name='resultType'][type='radio']:checked").val();
			selectProduct(producttype);
		});
		
		$("input[name='addExistOrNot'][type='radio']").click(function(){
			var data = $("input[name='addExistOrNot'][type='radio']:checked").val();
			edit.addOrNot(data);
		});
		
		$("input[name='resultType'][type='radio']").click(function(){
			var producttype = $("input[name='resultType'][type='radio']:checked").val();
			selectProduct(producttype);
		});
		
//		window.app = function(data){edit.app(data)};
//		window.selectProduct = function(producttype){selectProduct(producttype)};
		window.selectDtype = function(){selectDtype()};
	};
	exports.init = function() {
		$(function() {
			datepick.init();
			validate.valid();
			pop();
			initModifyApply();
			step_controller = new Step_controller();
			step_controller.after_move_opt = function(){//确定标签样式
				var flag = false;
				for (step in step_controller.steps){
					var $curLi = $("li[name=" + step_controller.steps[step].id + "]");
					if (step_controller.steps[step].id == step_controller.state){
						flag = true;
						$curLi.attr("class", "proc step_d");
					} else if (!flag){
						$curLi.attr("class", "proc step_e");
					} else {
						$curLi.attr("class", "proc step_f");
					}
				}
			}
			var apply_setting = new Setting({
				id: "apply",
				buttons: ['next', 'cancel'],
				out_check: function(){
					var applicationType = $("input[name='awardApplication.applicationType'][type='radio']:checked").val();
	//				validate.valid_apply(applicationType);
					var res1 = $("#application_form").valid();
					return res1;
				},
				on_in_opt: function(){
					var applicationType = $("input[name='awardApplication.applicationType'][type='radio']:checked").val();
	//				validate.valid_apply(applicationType);
				}
			});
			var product_setting = new Setting({
				id: "product",
				buttons: ['prev', 'save1', 'finish','cancel'],
				out_check: function(){
					var res1 = $("#application_form").valid();
					return res1;
				},
				on_in_opt: function(){
	//				validate.valid_product();
				}
			});
			step_controller.add_step(apply_setting);
			step_controller.add_step(product_setting);
			step_controller.form_id = "application_form";
			step_controller.init();
			
			$("li.proc").click(function(){
				step_controller.move_to($(this).attr('name'));
			});
			$("#prev").click(function(){
				step_controller.prev();
			});
			$("#next").click(function(){
				step_controller.next();
			});
			$("#cancel").click(function(){
				history.go(-1);
			});
			$("#save1").click(function(){
				saveOrNotModify(2);//暂存
			});
			$("#finish").click(function(){
				saveOrNotModify(3);//提交
			});
			$("#info").show();
		});
		

		/**
		 * 根据已选择的专业职称获取对应职称子类的列表
		 * @return
		 */
		var selectSubType = function(){
			var id = $("#title").val();
			if(id && id != "" && id != -1){
				personExtService.getCodeNameMapByParentId(id, callbackSubType);
			}else{
				DWRUtil.removeAllOptions("subTitle");
				DWRUtil.addOptions('subTitle',[{name:'请选择二级职称',id:'-1'}],'id','name');
			}
		};

		var callbackSubType = function(data) {
			DWRUtil.removeAllOptions("subTitle");
			DWRUtil.addOptions('subTitle',[{name:'请选择二级职称',id:'-1'}],'id','name');
			DWRUtil.addOptions('subTitle',data);
		};
		
		/**
		 * 根据专业职称一级菜单的初始值来初始化二级职称菜单
		 */
		var displaySubType = function(data){
			DWRUtil.removeAllOptions("subTitle");
			DWRUtil.addOptions('subTitle', [{name:'请选择业务子类',id:''}],'id','name');
			DWRUtil.addOptions('subTitle', data);
			DWRUtil.setValue('subTitle', $("#subTitleId").val());
		};
		
		//初始化二级职称下拉列表
		var selectId = $("#title").val();
		if (selectId && selectId !='-1'){
			personExtService.getCodeNameMapByParentId(selectId, displaySubType);
		}
		
		
		//监听专业职称下拉列表变化事件
		$(".j_selectSubType").live('change',function(){
			selectSubType();
		});
	};
});
