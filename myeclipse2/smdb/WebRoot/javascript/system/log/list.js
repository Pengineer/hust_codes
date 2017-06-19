define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":"log",
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4}
		});
		
		$(".link2").live("click", function() {
			popAccount(this.id);
			return false;
		});
		$("#list_statistic").live("click", function() {
			popLogStat();
//			select({"title":"日志统计信息","url":basePath + "log/toStatistic.action"});
//			return false;
		});
		
		$(".j_viewDetail").live("mouseover",function() {
			var ip = this.id;
			$(this).css({
				"cursor":"default"
			});
			$("#j_view").show();
			$.ajax({
				url: "log/getIpAddress.action",
				type: "post",
				data: "ip=" + ip,
				dataType: "json",
				success: function(result) {
					if (result.code == 0) {
						$("#j_view").empty();
						$("#j_view").append("IP地址：").append(result.city);
					} else {
						$("#j_view").empty();
						$("#j_view").append("IP地址：").append("未知");
					}
				}
			});
		/*	$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip='+ip,function(){
				var city = remote_ip_info.city;
				$("#cityNmae").append(city);
			});*/
		}).live("mouseout",function(){
			$("#j_view").empty();
			$(this).css({
				"cursor":""
			});
			$("#j_view").css({
				"display":"none"
			});
		});
	};
});
