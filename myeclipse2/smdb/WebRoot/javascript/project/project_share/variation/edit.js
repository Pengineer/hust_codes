/**
 * 用于添加、修改变更申请及变更结果
 * @author 余潜玉、肖雅
 */
define(function(require, exports, module) {
	require('dwr/util');
	require('javascript/engine');
	require('dwr/interface/projectService');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	require('jquery-ui');
	var datepick = require("datepick-init");
	var timeValidate = require('javascript/project/project_share/validate');
	var varValid = require('javascript/project/project_share/variation/validate');
	
	var flag = 99999;
	var memberNumber = 0;
	var errorInfo = "";
	/**
	 * 变更事项与页面元素对应关系
	 */
	var var_item_tr = {
		"01":"cD",//变更项目成员
		"02":"cA",//变更机构
		"03":"cPF",//变更成果形式
		"04":"cN",//变更项目名称
		"05":"cC",//研究内容有重大调整
		"06":"pO",//延期
		"07":"sBO",//自行中止项目
		"08":"aW",//申请撤项
		"09":"fEE",//变更经费预算
		"20":"o"//变更其他
	};
	
	//日期选择器初始化
	var datepickInit = function(){
		datepick.init();
	};
	
	/**
	 * 显示或隐藏指定变更事项对应的表单
	 * @param {Object} index变更事项的索引名称
	 * @param {Object} flag复选框状态
	 */
	var displayVarItem = function (index, flag) {
		if (var_item_tr[index] != undefined && var_item_tr[index] != null) { 
			if (flag) {// 若为选中状态显示对应的表单
				$("#" + var_item_tr[index]).show();
			} else {// 若为取消状态隐藏对应的表单
				$("#" + var_item_tr[index]).hide();
			}
			if(index == "01"){//变更负责人
				//编辑标志位 1：走流程	2：录入
				if($("#editFlag").val() == 1){
					varValid.valid_changeMember1();
				}else if($("#editFlag").val() == 2){
					varValid.valid_changeMember2();
				}
				$("[name$='memberType']").each(function(){
					var $table = $(this).parent().parent().parent();
					var $selectType = $(this).parent().parent().prev();
					var type = $("[type='radio']:checked", $selectType).val();
					$nextTr = $selectType.next().next();
					$nextNextTr = $selectType.next().next().next();
					$newnextTr = $selectType.next().next().next().next();
					$newnextNextTr =$selectType.next().next().next().next().next();
					$newnextNextneTr = $selectType.next().next().next().next().next().next();
					$newnextNextnextTr = $selectType.next().next().next().next().next().next().next();
					$newnextNextgenderTr = $selectType.next().next().next().next().next().next().next().next();
					$newnextNextgenderTr1 = $newnextNextgenderTr.next();
					$newnextNextgenderTr2 = $newnextNextgenderTr1.next();
					$newnextNextgenderTr3 = $newnextNextgenderTr2.next();
					if(type == 1){//新建
						$($nextTr).hide();
						$($nextNextTr).hide();
						$($newnextTr).show();
						$($newnextNextTr).show();
						$($newnextNextneTr).show();
						$($newnextNextgenderTr3).show();
						$($newnextNextgenderTr1).show();
						$($newnextNextgenderTr2).show();
					}else{
						$($newnextTr).hide();
						$($newnextNextTr).hide();
						$($newnextNextneTr).hide();
						$($newnextNextgenderTr3).hide();
						$($newnextNextgenderTr1).hide();
						$($newnextNextgenderTr2).hide();
						$($nextTr).show();
						$($nextNextTr).show();
					}
					if($("#editFlag").val() == 1){
						if($(this).val() == 2){//外部专家
							$("[name='inputUniDiv']", $table).show();
							$("[name='selectUniDiv']", $table).hide();
						}else{
							$("[name='inputUniDiv']", $table).hide();
							$("[name='selectUniDiv']", $table).show();
						}
					}
				});
			}else if(index == "02"){//变更机构
				varValid.valid_changeAgency();
			}else if(index == "03"){//变更成果形式
				varValid.valid_productType();
			}else if(index == "04"){//变更项目名称
				varValid.valid_changeName();
			}else if(index == "06"){//延期
				varValid.valid_postponement($("#variation5").val());
			}
		}
	};
	
	/**
	 * 根据页面参数设置默认同意的变更事项
	 */
	var setDefaultVarResultItem = function (){
		var defaultApproveVarItem = $("#defaultSelectApproveVarCode").val();
		var defaultApproveVarItems = [];
		if (defaultApproveVarItem != undefined && defaultApproveVarItem != null && defaultApproveVarItem != "") {
			defaultApproveVarItems = defaultApproveVarItem.split(",");
			var $tableResult = $("#var_result_item_list")
			for (var i = 0; i < defaultApproveVarItems.length; i++) {
				$("#" + defaultApproveVarItems[i], $tableResult).attr("checked", "checked");
				
			}
		}
	};
	
	/**
	 * 将第N次变更的N换成汉字
	 */
	var setNumToChinese = function setNumToChinese() {
		$("#num").html(Num2Chinese(parseInt($("#num").html())));
	};
	
	/**
	 * 根据页面参数设置默认选中的变更事项
	 */
	var setDefaultVarItem = function () {
		var defaultVarItem = $("#defaultSelectCode").val();
		var defaultVarItems = [];
		if (defaultVarItem != undefined && defaultVarItem != null && defaultVarItem != "") {
			defaultVarItems = defaultVarItem.split(",");
			var $table = ("#var_item_list");
			for (var i = 0; i < defaultVarItems.length; i++) {
				$("#" + defaultVarItems[i], $table).attr("checked", "checked");
				displayVarItem(defaultVarItems[i], true);
			}
		}
	};
	
	/**
	 * 设置默认选中的成果形式
	 */
	var setDefaultProductType = function () {
		var defaultSelectProductType = $("#defaultSelectProductTypeCode").val();
		var defaultSelectProductTypes = [];
		if (defaultSelectProductType != undefined && defaultSelectProductType != null && defaultSelectProductType != "") {
			defaultSelectProductTypes = defaultSelectProductType.split(",");
			for (var i = 0; i < defaultSelectProductTypes.length; i++) {
				$("#" + defaultSelectProductTypes[i], $("#cPF")).attr("checked", "checked");
				if(defaultSelectProductTypes[i] == '06'){
					$("#productTypeOtherSpan").show();
				}
			}
		}
	};
	
	var checkMember = function (projectType){
		//检查是否添加了项目成员
		var $allTable = $("#member .table_valid");
		if(!$allTable){
			return false;
		}
		if(projectType == "key"){
			//检查是否添加首席专家且只有一个首席专家
			var hasDirector = 0;
			$("[name$='isDirector']").each(function(){
				if($(this).val() == 1){
					hasDirector++;
				}
			});
			if(hasDirector == 0){
				alert("请添加一个项目首席专家！");
				return false;
			}else if(hasDirector > 1){
				alert("项目首席专家有且只有一个！");
				return false;
			}
		}else{
			if($allTable.length<1){
				alert("项目负责人至少有一个！");
				return false;
			}
		}
		if($allTable.length<1){
			alert("项目负责人至少有一个！");
			return false;
		}
		memberNum();
		if($("#editFlag").val() == 1){
			DWREngine.setAsync(false);
			//检查身份证号和姓名，性别是否匹配
			$($allTable).each(function(index){
				var $idcardType = $("[name*='idcardType']", $(this)).val();
				var $idcardNumber = $("[name*='idcardNumber']", $(this)).val();
				var $name =  $("[name*='memberName']", $(this)).val();
				var $gender =  $("[name*='gender']", $(this)).val();
				memberNumber = index + 1;
				projectService.isPersonMatch($idcardType, $idcardNumber, $name, $gender, isPersonMatchCallback);
			});
			DWREngine.setAsync(true);
			if(errorInfo.length > 0){
				alert(errorInfo);
				errorInfo = "";
				return false;
			}
		}
		return true;
	};
	
	var isPersonMatchCallback = function(data){
		if(data == 0){//填写的信息不匹配
			errorInfo += "第" + memberNumber + "负责人的证件信息与姓名和性别不符合，请核实后重新填写！\n"
		}
	};
	
	/**
	 * 校验变更成果形式前后是否一样
	 */
	var validProType = function(){
		if($("input[name='selectIssue'][type='checkbox'][value='03']").attr("checked")){//变更成果形式
			var $spt = $("input[name='selectProductType'][type='checkbox']");
			var dpt = "";
			for(var i = 0; i < $spt.length; i++){
				if($spt.eq(i).attr("checked")){
					dpt += $spt.eq(i).attr("value") + ",";
				}
			}
			if(dpt.length > 0){
				dpt = dpt.substring(0, dpt.length - 1);
			}
			var dptOther = $("[name='productTypeOther']:eq(0)").val();
			var $spttd = $(".table_td13", "#cPF").eq(0);
			if(dpt == $("#oldProductTypeCode").val() && dptOther == $("#oldProductTypeOther").val()){
				$spttd.html('<span class="error">变更前后不能一样</span>');// 写错误信息
				return false;
			}else{
				$spttd.html('');
				return true;
			}
		}else{
			return true;
		}
	};
	
	/**
	 * 判断其他成果类别输入框是否要显示
	 * @param {Object} isChecked	是否选择其他成果
	 * @param {Object} value	点中的多选框的值
	 */
	var showOtherProductTypeOrNot = function(isChecked, value){
		if(value == undefined || typeof(value) == 'undefined'){
			if(isChecked){
				$("input[name='productTypeOther']").rules("add", {required:true, maxlength:100, keyWords:true});
				$("#productTypeOtherSpan").show();
			}else{
				$("input[name='productTypeOther']").rules("remove");
				$("#productTypeOtherSpan").hide();
			}
		}else{
			if(isChecked && value == '06'){
				$("input[name='productTypeOther']").rules("add", {required:true, maxlength:100, keyWords:true});
				$("#productTypeOtherSpan").show();
			}else{
				$("input[name='productTypeOther']").rules("remove");
				$("#productTypeOtherSpan").hide();
			}
		}
	};
	
	//是否全选变更事项
	var showVarItemOrNot = function (isChecked){
		var elems = $("input[type='checkbox'][name='selectIssue']");
		for(var i = 0; i < elems.length; i++){
			displayVarItem(elems[i].id, isChecked);
		}
		var elemst = $("input[type='checkbox'][name='selectIssue']:checked");
		show(elemst, false);
	};
	
	//是否显示变更结果行
	var isShow = function(elems) {
		if($("input[type='radio'][name='varResult']:checked").val() == 2) {
			if(elems.length > 0) {
				$("#var_result_item_tr").show();
			} else {
				$("#var_result_item_tr").hide();
			}
		}
	};
	
	//显示变更结果行
	var show = function(elems, isInital) {
		$("#var_result_item_list").empty();
		elems.each(function(index){
			if(isInital) {displayVarItem(this.id, this.checked);}
			if(index % 4 == 0) {
				$("#var_result_item_list").append($("<tr></tr>"));
			}
			for(var i in obj[this.id]) {
				$("tr:last", $("#var_result_item_list")).append(obj[this.id][i]);
			}
		})
	};
	
	/**
	 * 提交前按table重新分配下标
	 */
	var memberNum = function (){
		$(".table_valid").each(function(key, value){
			$("input, select", $(value)).each(function(key1, value1){
				value1.name = value1.name.replace(/\[.*\]/, "[" + (key) + "]");
			});
		});
	};
	
	/**
	 * 添加一个相关人员
	 */
	var addTable = function (obj ,tableId){
		var onthis = $(obj).parent().parent().parent().parent();
		var addTable = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>").insertAfter(onthis);
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag++;
		sortNum();
		if($("#editFlag").val() == 1){
			varValid.valid_changeMember1();
		}else if($("#editFlag").val() == 2){
			varValid.valid_changeMember2();
		}
	};
	
	//自动编号
	var sortNum = function () {
//		var labels = document.getElementById('member').childNodes;
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
	
	var addLastTable = function (topId, tableId) {
		$("#" + topId).append("<table id='newTable' width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
		var addTable = $("#newTable");
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag++;
		sortNum();
		addTable.removeAttr("id");
		if($("#editFlag").val() == 1){
			varValid.valid_changeMember1();
		}else if($("#editFlag").val() == 2){
			varValid.valid_changeMember2();
		}
	};
	
	/*---------------------页面onclick方法--------------------------*/
	
	var submitOrNotApply = function(data, projectType){
		if($("#varStatus").val()== 0){
			alert("该业务已停止，无法进行此操作！");
			return false;
		}
		var flag = timeValidate.timeValidate($("#deadline").val());
		if(flag){
			
			var projectid = $("#projectid").val();
			var bookFee = $("#bookFee").val();
		    var dataFee= $("#dataFee").val();
		    var travelFee= $("#travelFee").val();
		    var conferenceFee= $("#conferenceFee").val();
		    var internationalFee= $("#internationalFee").val();
		    var deviceFee=$("#deviceFee").val();
		    var consultationFee= $("#consultationFee").val();
		    var laborFee= $("#laborFee").val();
		    var printFee= $("#printFee").val();
		    var indirectFee= $("#indirectFee").val();
		    var otherFeeD= $("#otherFeeD").val();
		    var totalFee= $("#totalFeeD").val();
		    var feeNote = $("#feeNote").val();
			
			document.getElementsByName('submitStatus')[0].value = data;
			var varId=document.getElementById('varId').value;
			if(data == 3){
				if( !confirm('提交后无法修改，是否确认提交？'))
					return ;
			}
			var val1 = $("#form_apply").valid();
			var val2 = validProType();
			var val3 = varValid.valid_postponement($("#variation5").val());
			var val4 = ($("input[name='selectIssue'][type='checkbox'][value='01']").attr("checked")) ? checkMember(projectType) : true; //变更负责人
			if(val1 && val2 && val3 && val4){
				$("#form_apply").submit();
			}
		}else{
			return false;
		}
	};
	
	var addVarResult = function(data, type, projectType) {
		$("#varImportedStatus").attr("value",data);
		var url = "";
		if (data == 3 && projectType !="post" && projectType != "entrust") {
			var midPending = $("#midPending").val();
			if(($("#sBO").val() != null || $("#aW").val() != null) && midPending == 1){
				alert("项目中检待处理，无法提交，请先处理中检！");
				return;
			}else if (!confirm("提交后不能修改，是否确认提交?")) {
				return;
			}
		}
		
		var projectid = $("#projectid").val();
			var bookFee = $("#bookFee").val();
		    var dataFee= $("#dataFee").val();
		    var travelFee= $("#travelFee").val();
		    var conferenceFee= $("#conferenceFee").val();
		    var internationalFee= $("#internationalFee").val();
		    var deviceFee=$("#deviceFee").val();
		    var consultationFee= $("#consultationFee").val();
		    var laborFee= $("#laborFee").val();
		    var printFee= $("#printFee").val();
		    var indirectFee= $("#indirectFee").val();
		    var otherFeeD= $("#otherFeeD").val();
		    var totalFee= $("#totalFeeD").val();
		    var feeNote = $("#feeNote").val();
		
		if(type == 1){
			url = "project/" + projectType + "/variation/apply/addResult.action";
		}else if(type == 2){
			url = "project/" + projectType + "/variation/apply/modifyResult.action";
		}
		var val1 = $("#form_apply").valid();
		var val2 = validProType();
		var val3 = varValid.valid_postponement($("#variation5").val());
		var val4 = ($("input[name='selectIssue'][type='checkbox'][value='01']").attr("checked")) ? checkMember(projectType) : true; //变更负责人
		if(val2 && val3 && val4){
			$("#form_apply").attr("action", url);
	     	$("#form_apply").ajaxSubmit({
	     		success: function(result){
	     			if (result.personList != null && result.personList != "") {
	     				var memberFlag = $("#memberFlag").val();
	     				if (memberFlag == 1) {
							alert("请选择库中已有成员或者仍然新建成员！");
							return false;
						} else {
							$("[name$='memberType']").each(function(){
								var $table = $(this).parent().parent().parent();
								var $selectType = $(this).parent().parent().prev();
								var type = $("[type='radio']:checked", $selectType).val();
								if(type == 1){
									var tmpdata = [];
									var ptr = $("<tr bgcolor='Yellow'></tr>");
									var ptd = $("<td class='table_td8'>库中已有人员：</td>");
									var ptd1 = $("<td class='table_td9'>姓名</td>");
									$("<span>　　　　　　　　　所在单位</span>").appendTo(ptd1);
									var ptd2 = $("<td class='table_td9'>所在部门</td>");
									var ptd3 = $("<td class='table_td9'>　　职务</td>");
									ptr.append(ptd);
									ptr.append(ptd1);
									ptr.append(ptd2);
									ptr.append(ptd3);
									$table.append(ptr);
									for (var i = 0 ; i < result.personList.length; i++) {
										tmpdata = result.personList[i];
										if (tmpdata[2] == null) {
											var tmpdata2 = "无";
										} else {
											var tmpdata2 = tmpdata[2];
										}
										if (tmpdata[3] == null) {
											var tmpdata3 = "无";
										} else {
											var tmpdata3 = tmpdata[3];
										}
										if (tmpdata[4] == null) {
											var tmpdata4 = "无";
										} else {
											var tmpdata4 = tmpdata[4];
										}
										var ptr = $("<tr bgcolor='Yellow'></tr>");
										var ptd = $("<td class='table_td8'></td>");
										var ptd1 = $("<td class='table_td9'></td>");
										$("<input class='j_person' type='radio' name='personId' value= ' "+ tmpdata[0] +" ' />").appendTo(ptd1);
										$("<span>"+ tmpdata[1] +"</span>").appendTo(ptd1);
										$("<span>　　　　　　　"+ tmpdata2 +"</span>").appendTo(ptd1);
										var ptd3 = $("<td class='table_td9'></td>");
										$("<span>"+ tmpdata3 +"</span>").appendTo(ptd3);
										var ptd4 = $("<td class='table_td9'></td>");
										$("<span>　　"+ tmpdata4 +"</span>").appendTo(ptd4);
										ptr.append(ptd);
										ptr.append(ptd1);
										ptr.append(ptd3);
										ptr.append(ptd4);
										$table.append(ptr);
									}
									var ptr = $("<tr bgcolor='Yellow'></tr>");
									var ptd = $("<td class='table_td8'></td>");
									var ptd1 = $("<td class='table_td9'></td>");
									$("<input class='j_person' type='radio' name='personId' value= '-1' />").appendTo(ptd1);
									$("<span>仍然新建此人</span>").appendTo(ptd1);
									var ptd2 = $("<td class='table_td9'></td>");
									var ptd3 = $("<td class='table_td9'></td>");
									ptr.append(ptd);
									ptr.append(ptd1);
									ptr.append(ptd2);
									ptr.append(ptd3);
									$table.append(ptr);
									$("#memberFlag").val(1);
								}
							});
						}
	     			} else if (result.varResultFlag != null && result.varResultFlag == 1) {
	     				var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.callBack(thisPopLayer);
					}
	     		}
	     	});
		}
	};
	
	/**
	 * 显示结果供选择
	 * @param {Object} data
	 */
	var showResultDetail = function (data){
		if(data == 2){//同意
			$("#var_result_item_tr").show();
			$("input[name='varSelectIssue']").rules("add",{required: true});
		}else{//不同意
			$("#var_result_item_tr").hide();
			$("input[name='varSelectIssue']").rules("remove");
		}
	};
	$(".j_person").live('click',function(){
		$slefTr = $(this).parent().parent().parent();
		var personId = $("input[name='personId'][type='radio']:checked", $slefTr).val();
		$("#personId").val(personId);
	});
	$(".j_showMembertDetail").live('click',function(){
		$slefTr = $(this).parent().parent();
		$nextTr = $(this).parent().parent().next().next();
		$nextNextTr = $(this).parent().parent().next().next().next();
		$newnextTr = $(this).parent().parent().next().next().next().next();
		$newnextNextTr = $(this).parent().parent().next().next().next().next().next();
		$newnextNextneTr = $(this).parent().parent().next().next().next().next().next().next();
		$newnextNextnextTr = $(this).parent().parent().next().next().next().next().next().next().next();
		$newnextNextgenderTr = $(this).parent().parent().next().next().next().next().next().next().next().next();
		$newnextNextgenderTr1 = $newnextNextgenderTr.next();
		$newnextNextgenderTr2 = $newnextNextgenderTr1.next();
		$newnextNextgenderTr3 = $newnextNextgenderTr2.next();
		var data = $("[type='radio']:checked", $slefTr).val();
		if(data == 1){//新建
			$($nextTr).hide();
			$($nextNextTr).hide();
			$($newnextTr).show();
			$($newnextNextTr).show();
			$($newnextNextneTr).show();
			$($newnextNextgenderTr3).show();
			$($newnextNextgenderTr1).show();
			$($newnextNextgenderTr2).show();
			$(":input[name*='member.id']", $nextTr).rules("remove");
			$(":input[name*='memberName']", $newnextTr).rules("add", {required:true});
		}else{//不是新建
			$($newnextTr).hide();
			$($newnextNextTr).hide();
			$($newnextNextgenderTr3).hide();
			$($newnextNextgenderTr1).hide();
			$($newnextNextgenderTr2).hide();
			$($newnextNextneTr).hide();
			$($nextTr).show();
			$($nextNextTr).show();
			$(":input[name*='member.id']", $nextTr).rules("add", {required:true});
			$(":input[name*='memberName']", $newnextTr).rules("remove");
		}
	});
	
	// 绑定弹出层选择高校
	$(".select_uni_btn_1").live("click",function() {
		$university = $(this).next();
		$universityId = $(this).next().next();
		popSelect({
			type : 3,
			label : 3,
			inData : {"id":$($universityId).val(), "name":$($university).html()},
			callBack : function(result){
				$($universityId).val(result.data.id);
				$($university).html(result.data.name);
			}
		});
	});
	
	// 绑定弹出层选择院系
	$(".select_dep_btn_1").live("click",function() {
		$memberName = $(this).parent().parent().prev().prev();
		var memberName = $(":input[name*='memberName']", $memberName).val();
		$universityId = $(this).parent().parent().prev();
		var universityId = $(":input[name*='university.id']", $universityId).val();
		$type = $(this).parent().parent().prev().prev().prev().prev().prev();
		var type = $("[name$='memberType']", $type).val();
		$unit = $(this).next().next();
		$departmentId = $(this).next().next().next();
		$divisionType = $(this).next().next().next().next().next();
		popSelect({
			type : 4,
			label : 3,
			inData : {"id":$($departmentId).val(), "name":$($unit).html(), "memberName":memberName, "universityId":universityId, "type":type},
			callBack : function(result){
				$($departmentId).val(result.data.id);
				$($unit).html(result.data.name);
				$($divisionType).val(2);
			}
		});
	});
	// 绑定弹出层选择基地
	$(".select_ins_btn_1").live("click",function() {
		$unit = $(this).next();
		$instituteId = $(this).next().next();
		$divisionType = $(this).next().next().next().next();
		popSelect({
			type : 5,
			label : 3,
			inData : {"id":$($instituteId).val(), "name":$($unit).html(), "memberName":memberName, "universityId":universityId, "type":type},
			callBack : function(result){
				$($instituteId).val(result.data.id);
				$($unit).html(result.data.name);
				$($divisionType).val(1);
			}
		});
	});
	/*---------------------页面onclick方法结束--------------------------*/
	
	/*-------------------以下为触发事件方法------------------------*/
	
	/**
	 * 处理变更事项、变更事项结果映射关系
	 */
	var varInitClick = function(){
		$(function() {
			//是否全选变更事项
			$("#checkAllVarItem").live("click",function() { 
				checkAll(this.checked, 'selectIssue');
				showVarItemOrNot(this.checked);
				checkAllOrNot(this.checked, 'varSelectIssue', 'checkAllVarResultItem');
			});
			
			//判断变更事项是否被选中
			$("input[name='selectIssue'][type='checkbox']").live("click",function() { 
				var elems = $("input[type='checkbox'][name='selectIssue']:checked");
				displayVarItem(this.id, this.checked);
				show(elems, false);
				checkAllOrNot(this.checked, 'selectIssue', 'checkAllVarItem'); 
				if(this.checked){
					var $tableResult = $("#var_result_item_list");
					$("#" + this.id, $tableResult).attr("checked", false);
				}
				checkAllOrNot(true, 'varSelectIssue', 'checkAllVarResultItem');
			});
			
			//是否全选成果类别
			$("#checkAllProductTypeItem").live("click",function() { 
				checkAll(this.checked, 'selectProductType');
				showOtherProductTypeOrNot(this.checked);
			});
			
			//判断是否全选成果类别
			$("input[name='selectProductType'][type='checkbox']").live("click",function() { 
				checkAllOrNot(this.checked, 'selectProductType', 'checkAllProductTypeItem'); 
				if(this.value == '06'){//如果是点击其他成果类别
					showOtherProductTypeOrNot(this.checked, this.value);
				}
			});
			
			//是否全选同意变更事项
			$("#checkAllVarResultItem").live("click",function() { 
				checkAll(this.checked, 'varSelectIssue');
			});
			
			//判断是否全选同意变更事项
			$("input[name='varSelectIssue'][type='checkbox']").live("click",function() { 
				checkAllOrNot(this.checked, 'varSelectIssue', 'checkAllVarResultItem');
			});
			// 绑定弹出层选择院系
			$("#select_dep_btn").live("click",function() {
				$memberName = $(this).parent().parent().prev();
				var memberName = $(":input[name*='memberName']", $memberName).val();
				$universityId = $(this).parent().parent().prev();
				var universityId = $(":input[name*='university.id']", $universityId).val();
				$type = $(this).parent().parent().prev();
				var type = $("[name$='memberType']", $type).val();
				if(type == undefined || typeof(type) == 'undefined' ) {
					type =0;
				} else {
					type= Number(type);
				}
				popSelect({
					type : 4,
					label : 3,
					inData : {"id":$("#deptInstId").val(), "name":$("#varUnit").html(), "type":type,"memberName":memberName,"universityId":universityId},
					callBack : function(result){
						$("#deptInstId").val(result.data.id);
						$("#varUnit").html(result.data.name);
						$("#deptInstFlag").val(2);
					}
				});
			});
			// 绑定弹出层选择基地
			$("#select_ins_btn").live("click",function() {
				$memberName = $(this).parent().parent().prev();
				var memberName = $(":input[name*='memberName']", $memberName).val();
				$universityId = $(this).parent().parent().prev();
				var universityId = $(":input[name*='university.id']", $universityId).val();
				$type = $(this).parent().parent().prev();
				var type = $("[name$='memberType']", $type).val();
				if(type == undefined || typeof(type) == 'undefined' ) {
					type =0;
				} else {
					type= Number(type);
				}
				popSelect({
					type : 5,
					label : 3,
					inData : {"id":$("#deptInstId").val(), "name":$("#varUnit").html(), "type":type,"memberName":memberName,"universityId":universityId},
					callBack : function(result){
						$("#deptInstId").val(result.data.id);
						$("#varUnit").html(result.data.name);
						$("#deptInstFlag").val(1);
					}
				});
			});
			//下载变更模板
			$("#download_model").live("click", function() {
				window.location.href = basePath + 'project/general/variation/apply/downloadTemplate.action';
				return false;
			});
			//选择成员类型
			$("[name$='memberType']").live("change", function(){
				var $select = $(this).parent().parent().next().next().next().next();
				var $select1 = $select.next();
				var $select2 = $select1.next();
				var $select3 = $select2.next();
				var $table = $(this).parent().parent().parent();
				var data = $("[type='radio']:checked", $(this).parent().parent().prev()).val();
				if (data == 1) {
					if($(this).val() == 2){//外部专家
						$($select).hide();
						$($select1).hide();
						$($select2).show();
						$($select3).show();
					} else {
						$($select).show();
						$($select1).show();
						$($select2).hide();
						$($select3).hide();
					}
					
				}
				if($("#editFlag").val() == 1){
					$("[name$='university.id']", $table).val(null);
					$("[name$='agencyName']", $table).val(null);
					$("[name='universityNameDiv']", $table).html(null);
					if($(this).val() == 2){//外部专家
						$("[name='inputUniDiv']", $table).show();
						$("[name='selectUniDiv']", $table).hide();
						$("[name$='divisionType']", $table).val(3);
					}else{
						$("[name='inputUniDiv']", $table).hide();
						$("[name='selectUniDiv']", $table).show();
						$("[name$='divisionType']", $table).val(-1);
					}
				}else if($("#editFlag").val() == 2){
					$("[name$='.member.id']", $table).val('');
					$("[name='memberNameDiv']", $table).html('');
					$("[name='memberUnitDiv']", $table).html('');
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
			
			//选择部门类型
			$("[name$='divisionType']").live("change", function(){
				var $table = $(this).parent().parent().parent();
				var memType = $("[name$='memberType']", $table).val();
				if(memType == '-1'){
					alert("请选择成员类型！");
					$(this).val('-1');
					return false;
				}else if((memType == 1 || memType == 3) && $(this).val() == 3){
					alert("教师和学生不可能在外部部门！");
					$(this).val('-1');
					return false;
				}else if(memType == 2 && $(this).val() != 3){
					alert("外部专家只能在外部部门！");
					$(this).val('-1');
					return false;
				}
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
					label : 3,
					inData : {
						"id" : $("[name*='member.id']", $table).val(), 
						"name" : $("[name='memberNameDiv']", $table).html()
					},
					callBack : function(result){
						$("[name*='member.id']", $table).val(result.data.id);
						$("[name*='member.id']", $table).valid();
						$("[name='memberNameDiv']", $table).html(result.data.name);
						$("[name='memberUnitDiv']", $table).html(result.data.sname);
					}
				});
			});
			//添加成员
			$(".add_member").live("click", function(){
				addTable(this, 'table_member');
			});
			//在最后面添加成员
			$(".add_last_table").live("click", function(){
				$("#last_add_div").hide();
				addLastTable('member', 'table_member');
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
		});
	};
	/*-------------------触发事件方法结束------------------------*/
	
	/*-------------------以下为初始化方法------------------------*/
	var init = function(){
		$(function() {
			datepickInit();
			setNumToChinese();// 将N换成汉字
			setDefaultVarItem();// 设置默认选中的变更事项
			setDefaultProductType();// 设置默认选中的成果形式
			var $product = $("input[name='selectIssue'][type='checkbox'][value='03']");
			if($product.attr("checked") == true){
				var $other = $("input[name='selectProductType'][type='checkbox'][value='06']");
				if($other.attr("checked") == true){
					$("input[name='productTypeOther']").rules("add", {required:true, maxlength:100, keyWords:true});
					$("#productTypeOtherSpan").show();
				}
			}
			checkAllOrNot(true, 'selectIssue', 'checkAllVarItem'); //判断变更事项是否全选
			checkAllOrNot(true, 'selectProductType', 'checkAllProductTypeItem'); //判断成果类别是否全选
			//存储元素对象
			obj = {};
			for(var i in var_item_tr) {
				var elem = $("#" + i, $("#var_result_item_list"));
				obj[i] = [elem.parent(), elem.parent().next()];
			}
			//初始化
			(function(){
				var elems = $("input[type='checkbox'][name='selectIssue']:checked");
				isShow(elems);
				show(elems, true);
			})();
			
			if($("#editFlag").val() == 2){
				var $varResult = $("input[name='varResult'][type='radio']:checked");
				if($varResult != undefined && $varResult != null &&$varResult.length > 0 ){
					showResultDetail($varResult.val());
				}
				setDefaultVarResultItem();// 设置默认同意的变更事项
				checkAllOrNot(true, 'varSelectIssue', 'checkAllVarResultItem');
			}
			//成员排序
			$("#member").sortable( {stop: function(event, ui) {
				sortNum();
			}});
			sortNum();
			if($("#member .table_valid").length < 1){
				$("#last_add_div").show();
			}
		});
	};
	
	module.exports = {
		addVarResult: function(data, type, projectType){addVarResult(data, type, projectType)}, 
		submitOrNotApply: function(data, projectType){submitOrNotApply(data, projectType)},
		showResultDetail: function(data){showResultDetail(data)}, 
		init: function(){init()},
		varInitClick: function(){varInitClick()}
	};
});
