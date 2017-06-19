/**
 * 用于录入结项评审
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
//	var endValid = require('javascript/project/project_share/endinspection/review/validate_result');
	
	var flag = 0;
	var reviewNumber = 0;
	var errorInfo = "";
	
	var init = function(){
		$(function() {
			if(accountType != "MINISTRY"){//非教育部录入
				$("[name$='reviewerType']").each(function(){
					var $table = $(this).parent().parent().parent();
					if($(this).val() == 2){//外部专家
						$("[name='inputUniDiv']", $table).show();
						$("[name='selectUniDiv']", $table).hide();
					}else{
						$("[name='inputUniDiv']", $table).hide();
						$("[name='selectUniDiv']", $table).show();
					}
				});
			};
			
			//选择人员类型
			$("[name$='reviewerType']").live("change", function(){
				var $table = $(this).parent().parent().parent();
				if(accountType == "MINISTRY"){//教育部录入
					$("[name$='.reviewer.id']", $table).val('');
					$("[name='reviewerNameDiv']", $table).html('');
					$("[name='reviewerUnitDiv']", $table).html('');
				}else{
					$("[name$='.university.id']", $table).val(null);
					$("[name$='.agencyName']", $table).val(null);
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
				}
			});
			
			//弹出层选择成员所在高校
			$("[name='selectUniversity']").live("click", function(){
				var $table = $(this).parent().parent().parent().parent();
				var reviewType = $("[name$='reviewerType']", $table).val();
				if(reviewType == '-1'){
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
			
			//选择成员类型
			$("[name$='divisionType']").live("change", function(){
				var $table = $(this).parent().parent().parent();
				var reviewType = $("[name$='reviewerType']", $table).val();
				if(reviewType == '-1'){
					alert("请选择成员类型！");
					$(this).val('-1');
					return false;
				}else if((reviewType == 1 || reviewType == 3) && $(this).val() == 3){
					alert("教师不可能在外部部门！");
					$(this).val('-1');
					return false;
				}else if(reviewType == 2 && $(this).val() != 3){
					alert("外部专家只能在外部部门！");
					$(this).val('-1');
					return false;
				}
		 	});
			
			//选择项目相关人员
			$("[name='selectReviewer']").live("click", function(){
				$slefTr = $(this).parent().parent().prev();
				$table = $(this).parent().parent().parent();
				var reviewType = $("[name$='reviewerType']", $slefTr).val();
				if(reviewType == '-1'){
					alert("请选择人员类型");
					return false;
				}
				
				var type = {'1' : 8, '2' : 7, '3' : 15};//教师|专家|学生
				
				popSelect({
					type : type[reviewType],
					inData : {
						"id" : $("[name$='reviewer.id']", $table).val(), 
						"name" : $("[name$='reviewerName']", $table).val()
					},
					callBack : function(result){
						$("[name$='reviewer.id']", $table).val(result.data.id);
						$("[name$='reviewerName']", $table).val(result.data.name);
//						$("[name$='reviewerName']", $table).valid();
						$("[name='reviewerNameDiv']", $table).html(result.data.name);
						$("[name='reviewerUnitDiv']", $table).html(result.data.sname);
					}
				});
			});
			//增加一条评审记录
			$(".add_review").live("click", function(){
				addTable(this, 'table_review');
			});
			//计算详细分数
			$(".cal_score").live("change", function(){
				calcuateScore($(this).parent().parent().parent().parent());
			});
			//在最后面增加评审记录
			$(".add_last_table").live("click", function(){
				$("#last_add_div").hide();
				addLastTable('review', 'table_review');
			});
			//删除一条评审记录
			$(".delete_row").live("click", function(){
				$(this).parent().parent().parent().parent().remove();
				if($("#review .table_valid").length < 1){
					$("#last_add_div").show();
				}
				sortNum();
				showResult();
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
			
			//成员排序
			$("#review").sortable({stop: function(event, ui) {
				sortNum();
			 }});
			sortNum();
			if($("#review .table_valid").length < 1){
				$("#last_add_div").show();
			};
		});
	};
	
	/**
	 * 提交前检查是否添加了结项评审
	 */
	var checkReview = function (){
		//检查是否添加了项目成员
		var $allTable = $("#review .table_valid");
		if(!$allTable){
			return false;
		}
		if($allTable.length<1){
			alert("请至少添加一个专家评审信息！");
			return false;
		}
		reviewNum();
		if(accountType != "MINISTRY"){
			DWREngine.setAsync(false);
			//检查身份证号和姓名，性别是否匹配
			$($allTable).each(function(index){
				var $idcardType = $("[name*='idcardType']", $(this)).val();
				var $idcardNumber = $("[name*='idcardNumber']", $(this)).val();
				var $name =  $("[name*='reviewerName']", $(this)).val();
				var $gender =  $("[name*='gender']", $(this)).val();
				reviewNumber = index + 1;
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
			errorInfo += "评审专家" + reviewNumber + "的证件信息与姓名和性别不符合，请核实后重新填写！\n"
		}
	};
	
	//自动编号	
	var sortNum = function() {
	 	var spans = getElementsByTagTitle("span","reviewSpan");
	 	for(var iLoop = 0; iLoop < spans.length; iLoop++) {
	 		spans[iLoop].innerHTML=iLoop+1;
	 	}
	};
	
	/**
	 * 提交前按table重新分配下标
	 */
	var reviewNum = function(){
		$(".table_valid").each(function(key, value){
			$("input, select, textarea", $(value)).each(function(key1, value1){
				value1.name = value1.name.replace(/\[.*\]/, "[" + (key) + "]");
			});
		});
//	 	endValid.valid_review();//分配下标后重新设置校验，否则修改完成时会出错
	}

	/**
	 * 添加一个相关人员
	 */
	var addTable = function(obj ,tableId){
		var onthis = $(obj).parent().parent().parent().parent();
		var addTable = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>").insertAfter(onthis);
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag++;
//		endValid.valid_review();
		sortNum();
	};

	var addLastTable = function(topId, tableId) {
		$("#" + topId).append("<table id='newTable' width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
		var addTable = $("#newTable");
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag++;
//		endValid.valid_review();
		sortNum();
		addTable.removeAttr("id");
	};

	//添加结项评审结果
	//data：2保存；3提交
	var submitOrSaveAddEndReviewResult = function (data, projectType){
		submitOrSaveEndReviewResult(data, 1, projectType);
	};

	var submitOrSaveModifyEndReviewResult = function (data, projectType){
		submitOrSaveEndReviewResult(data, 2, projectType );
	};

	//结项评审结果是否提交
	//data：2保存；3提交
	//type：1添加；2修改
	var submitOrSaveEndReviewResult = function(data, type, projectType){
		if(!$("#review_endform").valid()){
			return;
		}
		$("#submitStatus").val(data);
		var url="";
		if(type==1){
			url="project/" + projectType + "/endinspection/review/addResult.action";
		}else if(type==2){
			url="project/" + projectType + "/endinspection/review/modifyResult.action";
		}
		if(checkReview()){
			$("#review_endform").attr("action",url);
			$("#review_endform").submit();
		}
	}
	//添加结项评审专家
	var addReviewExpert = function (endId, projectType){
		addExpert(endId, projectType );
	};
	var addExpert = function(endId, projectType){
		var url="project/" + projectType + "/endinspection/review/addResultExpert.action";
		$("#review_endform").attr("action",url);
		$("#review_endform").submit();
	}

	// 获得当前评审专家的打分，如果三项都有，则计算总分。
	var calcuateScore = function($table){
		var calcuateFlag = true;
		var score = 0;
		for(var i = 0; i < 3; i++){
			if($(".cal_score", $table).eq(i).val() == "" ){
				calcuateFlag = false;
			} else {
				score += parseFloat($(".cal_score", $table).eq(i).val());
			}
		}
		if(calcuateFlag){
			$("[name*='reviewScore']", $table).eq(0).attr('value', score);
			var $grade = $("[name*='reviewGrade']", $table).get(0);
			if (score > 90) {
				$grade.value = "优秀";
			} else if (score >= 65 && score <= 90) {
				$grade.value = "合格";
			} else if (score < 65) {
				$grade.value = "不合格";
			}
			showResult();
		}
	};

	// 判断是否每个评审专家都有总分，如果有，计算小组总分、平均分及成果鉴定等级。
	var showResult = function (){
		var index = $(".table_valid").length;
		var showFlag = true;
		var totalScore = 0;
		for(var i = 0; i < index; i++){
			var reviewScore = $("[name*='reviewScore']", $(".table_valid").eq(i)).val();
			if(reviewScore == ""){
				showFlag = false;
			} else {
				totalScore += parseFloat(reviewScore);
			}
		}
		if(showFlag && index > 0){
			var $groupGrade = $("[name*='groupGrade']").get(0);
			$("[name*='totalScore']").eq(0).attr('value', totalScore);
			$("[name*='avgScore']").eq(0).attr('value', totalScore / index);
			if (totalScore / index > 90) {
				$groupGrade.value = "优秀";
			} else if (totalScore / index >= 65 && totalScore / index <= 90) {
				$groupGrade.value = "合格";
			} else if (totalScore / index < 65) {
				$groupGrade.value = "不合格";
			}
		}else{
			$("[name*='totalScore']").eq(0).attr('value', "");
			$("[name*='avgScore']").eq(0).attr('value', "");
			$("[name*='groupGrade']").eq(0).attr('value', "");
		}
	};
	
	/*-------------------初始化方法结束------------------------*/
	
	
	module.exports = {
		 submitOrSaveAddEndReviewResult: function(data, projectType){submitOrSaveAddEndReviewResult(data, projectType)},
		 submitOrSaveModifyEndReviewResult: function(data, projectType){submitOrSaveModifyEndReviewResult(data, projectType)},
		 addReviewExpert: function(endId, projectType){addReviewExpert(endId, projectType)},
		 init: function(){init()} 
	};
});









