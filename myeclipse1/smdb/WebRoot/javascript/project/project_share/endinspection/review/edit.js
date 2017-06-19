/**
 * 用于结项评审
 * @author 余潜玉、肖雅
 */

define(function(require, exports, module) {
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('form');
	var endRevValid = require('javascript/project/project_share/endinspection/review/validate');
	
	var init = function(){
		$(function() {
			//计算详细分数
			$(".cal_score").live("change", function(){
				calcuateScore();
			});
		});
	};
	
	/******************************以下方法用于专家评审*************************************/
	
	//添加专家评审
	//data：2保存；3提交
	var submitOrSaveAddEndReview = function(data, layer){
		submitOrSaveEndReview(data, 1, layer);
	};
	
	//修改专家评审
	//data：2保存；3提交
	var submitOrSaveModifyEndReview = function(data, layer){
		submitOrSaveEndReview(data, 2, layer);
	};
	
	//专家评审是否提交
	//data：2保存；3提交
	//type：1添加；2修改
	//id：结项id
	var submitOrSaveEndReview = function(data, type, layer){
		if(!$("#end_review").valid()){
			return;
		}
		if(data == 3) {
			if( !confirm('提交后无法修改，是否确认提交？'))
				return ;
		}
		var endId = document.getElementById('endId').value;
		var innovationScore = document.getElementsByName("innovationScore")[0].value;
		var scientificScore = document.getElementsByName("scientificScore")[0].value;
		var benefitScore = document.getElementsByName("benefitScore")[0].value;
		var qualitativeOpinion = document.getElementById('qualitativeOpinion').value;
		var opinion = document.getElementsByName('opinion')[0].value;
		var dis = {
			"submitStatus":data,
			"innovationScore":innovationScore,
			"scientificScore":scientificScore,
			"benefitScore":benefitScore,
			"qualitativeOpinion":qualitativeOpinion,
			"opinion":opinion
		};
		layer.callBack(dis, type, endId);
		layer.destroy();
	};
	
	// 获得当前评审专家的打分，如果三项都有，则计算总分。
	var calcuateScore = function(){
		var calcuateFlag = true;
		var score = 0;
		for(var i = 0; i < 3; i++){
			if($(".cal_score").eq(i).val() == "" ){
				calcuateFlag = false;
			} else {
				score += parseFloat($(".cal_score").eq(i).val());
			}
		}
		if(calcuateFlag){
			$("[name*='totalScore']").eq(0).attr('value', score);
			var $grade = $("[name*='grade']").get(0);
			if (score > 90) {
				$grade.value = "优秀";
			} else if (score >= 65 && score <= 90) {
				$grade.value = "合格";
			} else if (score < 65) {
				$grade.value = "不合格";
			}
		}
	};
	
	/****************************************结束******************************************/
	
	/******************************以下方法用于小组评审*************************************/
	//组评审时查看本组专家所有意见
	var alertGroupOpinion = function(endId, projectType){
		popProjectOperation({
			title : "查看结项小组所有专家的评审意见",
			src : "project/" + projectType + "/endinspection/review/viewGroupOpinion.action?endId=" + endId
		});
	};
	
	//初始化查看本组专家所有意见链接
	var initGroupOpinion = function(projectType){
		$(".j_groupOpinion").live("click", function() {
			alertGroupOpinion($("#endId").val(), projectType);
			return false;
		});
	};
	
	//添加小组评审
	//data：2保存；3提交
	var submitOrSaveAddEndGroupReview = function(data, layer){
		submitOrSaveEndGroupReview(data, 1, layer);
	};
	
	//修改小组评审
	//data：2保存；3提交
	var submitOrSaveModifyEndGroupReview = function(data, layer){
		submitOrSaveEndGroupReview(data, 2, layer);
	};
	
	//组评审是否提交
	//data：2保存；3提交
	//type：1添加；2修改
	var submitOrSaveEndGroupReview = function(data, type, layer){
		if(!$("#end_review_group").valid()){
			return;
		}
		if(data==3) {
			if( !confirm('提交后无法修改，是否确认提交？'))
				return;
		}
		var endId = document.getElementById('endId').value;
		var reviewWay=$("input[name='reviewWay'][type='radio']:checked").val();
		var reviewResult=$("input[name='reviewResult'][type='radio']:checked").val();
		var reviewOpinionQualitative = document.getElementById('reviewOpinionQualitative').value;
		var reviewOpinion = document.getElementsByName('reviewOpinion')[0].value;
		var dis = {
			"reviewWay":reviewWay,
			"reviewStatus":data,
			"reviewResult":reviewResult,
			"reviewOpinionQualitative":reviewOpinionQualitative,
			"reviewOpinion":reviewOpinion
		};
		layer.callBack(dis, type, endId);
		layer.destroy();
	};
	
	module.exports = {
		 init: function(){init()}, 
		 submitOrSaveAddEndReview: function(data, layer){submitOrSaveAddEndReview(data, layer)},
		 submitOrSaveModifyEndReview: function(data, layer){submitOrSaveModifyEndReview(data, layer)},
		 alertGroupOpinion: function(endId, projectType){alertGroupOpinion(endId, projectType)}, 
		 initGroupOpinion: function(projectType){initGroupOpinion(projectType)},
		 submitOrSaveAddEndGroupReview: function(data, layer){submitOrSaveAddEndGroupReview(data, layer)},
		 submitOrSaveModifyEndGroupReview: function(data, layer){submitOrSaveModifyEndGroupReview(data, layer)}
	};
});
