define(function(require, exports, module) {
	require('datepick');
	require('datepick-zh-CN')($);//手动包装为cmd模块
	require('validate');// 有些日期选择器选择后返回校验
	
	exports.init = function() {
		$('#datepick').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
		$('#expireDate').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
		$("input[class='older']").datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true}); // 检索起始时间
		$("input[class='younger']").datepick({yearRange: '-10:+90', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true}); // 检索终止时间
		$('#approveDate').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
		$('#actualEndDate').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
		$('#planEndDate').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
		$('#applicantSubmitDate').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
		$("input.FloraDatepick").datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
		$('#publicationDate').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
		$('#applicationDate').datepick({yearRange: '-90:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
		$("input[name='createDate1']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 邮件高级检索的邮件创建日期
		$("input[name='createDate2']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 邮件高级检索的邮件创建日期
		$("input[name='expireDate1']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 邮件高级检索的邮件创建日期
		$("input[name='expireDate2']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 邮件高级检索的邮件创建日期
		$("input[name='startDate']").datepick({yearRange: '-20:+20', alignment: "left", autoSize: true});// 新闻、通知高级检索的邮件创建日期
		$("input[name='endDate']").datepick({yearRange: '-20:+20', alignment: "left", autoSize: true});// 新闻、通知高级检索的邮件创建日期
		$("input[name*='applicantDeadline']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 业务管理的个人申请截止时间
		$("input[name*='deptInstDeadline']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 业务管理的部门审核截止时间
		$("input[name*='univDeadline']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 业务管理的高校审核截止时间
		$("input[name*='provDeadline']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 业务管理的省厅截止时间
		$("input[name*='reviewDeadline']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 业务管理的评审截止时间
		$("input[name='mdxQuery.date']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 阶段统计发布时间
		$("input[name=auditStartDate], input[name=auditEndDate]").datepick({yearRange: '-90:+10', onSelect: function(){if($("input[name=auditStartDate]").val() > $("input[name=auditEndDate]").val()) {$("input[name=auditEndDate]").val($("input[name=auditStartDate]").val());}if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});
		$("input[name=applyStartDate], input[name=applyEndDate]").datepick({yearRange: '-90:+10', onSelect: function(){if($("input[name=applyStartDate]").val() > $("input[name=applyEndDate]").val()) {$("input[name=applyEndDate]").val($("input[name=applyStartDate]").val());}if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});

		$("input[name='datetime1']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 邮件高级检索的邮件创建日期
		$("input[name='datetime2']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});// 邮件高级检索的邮件创建日期
		
		$("input[name='memo.remindTime']").datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left", autoSize: true});//备忘录提醒日期
		$(".multiDate").datepick({yearRange: '-20:+10',multiSelect: 31, onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left"});//备忘录结束时间
		$('.start').datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left",autoSize: true});//备忘录开始时间
		$('.end').datepick({yearRange: '-20:+10', onSelect: function(){if($(document.forms[0]).length != 0)$(this).valid();}, alignment: "left",autoSize: true});//备忘录结束时间
	}
});
