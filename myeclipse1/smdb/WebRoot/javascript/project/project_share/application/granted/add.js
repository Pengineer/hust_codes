define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/projectService');
	require('jquery-ui');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/step_tools');
	var datepick = require("datepick-init");
	
	var validGra = require('javascript/project/project_share/application/validate');
	var editGra = require('javascript/project/project_share/application/granted/edit');
	
	//绑定弹出层选择
	var initPopLayer = function(){
		$(function() {
			//是否全选成果类别
			$("#checkAllProductTypeItem").live("click",function() { 
				checkAll(this.checked, 'application1.productType');
				editGra.showOtherProductTypeOrNot(this.checked);
			});
			
			//判断是否全选成果类别
			$("input[name='application1.productType'][type='checkbox']").live("click",function() { 
				checkAllOrNot(this.checked, 'application1.productType', 'checkAllProductTypeItem'); 
				if(this.value == '其他'){//如果是点击其他成果类别
					editGra.showOtherProductTypeOrNot(this.checked, this.value);
				}
			});
			
			//弹出层选择成员所在高校
			$("[name='selectUniversity']").live("click", function(){
				var $table = $(this).parent().parent().parent().parent();
				var memType = $("[name$='memberType']", $table).val();
				if(memType == '-1'){
					alert("请选择成员类型");
					return false;
				}
				popSelect({
					type : 3,
					label : 2,
					title : "选择高校",
					inData : {
						"id" : $("[name$='.university.id']", $table).val(),
						"name" : $("[name$='.agencyName']", $table).val()
					},
					callBack : function(result){
						$("[name$='.university.id']", $table).val(result.data.id);
						$("[name$='.agencyName']", $table).val(result.data.name);
						$("[name='universityNameDiv']", $table).html(result.data.name);
					}
				});
			});
			
			$("#select_dep_btn").live("click",function() {// 绑定弹出层选择院系
				popSelect({
					type : 4,
					label : 3,
					inData : {"id":$("#departmentId").val(), "name":$("#unit").html(), "type" : -1},
					callBack : function(result){
						$("#departmentId").val(result.data.id);
						$("#instituteId").val("");
						$("#unit").html(result.data.name);
						$("#unitName").val(result.data.name);
						$("#deptInstFlag").val(2);
					}
				});
			});
			
			$("#select_ins_btn").live("click",function() {// 绑定弹出层选择基地
				popSelect({
					type : 5,
					label : 3,
					inData : {"id":$("#instituteId").val(), "name":$("#unit").html(), "type" : -1},
					callBack : function(result){
						$("#instituteId").val(result.data.id);
						$("#departmentId").val("");
						$("#unit").html(result.data.name);
						$("#unitName").val(result.data.name);
						$("#deptInstFlag").val(1);
					}
				});
			});
			
			$("[name='addType']").live("change", function(){
				if($(this).val() == 1){
					$("#type_select").show();
					$("#type_new").hide();
				}else if($(this).val() == 2){
					$("#type_select").hide();
					$("#type_new").show();
				}
			});
			
			$("#select_ungranted_project").click(function(){
				popSelect({
					type : 18,
					inData : $("#applicationId").val(),
					proType : 1,
					callBack : function(result){
						$("#applicationId").val(result.data.id);
						$("#project_div").html(result.data.name);
					}
				});
			});
			
			$("#selectDisciplineType").click(function(){
				popSelect({
					type : 11,
					inData : $("#discipline").val(),
					callBack : function(result){
						doWithXX(result, "discipline", "disptr");
						$("#discipline").valid();
					}
				});
			});
			
			$("#selectDiscipline").click(function(){
				popSelect({
					type : 12,
					inData : $("#disciplineId").val(),
					callBack : function(result){
						doWithXX(result, "disciplineId", "disp");
						$("#disciplineId").valid();
					}
				});
			});
			
			$("#selectRelativeDiscipline").click(function(){
				popSelect({
					type : 12,
					inData : $("#relativeDisciplineId").val(),
					callBack : function(result){
						doWithXX(result, "relativeDisciplineId", "rdsp");
						$("#relativeDisciplineId").valid();
					}
				});
			});
		});
	};
	
	var init = function(projectType) {
		$(function() {
			datepick.init();
			initPopLayer();
			step_controller = new Step_controller();
			step_controller.form_id = "application_form";
			//确定标签样式
			step_controller.after_move_opt = function(){
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
			};
			var apply_setting = new Setting({
				id: "apply",
				buttons: ['next', 'cancel'],
				out_check: function(){
					var res1 = $("#application_form").valid();
//					var res2 = valid_prodType();
//					return res1 && res2;//执行全部校验后再返回结果
					return res1;
				},
				on_in_opt: function(){
					validGra.valid_apply();
				}
			});
			var granted_setting = new Setting({
				id: "granted",
				buttons: ['prev', 'next','cancel'],
				out_check: function(){
					var res1 = $("#application_form").valid();
					//校验批准号唯一
					var $radio = $("[name='application1.finalAuditResult']");
					if($radio==undefined || $radio==null || $radio.length<1){//无是否立项单选框
						var res2 = validGra.valid_number(projectType);
					}else{//存在是否立项单选框
						var $isCheck = $radio.filter('[value="2"]');
						if($isCheck.attr("checked") == true){//需校验批准号唯一
							var res2 = validGra.valid_number(projectType);
						}else{//不需校验批准号唯一
							var res2 = true;
						}
					}
					return res1 && res2;//执行全部校验后再返回结果
				},
				on_in_opt: function(){
//					validGra.valid_granted();
				}
			});
			var member_setting = new Setting({
				id: "member",
				buttons: ['prev', 'finish','cancel'],
				out_check: function(){
					var res1 = $("#application_form").valid();
					var res2 = editGra.checkMember();
					return res1 && res2;
				},
				on_in_opt: function(){
					validGra.valid_member();
				},
				on_submit_opt: function(){
					editGra.memberNum();
				}
			});
			step_controller.add_step(apply_setting);
			step_controller.add_step(granted_setting);
			step_controller.add_step(member_setting);
			step_controller.init();
			
			//选择成员类型
			$("[name*='memberType']").live("change", function(){
				var thisTable = $(this).parent().parent().parent();
				$("[name$='member.id']", thisTable).val('');
				$("[name$='memberName']", thisTable).val('');
				$("[name='memberNameDiv']", thisTable).html('');
				$("[name='memberUnitDiv']", thisTable).html('');
			});
			
			//选择项目相关人员
			$("[name='selectMember']").live("click", function(){
				$slefTr = $(this).parent().parent().prev();
				$table = $(this).parent().parent().parent();
				var memType=$("[name$='memberType']", $slefTr).val();
				if(memType == '-1'){
					alert("请选择成员类型");
					return false;
				}
				var type = {'1' : 8, '2' : 7, '3' : 15};//教师|专家|学生
				popSelect({
					type : type[memType],
					inData : {
						"id" : $("[name$='member.id']", $table).val(), 
						"name" : $("[name$='memberName']", $table).val()
					},
					callBack : function(result){
						$("[name$='member.id']", $table).val(result.data.id);
						$("[name$='memberName']", $table).val(result.data.name);
						$("[name$='memberName']", $table).valid();
						$("[name='memberNameDiv']", $table).html(result.data.name);
						$("[name='memberUnitDiv']", $table).html(result.data.sname);
					}
				});
			});
			
			//一般项目负责人至少有一个	
			$("[name$='isDirector']").live("change",function(){
				if ($(this).val() == 1) {
					$here = $(this).parent().parent().parent();
					var memType = $("[name$='memberType']", $here).val();
					if (memType == '-1') {
						$(this).val("-1");
						alert("请选择成员类型");
					}
				}
			});
			
			//是否显示结项（终止，撤项）时间、审核意见
			$("#status").live("click",function(){
				if($(this).val()>1){
					$(".endStopWithdraw").show();
				}else{
					$(".endStopWithdraw").hide();
				}
			});
			
			//是否显示结项（终止，撤项）时间、审核意见
			$("#status").live("click",function(){
				if($(this).val()>1){
					$(".endStopWithdraw").show();
				}else{
					$(".endStopWithdraw").hide();
				}
			})
			//添加成员
			$(".add_member").live("click", function(){
				editGra.addTable(this, 'table_member', projectType);
			});
			//在最后面添加成员
			$(".add_last_table").live("click", function(){
				$("#last_add_div").hide();
				editGra.addLastTable('membersort', 'table_member', projectType);
			});
			//删除成员
			$(".delete_row").live("click", function(){
				$(this).parent().parent().parent().parent().remove();
				if($("#member .table_valid").length < 1){
					$("#last_add_div").show();
				}
				sortNum();
			});
			//上移
			$(".up_row").live("click", function(){
				var onthis = $(this).parent().parent().parent().parent();
		 		var getup = $(this).parent().parent().parent().parent().prev();
		 		$(getup).before(onthis);
		 		sortNum();
			});
			//下移
			$(".down_row").live("click", function(){
				var onthis = $(this).parent().parent().parent().parent();
		 		var getdown = $(this).parent().parent().parent().parent().next();
		 		$(getdown).after(onthis);
		 		sortNum();
			});
			
			$( "#membersort" ).sortable( {stop: function(event, ui) {
				sortNum();
			 }});
			
			$("#prev").click(function(){
				step_controller.prev();
			});
			$("#next").click(function(){
				step_controller.next();
			});
			$("#cancel").click(function(){
				history.go(-1);
			});
			$("#finish").click(function(){
				step_controller.submit();
			});
			$("#info").show();
			sortNum();
			if($("#member .table_valid").length < 1){
				$("#last_add_div").show();
			}
		});
	};
	
	var submitSelectForm = function(projectType){
		var valid1 = $("#select_form").valid();
		if(valid1){
			valid2 = valid_selectGrantedNumber(projectType);
		}
		if(valid1 && valid2){
			$("#select_form").submit();
		}
	};
	
	var valid_selectGrantedNumber = function(projectType){
		var $form = $("#select_form");
		var $number = $("[name='granted.number']", $form).get(0);
		var $appId = $("[name='entityId']", $form).get(0);
		var number = $number.value;
		var appId = "";
		if($appId != undefined){
			appId = $appId.value;
		}
		var granted = "";
		if(projectType == "general"){
			granted = "GeneralGranted";
		}else if(projectType == "instp"){
			granted = "InstpGranted";
		}else if(projectType == "post"){
			granted = "PostGranted";
		}else if(projectType == "key"){
			granted = "KeyGranted";
		}else if(projectType == "entrust"){
			granted = "EntrustGranted";
		}else if(projectType == "special"){
			granted = "SpecialGranted";
		}
		DWREngine.setAsync(false);
		projectService.isGrantedNumberUnique(granted, number, appId, isSelectGrantedNumberUniqueCallback);
		DWREngine.setAsync(true);
		return isSelectGrantedNumberUnique;
	}

	var isSelectGrantedNumberUniqueCallback = function(data){
		var $form = $("#select_form");
		var $number = $("[name='granted.number']", $form);
		var $numberTd = $number.parent("td");
		if(!data){//未通过校验
			isSelectGrantedNumberUnique = false;
			$numberTd.next("td").append('<span class="error">该批准号已存在</span>');// 写错误信息
		} else {//通过校验
			isSelectGrantedNumberUnique = true;
		}
	};
	
	//自动编号
	var sortNum = function() {
//		var labels = document.getElementById('membersort').childNodes;
//	 	var cnt = 0;
//	 	for(var iLoop = 0; iLoop < labels.length; iLoop++) {
//	   		var label = labels[iLoop];
//	   		if(label.nodeName == 'TABLE') {
//	   			$(":input", label).each(function(key, value){
//				value.name = value.name.replace(/\[.*\]/, "[" + cnt + "]");
//					value.id = value.name;
//				});
//	   			cnt++;
//	   		}
//		}
	 	var spans = getElementsByTagTitle("span","memberSpan");
	 	for(var iLoop = 0; iLoop < spans.length; iLoop++) {
	 		spans[iLoop].innerHTML=iLoop+1;
	 	}
	};
	
	module.exports = {
		 init: function(projectType){init(projectType)}, 
		 submitSelectForm: function(projectType){submitSelectForm(projectType)}, 
		 sortNum: function(){sortNum()}
	};
});