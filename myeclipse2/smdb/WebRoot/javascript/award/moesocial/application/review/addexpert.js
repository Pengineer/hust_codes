/**
 * 用于录入奖励评审
 * @author 王燕
 */

define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/awardService');
//	require('validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/step_tools');
	require('form');
	require('jquery-ui');
	require('pop-init');
	var datepick = require("datepick-init");
//	var validate = require('javascript/award/moesocial/application/review/validate_result');
	
	var flag = 0;
	var reviewNumber = 0;
	var errorInfo = "";
	var accountType = $("#accountType").val();
	var initReview = function(){
		$(function() {
			$("[name$='reviewerType']").live("change", function(){//选择人员类型
				var $table = $(this).parent().parent().parent();
				$("[name$='.reviewer.id']", $table).val('');
				$("[name='reviewerNameDiv']", $table).html('');
				$("[name='reviewerUnitDiv']", $table).html('');
				
			});
			$("[name='selectUniversity']").live("click", function(){//弹出层选择成员所在高校
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
			$("[name$='divisionType']").live("change", function(){//选择成员类型
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
			$("[name='selectReviewer']").live("click", function(){//选择项目相关人员
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
			$(".add_review").live("click", function(){//增加一条评审记录
				addTable(this, 'table_review');
			});
//			$(".cal_score").live("change", function(){//计算详细分数
//				calcuateScore($(this).parent().parent().parent().parent());
//			});
			$(".add_last_table").live("click", function(){//在最后面增加评审记录
				$("#last_add_div").hide();
				addLastTable('review', 'table_review');
			});
			$(".delete_row").live("click", function(){//删除一条评审记录
				$(this).parent().parent().parent().parent().remove();
				if($("#review .table_valid").length < 1){
					$("#last_add_div").show();
				}
				sortNum();
				showResult();
			});
			$(".up_row").live("click", function(){//上移
				var onthis = $(this).parent().parent().parent().parent();
		 		var getup = $(this).parent().parent().parent().parent().prev();
		 		$(getup).before(onthis);
		 		sortNum();
			});
			$(".down_row").live("click", function(){//下移
				var onthis = $(this).parent().parent().parent().parent();
		 		var getdown = $(this).parent().parent().parent().parent().next();
		 		$(getdown).after(onthis);
		 		sortNum();
			});
			$("#review" ).sortable( {stop: function(event, ui) {
				sortNum();
			 }});
			sortNum();
			if($("#review .table_valid").length < 1){
				$("#last_add_div").show();
			}
		});	
	};
	
	var checkReview = function(){//提交前检查是否添加了结项评审
		var $allTable = $("#review .table_valid");//检查是否添加了项目成员
		if(!$allTable){
			return false;
		}
		if($allTable.length<1){
			alert("请至少添加一个专家评审信息！");
			return false;
		}
		reviewNum();
		return true;
	};
	var sortNum = function(){//自动编号	
		var spans = getElementsByTagTitle("span","reviewSpan");
	 	for(var iLoop = 0; iLoop < spans.length; iLoop++) {
	 		spans[iLoop].innerHTML=iLoop+1;
	 	}
	};
	var reviewNum = function(){//提交前按table重新分配下标
		$(".table_valid").each(function(key, value){
			$("input, select, textarea", $(value)).each(function(key1, value1){
				value1.name = value1.name.replace(/\[.*\]/, "[" + (key) + "]");
			});
		});
	// 	validate.valid_review();//分配下标后重新设置校验，否则修改完成时会出错
	};
	var addTable = function(obj ,tableId){//添加一个相关人员
		var onthis = $(obj).parent().parent().parent().parent();
		var addTable = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>").insertAfter(onthis);
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag++;
//		validate.valid_review();
		sortNum();
	};
	var addLastTable = function(topId, tableId){
		$("#" + topId).append("<table id='newTable' width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
		var addTable = $("#newTable");
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value){
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;//校验需要修改id，可能是jquery的内部实现
		});
		flag++;
//		validate.valid_review();
		sortNum();
		addTable.removeAttr("id");
	};
	//添加奖励评审结果
	//data：2保存；3提交
	var submitOrSaveAddReviewResult = function(data){//添加奖励评审结果;data：2保存；3提交
		submitOrSaveReviewResult(data, 1);
	};
//	var submitOrSaveModifyReviewResult = function(data){
//		submitOrSaveReviewResult(data, 2);
//	};
	var submitOrSaveReviewResult = function(data){//奖励评审结果是否提交;data：2保存；3提交;type：1添加；2修改
		$("#submitStatus").val(data);
		var url="";
		url="award/moesocial/application/review/addExpert.action";
		$("#review_awardform").attr("action",url);
		$("#review_awardform").submit();
	};
//	var calcuateScore = function($table){//获得当前评审专家的打分，如果四项都有，则计算总分
//		var calcuateFlag = true;
//		var score = 0;
//		for(var i = 0; i < 4; i++){
//			if($(".cal_score", $table).eq(i).val() == "" ){
//				calcuateFlag = false;
//			} else {
//				score += parseFloat($(".cal_score", $table).eq(i).val());
//			}
//		}
//		if(calcuateFlag){
//			$("[name*='reviewScore']", $table).eq(0).attr('value', score);
//			var $grade = $("[name*='reviewGrade']", $table).get(0);
//			if (score > 95) {
//				$grade.value = "优秀";
//			} else if (score >= 80 && score <= 95) {
//				$grade.value = "合格";
//			} else if (score < 80) {
//				$grade.value = "不合格";
//			}
//			showResult();
//		}
//	};
//	var showResult = function(){//判断是否每个评审专家都有总分，如果有，计算小组总分、平均分及成果鉴定等级
//		var index = $(".table_valid").length;
//		var showFlag = true;
//		var totalScore = 0;
//		for(var i = 0; i < index; i++){
//			var reviewScore = $("[name*='reviewScore']", $(".table_valid").eq(i)).val();
//			if(reviewScore == ""){
//				showFlag = false;
//			} else {
//				totalScore += parseFloat(reviewScore);
//			}
//		}
//		if(showFlag && index > 0){
//			var $groupGrade = $("[name*='groupGrade']").get(0);
//			$("[name*='totalScore']").eq(0).attr('value', totalScore);
//			$("[name*='avgScore']").eq(0).attr('value', totalScore / index);
//			if (totalScore / index > 90) {
//				$groupGrade.value = "优秀";
//			} else if (totalScore / index >= 65 && totalScore / index <= 90) {
//				$groupGrade.value = "合格";
//			} else if (totalScore / index < 65) {
//				$groupGrade.value = "不合格";
//			}
//		}else{
//			$("[name*='totalScore']").eq(0).attr('value', "");
//			$("[name*='avgScore']").eq(0).attr('value', "");
//			$("[name*='groupGrade']").eq(0).attr('value', "");
//		}
//	};
//	
	exports.init = function() {
		initReview();
//		
//		$(".j_saveAddReviewResult").live("click", function(){
//			submitOrSaveAddReviewResult(2);
//		});
		
		$(".j_addExp").live("click", function(){
			submitOrSaveAddReviewResult(3);
		});
		
//		$(".j_saveModifyReviewResult").live("click", function(){
//			submitOrSaveModifyReviewResult(2);
//		});
//		
//		$(".j_submitModifyReviewResult").live("click", function(){
//			submitOrSaveModifyReviewResult(3);
//		});
		
	};
});