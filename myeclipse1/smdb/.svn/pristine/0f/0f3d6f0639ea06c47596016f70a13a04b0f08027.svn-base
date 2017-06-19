/**
 * 用于为申请评审添加指定专家
 * 
 * @author 杨发强
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

	var flag = 0;
	var reviewNumber = 0;
	var errorInfo = "";

	var init = function() {
		$(function() {
			// 选择人员类型
			$("[name$='reviewerType']").live("change", function() {
				var $table = $(this).parent().parent().parent();
				if (accountType == "MINISTRY") {// 教育部录入
					$("[name$='.reviewer.id']", $table).val('');
					$("[name='reviewerNameDiv']", $table).html('');
					$("[name='reviewerUnitDiv']", $table).html('');
				} else {
					$("[name$='.university.id']", $table).val(null);
					$("[name$='.agencyName']", $table).val(null);
					$("[name='universityNameDiv']", $table).html(null);
					if ($(this).val() == 2) {// 外部专家
						$("[name='inputUniDiv']", $table).show();
						$("[name='selectUniDiv']", $table).hide();
						$("[name$='divisionType']", $table).val(3);
					} else {
						$("[name='inputUniDiv']", $table).hide();
						$("[name='selectUniDiv']", $table).show();
						$("[name$='divisionType']", $table).val(-1);
					}
				}
			});

			// 选择项目相关人员
			$("[name='selectReviewer']")
					.live(
							"click",
							function() {
								$slefTr = $(this).parent().parent().prev();
								$table = $(this).parent().parent().parent();
								var reviewType = $("[name$='reviewerType']",
										$slefTr).val();
								if (reviewType == '-1') {
									alert("请选择人员类型");
									return false;
								}

								var type = {
									'1' : 8,
									'2' : 7,
									'3' : 15
								};// 教师|专家|学生

								popSelect({
									type : type[reviewType],
									inData : {
										"id" : $("[name$='reviewer.id']",
												$table).val(),
										"name" : $("[name$='reviewerName']",
												$table).val()
									},
									callBack : function(result) {
										$("[name$='reviewer.id']", $table).val(
												result.data.id);
										$("[name$='reviewerName']", $table)
												.val(result.data.name);
										$("[name='reviewerNameDiv']", $table)
												.html(result.data.name);
										$("[name='reviewerUnitDiv']", $table)
												.html(result.data.sname);
									}
								});
							});
			// 增加一条评审记录
			$(".add_review").live("click", function() {
				addTable(this, 'table_review');
			});
			// 在最后面增加评审记录
			$(".add_last_table").live("click", function() {
				$("#last_add_div").hide();
				addLastTable('review', 'table_review');
			});
			// 删除一条评审记录
			$(".delete_row").live("click", function() {
				$(this).parent().parent().parent().parent().remove();
				if ($("#review .table_valid").length < 1) {
					$("#last_add_div").show();
				}
				sortNum();
				showResult();
			});
			// 上移
			$(".up_row").live("click", function() {
				var onthis = $(this).parent().parent().parent().parent();
				var getup = $(this).parent().parent().parent().parent().prev();
				$(getup).before(onthis);
				sortNum();
			});
			// 下移
			$(".down_row").live(
					"click",
					function() {
						var onthis = $(this).parent().parent().parent()
								.parent();
						var getdown = $(this).parent().parent().parent()
								.parent().next();
						$(getdown).after(onthis);
						sortNum();
					});

			// 成员排序
			$("#review").sortable({
				stop : function(event, ui) {
					sortNum();
				}
			});
			sortNum();
			if ($("#review .table_valid").length < 1) {
				$("#last_add_div").show();
			}
			;
		});
	};

	var isPersonMatchCallback = function(data) {
		if (data == 0) {// 填写的信息不匹配
			errorInfo += "评审专家" + reviewNumber + "的证件信息与姓名和性别不符合，请核实后重新填写！\n"
		}
	};

	// 自动编号
	var sortNum = function() {
		var spans = getElementsByTagTitle("span", "reviewSpan");
		for ( var iLoop = 0; iLoop < spans.length; iLoop++) {
			spans[iLoop].innerHTML = iLoop + 1;
		}
	};

	/**
	 * 提交前按table重新分配下标
	 */
	var reviewNum = function() {
		$(".table_valid").each(function(key, value) {
			$("input, select, textarea", $(value)).each(function(key1, value1) {
				value1.name = value1.name.replace(/\[.*\]/, "[" + (key) + "]");
			});
		});
	}

	/**
	 * 添加一个相关人员
	 */
	var addTable = function(obj, tableId) {
		var onthis = $(obj).parent().parent().parent().parent();
		var addTable = $(
				"<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>")
				.insertAfter(onthis);
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value) {
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;// 校验需要修改id，可能是jquery的内部实现
		});
		flag++;
		sortNum();
	};

	var addLastTable = function(topId, tableId) {
		$("#" + topId)
				.append(
						"<table id='newTable' width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
		var addTable = $("#newTable");
		addTable.html($("#" + tableId).html());
		addTable.addClass("table_valid");
		$(":input", addTable).each(function(key, value) {
			value.name = value.name.replace(/\[.*\]/, "[" + flag + "]");
			value.id = value.name;// 校验需要修改id，可能是jquery的内部实现
		});
		flag++;
		sortNum();
		addTable.removeAttr("id");
	};

	// 添加结项评审专家
	var addReviewExpert = function(entityId, projectType) {
		addExpert(entityId, projectType);
	};
	var addExpert = function(entityId, projectType) {
		var url = "project/" + projectType
				+ "/application/review/addResultExpert.action";
		$("#review_endform").attr("action", url);
		$("#review_endform").submit();
	}

	/*-------------------初始化方法结束------------------------*/

	module.exports = {
		addReviewExpert : function(entityId, projectType) {
			addReviewExpert(entityId, projectType)
		},
		init : function() {
			init()
		}
	};
});
